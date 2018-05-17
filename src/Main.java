import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		final int MAX_ITERATIONS = 1000;

		//Used by the Simulated Annealing Algorithm
		final double STARTING_TEMPERATURE = 100000;
		final double COOLING_RATE = 0.003;

		if (args.length != 5) {
			print_usage();
			System.exit(0);
		}

		/*TESTING GROUP AFFINITY*/

        Group g1 = new Group(10,5);
        double g1attributes[] = {0.5,0.5,0.5,0.5,0.5};
        g1.attributes = g1attributes;
        Group g2 = new Group(2,5);
        double g2attributes[] = {0.5,0.5,0.5,0.5,0.5};
        g2.attributes = g2attributes;
        Group g3 = new Group(10,5);
        double g3attributes[] = {0.5,0.5,0.5,0.5,0.5};
        g3.attributes = g3attributes;

        ArrayList<Group> table = new ArrayList<>();
        table.add(g1);
        table.add(g2);
        table.add(g3);

        System.out.println("Affinity: " + g1.getAffinityWith(table));

        //System.exit(0);

		//TODO: prevent user from inputing bad data

		int minTableSize = Integer.parseInt(args[0]);
		int maxTableSize = Integer.parseInt(args[1]);
		int maxTableNum = Integer.parseInt(args[2]);

		int maxGroupSize = maxTableSize/3;
		int minGroupSize = 3;
		int groupNum = 10;

		int generationSize = Integer.parseInt(args[4]);

		RandomGroupGenerator rgg; // = new RandomGroupGenerator(maxGroupSize, minGroupSize, groupNum);

		rgg = RandomGroupGenerator.load("groups.ser");

		/*
		GeneticAlgorithm genAlg = new GeneticAlgorithm(minTableSize, maxTableSize, maxTableNum, groupNum,
				generationSize, rgg.groups);

		long genAlgStartTime = System.currentTimeMillis();
		genAlg.start(-1, MAX_ITERATIONS);
		long genAlgEndTime = System.currentTimeMillis();
		long genAlgTotalTime = genAlgEndTime - genAlgStartTime;
		genAlg.printFinalSolution(genAlgTotalTime);*/

		HillClimbingAlgorithm hca = new HillClimbingAlgorithm(minTableSize, maxTableSize, maxTableNum, rgg.groups);

		long hcaAlgStartTime = System.currentTimeMillis();
		hca.start(MAX_ITERATIONS);
		long hcaAlgEndTime = System.currentTimeMillis();
		long hcaAlgTotalTime = hcaAlgEndTime - hcaAlgStartTime;
		hca.printFinalSolution(hcaAlgTotalTime);

		System.out.println("----------------------------------Simulated Annealing--------------------------------");
		rgg = RandomGroupGenerator.load("groups.ser");
		SimulatedAnnealingAlgorithm saa = new SimulatedAnnealingAlgorithm(minTableSize, maxTableSize, maxTableNum, rgg.groups);

		long saaAlgStartTime = System.currentTimeMillis();
		saa.start(STARTING_TEMPERATURE, COOLING_RATE);
		long saaAlgEndTime = System.currentTimeMillis();
		long saaAlgTotalTime = saaAlgEndTime- saaAlgStartTime;
		saa.printFinalSolution(saaAlgTotalTime);
	}

	static void print_usage() {
		System.out.println("How to use:");
		System.out.println(
				"Main <min table size> <max table size> <max number of tables> <number of groups> <number of generations>");
	}
}
