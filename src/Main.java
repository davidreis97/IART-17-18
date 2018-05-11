
public class Main {
	public static void main(String[] args) {
		final int MAX_ITERATIONS = 1000000;

		if (args.length != 5) {
			print_usage();
			System.exit(0);
		}
		
		//TODO: prevent user from inputing bad data

		int minTableSize = Integer.parseInt(args[0]);
		int maxTableSize = Integer.parseInt(args[1]);
		int maxTableNum = Integer.parseInt(args[2]);

		int maxGroupSize = maxTableSize;
		int minGroupSize = 2;
		int groupNum = Integer.parseInt(args[3]);

		int generationSize = Integer.parseInt(args[4]);

		RandomGroupGenerator rgg = new RandomGroupGenerator(maxGroupSize, minGroupSize, groupNum);

		GeneticAlgorithm genAlg = new GeneticAlgorithm(minTableSize, maxTableSize, maxTableNum, groupNum,
				generationSize, rgg.groups);

		long genAlgStartTime = System.currentTimeMillis();
		genAlg.start(-1, MAX_ITERATIONS);
		long genAlgEndTime = System.currentTimeMillis();
		long genAlgTotalTime = genAlgEndTime - genAlgStartTime;
		genAlg.printFinalSolution(genAlgTotalTime);

		HillClimbingAlgorithm hca = new HillClimbingAlgorithm(minTableSize, maxTableSize, maxTableNum, rgg.groups);

		long hcaAlgStartTime = System.currentTimeMillis();
		hca.start(MAX_ITERATIONS);
		long hcaAlgEndTime = System.currentTimeMillis();
		long hcaAlgTotalTime = hcaAlgEndTime - hcaAlgStartTime;
		hca.printFinalSolution(hcaAlgTotalTime);

		/* TODO: simulated annealing & hill climbing algorithms */
		// SimulatedAnnealingAlgorithm saa = new SimulatedAnnealingAlgorithm();
	}

	static void print_usage() {
		System.out.println("How to use:");
		System.out.println(
				"Main <min table size> <max table size> <max number of tables> <number of groups> <number of generations>");
	}
}
