import java.io.Serializable;

public class GeneticAlgorithm implements Serializable {
	public Group[] groups;

	public int minTableSize;
	public int maxTableSize;
	public int maxTableNum;
	public int groupNo;
	public int genSize;

	public int attributeNum;

	public GeneticAlgorithm(int argMinTableSize, int argMaxTableSize, int argMaxTableNum, int argGroupNo,
			int argGenSize, Group[] argGroups) {
		minTableSize = argMinTableSize;
		maxTableSize = argMaxTableSize;
		maxTableNum = argMaxTableNum;

		groupNo = argGroupNo;
		genSize = argGenSize;

		attributeNum = 5;

		groups = argGroups;
	}

	public void start(long threshold, long maxGens) {
		Generation currGen = new Generation(minTableSize, maxTableSize, maxTableNum, groupNo, genSize, this);
		currGen.randomize();
		long score = 0;
		int generations = 0;
		do {
			generations++;
			score = currGen.getTotalFitness();
			System.out.println("Generation " + generations + ": (TF=" + score + "|AF=" + currGen.getAvgFitness()
					+ "|BCF=" + currGen.bestChromo.getFitness() + ")"); // TS - Total Score BCF - Best Chromosome
																		// Fitness

			currGen = currGen.generateNewGen();
		} while ((score < threshold || threshold == -1) && (maxGens > generations || maxGens == -1));
	}

	public static void printArray(int[] a) {
		System.out.print("[");
		for (int elem : a) {
			System.out.print(elem + "/");
		}
		System.out.println("]");
	}

}
