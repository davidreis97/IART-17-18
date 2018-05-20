import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    RandomGroupGenerator rgg;

    public void groupGenerationMenu(Scanner keyboard){
		int choice = 0;

		System.out.println("IART 17/18");
		System.out.println("1- Generate groups randomly");
		System.out.println("2- Load groups from file");

		choice = keyboard.nextInt();

		if(choice == 1){
			generateGroupsRandomly(keyboard);
		}else if(choice == 2){
			loadGroupsFromFile(keyboard);
		}else{
			groupGenerationMenu(keyboard);
			return;
		}

		chooseAlgorithm(keyboard);
	}

	public void loadGroupsFromFile(Scanner keyboard){
        System.out.println("-----------------------");
        System.out.println("File name:");
        keyboard.nextLine();
        String fileName = keyboard.nextLine();

        rgg = RandomGroupGenerator.load(fileName);
    }

	public void generateGroupsRandomly(Scanner keyboard){
	    System.out.println("Maximum size of group: ");
        int maxGroupSize = keyboard.nextInt();
        System.out.println("Minimum size of group: ");
        int minGroupSize = keyboard.nextInt();
        System.out.println("Number of groups: ");
        int groupNum = keyboard.nextInt();
        System.out.println("Number of attributes: ");
        int attributes = keyboard.nextInt();

        rgg = new RandomGroupGenerator(maxGroupSize, minGroupSize, groupNum, attributes);

        System.out.println("Groups have been generated randomly, do you wish to save them to a file? (Y/N)");
        keyboard.nextLine();
        String response = keyboard.nextLine();
        if(response.equals("Y") || response.equals("y")){
            System.out.println("Filename: ");
            String filename = keyboard.nextLine();

            rgg.save(filename);
        }
    }

	public void chooseAlgorithm(Scanner keyboard){
		int choice = 0;

		System.out.println("-----------------------");
		System.out.println("Choose algorithm:");
		System.out.println("1- Genetic Algorithm");
		System.out.println("2- Hill Climbing Algorithm");
		System.out.println("3- Simulated Annealing");

		choice = keyboard.nextInt();

		if(choice == 1){
			geneticAlg(keyboard);
		}else if(choice == 2){
			hillClimbing(keyboard);
		}else if(choice == 3){
			simulAnneal(keyboard);
		}else{
			chooseAlgorithm(keyboard);
			return;
		}
	}

	public void simulAnneal(Scanner keyboard){
		System.out.println("Minimum table size: ");
		int minTableSize = keyboard.nextInt();
		System.out.println("Maximum table size: ");
		int maxTableSize = keyboard.nextInt();
		System.out.println("Maximum number of tables: ");
		int maxTableNum = keyboard.nextInt();
		System.out.println("Starting temperature (Suggested 100000): ");
		double startingTemperature = keyboard.nextDouble();
		System.out.println("Cooling Rate (Suggested 0.003): ");
		double coolingRate = keyboard.nextDouble();
        System.out.println("Maximum distance to neighbour");
        int maxDist = keyboard.nextInt();

		SimulatedAnnealingAlgorithm saa = new SimulatedAnnealingAlgorithm(minTableSize, maxTableSize, maxTableNum, rgg.groups, maxDist);

		long saaAlgStartTime = System.currentTimeMillis();
		saa.start(startingTemperature, coolingRate);
		long saaAlgEndTime = System.currentTimeMillis();
		long saaAlgTotalTime = saaAlgEndTime- saaAlgStartTime;
		saa.printFinalSolution(saaAlgTotalTime);
    }

	public void geneticAlg(Scanner keyboard){
		System.out.println("Minimum table size: ");
		int minTableSize = keyboard.nextInt();
		System.out.println("Maximum table size: ");
		int maxTableSize = keyboard.nextInt();
		System.out.println("Maximum number of tables: ");
		int maxTableNum = keyboard.nextInt();
		System.out.println("Number of chromosomes per generation: ");
		int generationSize = keyboard.nextInt();
		System.out.println("Maximum number of generations: ");
		int maxIterations = keyboard.nextInt();
		System.out.println("Maximum fitness: ");
		int maxFitness = keyboard.nextInt();

		GeneticAlgorithm genAlg = new GeneticAlgorithm(minTableSize, maxTableSize, maxTableNum, rgg.groups.length, generationSize, rgg.groups);

		long genAlgStartTime = System.currentTimeMillis();
		genAlg.start(maxFitness, maxIterations);
		long genAlgEndTime = System.currentTimeMillis();
		long genAlgTotalTime = genAlgEndTime - genAlgStartTime;
		genAlg.printFinalSolution(genAlgTotalTime);
	}

	public void hillClimbing(Scanner keyboard){
        System.out.println("Minimum table size: ");
        int minTableSize = keyboard.nextInt();
        System.out.println("Maximum table size: ");
        int maxTableSize = keyboard.nextInt();
        System.out.println("Maximum number of tables: ");
        int maxTableNum = keyboard.nextInt();
        System.out.println("Maximum number of iterations: ");
        int maxIterations = keyboard.nextInt();
        System.out.println("Maximum distance to neighbour");
        int maxDist = keyboard.nextInt();

        HillClimbingAlgorithm hca = new HillClimbingAlgorithm(minTableSize, maxTableSize, maxTableNum, rgg.groups, maxDist);

        long hcaAlgStartTime = System.currentTimeMillis();
        hca.start(maxIterations);
        long hcaAlgEndTime = System.currentTimeMillis();
        long hcaAlgTotalTime = hcaAlgEndTime - hcaAlgStartTime;
        hca.printFinalSolution(hcaAlgTotalTime);
    }

	public static void main(String[] args) {
        //testAffinity();
        Main main = new Main();
        Scanner keyboard = new Scanner(System.in);
        main.groupGenerationMenu(keyboard);
	}

    public static void testAffinity(){
        /*TESTING GROUP AFFINITY*/

        Group g1 = new Group(4,2);
        double g1attributes[] = {-0.6645973066055582,-0.7171051709283822};
        g1.attributes = g1attributes;
        Group g2 = new Group(4,2);
        double g2attributes[] = {0.14627073635600452,-0.867482705269095};
        g2.attributes = g2attributes;
        Group g3 = new Group(2,2);
        double g3attributes[] = {-0.8831140272872668,0.9155636380006296};
        g3.attributes = g3attributes;
        Group g4 = new Group(4,2);
        double g4attributes[] = {-0.3143717121712293,0.4805433092432947};
        g4.attributes = g4attributes;

        ArrayList<Group> table1 = new ArrayList<>();
        table1.add(g1);
        table1.add(g2);

        ArrayList<Group> table2 = new ArrayList<>();
        table2.add(g3);

        ArrayList<Group> table3 = new ArrayList<>();
        table3.add(g4);

        int g1affinity = g1.getAffinityWith(table1);
        int g2affinity = g2.getAffinityWith(table1);
        int g3affinity = g3.getAffinityWith(table2);
        int g4affinity = g4.getAffinityWith(table3);       
        
        System.out.println("Affinity: " + g1affinity + g2affinity + g3affinity + g4affinity);
    }
}
