import java.util.Random;
import java.util.ArrayList;

public class BatMap {

	public static Location[][] map;
	Random rand = new Random();
	
	public BatMap(){

		map = new Location[20][20];

        // Initialize Map
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                map[i][j] = new Location(0);
            }
        }

	}
	
	public void addPerson(int x, int y, Person person){

		// add person to map
		map[x][y].addPerson(person);
        int range = person.getRange();
        int threatLevel = person.getThreatLevel();
        int distance = 0;
        int distanceThreat = 0;

		// increase threat level into range of person
		for(int i = range * -1; i <= range; i++){
			for(int j = range * -1; j <= range; j++){
                int xCoord = x + i;
                int yCoord = y + j;
                int xDist = Math.abs(0 - i);
                int yDist = Math.abs(0 - j);
                
                // find farthest distance from person
                if(xDist > yDist){
                	distance = xDist;
                }else{
                	distance = yDist;
                }
                // decrease/increase threat level based on distance and good/bad person
                if (person.whoAmI() == "bad"){
                	distanceThreat = threatLevel - (distance * 8);
                    if(distanceThreat < 0) distanceThreat = 0;
                } else if (person.whoAmI() == "good"){
                	distanceThreat = threatLevel + (distance * 13);
                    if(distanceThreat > 0) distanceThreat = 0;
                } else {
                	distanceThreat = threatLevel;
                }
				if(xCoord >= 0 && xCoord < 20 && yCoord >= 0 && yCoord < 20){
                    // if(i != 0 && j != 0) 
                        this.addPeopleThreat(xCoord, yCoord, distanceThreat);
				}
			}
		}

	}
		
	public void addThreat(int x, int y, int threatLevel){
		map[x][y].addThreat(threatLevel);
	}	

	public void addPeopleThreat(int x, int y, int threatLevel){
		map[x][y].addPeopleThreat(threatLevel);
	}	

    public int getLocThreat(int x, int y) {
        return map[x][y].getThreat();
    }

    public void printThreats() {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                int threat = map[j][i].getThreat();
                System.out.printf("%3d ", threat);
            }
            System.out.println("");
        }
    }

    public ArrayList<String> getLocPeople(int x, int y) {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Person> people = map[x][y].getPeople();
        for(int i = 0; i < people.size(); i++) {
            names.add(people.get(i).getName());
        }
        return names;
    }

    public void reloadPeople(ArrayList<Person> people) {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                map[j][i].clearLocation();
            }
        }
        for(Person person: people) {
            int x = person.getXVal();
            int y = person.getYVal();
            addPerson(x, y, person);
        }
    }

}
