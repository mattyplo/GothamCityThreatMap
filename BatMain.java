import java.io.File; 
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BatMain {

	private static Random rand = new Random(System.currentTimeMillis());
	private static int numOfPeds = 50;
    private static ArrayList<Person> people = new ArrayList<Person>();

	public static void main(String[] args) {

        // intialize variables
		BatMap map = new BatMap();
		
        // load people from .txt files
        addPeopleFromFiles(people); 

        // initialize map
		assignThreatsToMap(map);
		assignPersonsToMap(map, people);
        addPedestrians(map);

        // display threat map (terminal)
        System.out.printf("\nThreat Level Heat Map: \n\n");
        map.printThreats();

        // display threat map (gui)
        UI.genMain(map, people);
        // UI.genPopUp();

	}

    public static void printPeopleLocations() {
        for(int i = 0; i < people.size(); i++) {
            String name = people.get(i).getName();
            int x = people.get(i).getXVal();
            int y = people.get(i).getYVal();
            System.out.printf("%s: %d, %d\n", name, x, y);
        }
    }

    public static void addPeopleFromFiles(ArrayList<Person> people) {
		try {

			Scanner scan = new Scanner(new File("goodPeople.txt"));
			while (scan.hasNextLine()) {

                // get line info
				String line = scan.nextLine();
				String name = line.split(":")[0];
				String range = line.split(":")[1];
				String threatLevel = line.split(":")[2];

                // convert range and threat level to int
				int intRange = Integer.parseInt(range);
				int intThreat = Integer.parseInt(threatLevel);
				
                // add good people
				people.add(new Good(name, intRange, intThreat));

			}
			scan.close();

			scan = new Scanner(new File("badPeople.txt"));
			while (scan.hasNextLine()) {

                // extract line info
				String line = scan.nextLine();
				String name = line.split(":")[0];
				String range = line.split(":")[1];
				String threatLevel = line.split(":")[2];

                // convert range and threat level to int
				int intRange = Integer.parseInt(range);
				int intThreat = Integer.parseInt(threatLevel);
				
                // add bad people
				people.add(new Bad(name, intRange, intThreat));

			}
			scan.close();

		} catch (FileNotFoundException e) {
			// todo: Auto-generated catch block
			e.printStackTrace();
		}
    }

    // to make random number generation more readable
    public static int genRandIntRange(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

	public static void assignThreatsToMap(BatMap map) {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
                // I disabled randomization for now
                // to make sure people are influencing map
				// int locThreat = genRandIntRange(-10, 10);
                int locThreat = 0; // default to 0
				map.addThreat(i, j, locThreat);
			}
		}
	}

	public static void assignPersonsToMap(BatMap map, ArrayList<Person> people) {
		for (int i = 0; i < people.size(); i++) {
			int xVal = genRandIntRange(0, 19);
			int yVal = genRandIntRange(0, 19);
			map.addPerson(xVal, yVal, people.get(i));
			people.get(i).setLocation(xVal, yVal);
		}
	}

    public static void addPedestrians(BatMap map) {

        for (int i = 0; i < numOfPeds; i++) {

            // randomize attributes
            int rand_x = genRandIntRange(0, 19);
            int rand_y = genRandIntRange(0, 19);
            int rand_range = genRandIntRange(0, 3);

            // create pedestrian
            Pedestrian ped = new Pedestrian(rand_range);
            ped.setLocation(rand_x, rand_y);
            people.add(ped);

            // add to map
            map.addPerson(rand_x, rand_y, ped);

        }

    }

}
