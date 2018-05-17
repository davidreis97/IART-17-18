public class HillClimbingAlgorithm extends Algorithm {
    public Chromosome currentSolution;

	public HillClimbingAlgorithm(int argMinTableSize, int argMaxTableSize, int argMaxTableNum, Group[] argGroups, int largestJump) {
		super(argMinTableSize, argMaxTableSize, argMaxTableNum, argGroups.length, argGroups);

		this.largestJump = largestJump;
		currentSolution = new Chromosome(maxTableNum,groupNo,this);
		currentSolution.randomizeValid();
	}

	public void start(int max_iterations) {
		int iterationsCounter = 0;
		do {
		    Chromosome nextSol = currentSolution.getImprovedNeighbour();
			if(nextSol == null){
			    break;
            }
            currentSolution = nextSol;
            System.out.println(currentSolution.getFitness());
		} while (iterationsCounter < max_iterations);
	}

    public void printFinalSolution(long algTotalTime) {
        System.out.println("Hill Climbing Algorithm Results:");
        System.out.println();

        long ms = algTotalTime % 1000;
        long s = algTotalTime / 1000;
        System.out.println("Code runtime - " + s + "." + ms + " seconds");

        System.out.println(printChromosomeInfo(currentSolution));
    }
}
