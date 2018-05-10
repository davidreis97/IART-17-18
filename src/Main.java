
public class Main {
	public static void main(String[] args) {

		if (args.length != 5) {
			print_usage();
			System.exit(0);
		}

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
		
		/*TODO: simulated annealing & hill climbing algorithms*/
		//SimulatedAnnealingAlgorithm saa = new SimulatedAnnealingAlgorithm();
		//HillClimbingAlgorithm hca = new HillClimbingAlgorithm();

		genAlg.start(-1, 1000000);
	}

	static void print_usage() {
		System.out.println("How to use:");
		System.out.println(
				"Main <min table size> <max table size> <max number of tables> <number of groups> <number of generations>");
	}
}
