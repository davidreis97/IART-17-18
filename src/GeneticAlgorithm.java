public class GeneticAlgorithm extends Algorithm{
	public Generation currGen;
	public Chromosome bestChromo;

	public GeneticAlgorithm(int argMinTableSize, int argMaxTableSize, int argMaxTableNum, int argGroupNo, int argGenSize, Group[] argGroups) {
		super(argMinTableSize,argMaxTableSize,argMaxTableNum,argGroupNo,argGroups);

        genSize = argGenSize;

        currGen = new Generation(minTableSize, maxTableSize, maxTableNum, groupNo, genSize, this);
		currGen.randomize();
	}

	public void start(long threshold, long maxGens) {
		double score;
		int generations = 0;
		do {
			generations++;
			score = currGen.getTotalFitness();
			if(bestChromo == null || bestChromo.getFitness() < currGen.bestChromo.getFitness()){
				bestChromo = currGen.bestChromo;
			}
			if(currGen.getAvgFitness()/currGen.bestChromo.getFitness() > 0.95){
				System.out.println("Solution converged earlier than expected");
				break;
			}
			System.out.println("Generation " + generations + ": (TF=" + score + "|AF=" +
								currGen.getAvgFitness() + "|BCF=" + currGen.bestChromo.getFitness() + ")");
			currGen = currGen.generateNewGen();
		} while ((bestChromo.getFitness() < threshold || threshold == -1) && (maxGens > generations || maxGens == -1));
	}

	public void printFinalSolution(long genAlgTotalTime) {
		System.out.println("Genetic Algorithm Results:");
		System.out.println();

		long ms = genAlgTotalTime % 1000;
		long s = genAlgTotalTime / 1000;
		System.out.println("Code runtime - " + s + "." + ms + " seconds");

		currGen.getTotalFitness();
		System.out.println(printChromosomeInfo(bestChromo));
	}
}
