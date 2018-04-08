import java.util.ArrayList;

public class Location {

	private int locationThreat;
    private int peopleThreat = 0;
	ArrayList<Person> people;

	public Location(int threatLevel) {
        locationThreat = threatLevel;
		this.people = new ArrayList<Person>();
	}
	
	public void addPerson(Person person){
		people.add(person);
	}
	
	public void addThreat(int threatLevel){
		locationThreat += threatLevel;
	}

    public void addPeopleThreat(int threatLevel) {
        peopleThreat += threatLevel;
    }

    public void clearLocation() {
        peopleThreat = 0;
        people.clear();
    }
	
	public int getThreat(){
		int totalThreat = locationThreat + peopleThreat;
		for(int i = 0; i < people.size(); i++){
			totalThreat += people.get(i).getThreatLevel();
		}
		return totalThreat;
	}
	
    public ArrayList<Person> getPeople() {
        return people;
    }
	
}
