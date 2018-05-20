public class Generation {
    public Chromosome[] chromosomes;

	private int minTableSize;
	private int maxTableSize;
	private int maxTableNum;
	private int groupNo;
	private int genSize;

	private int totalFitness;
	private int avgFitness;
	public Chromosome bestChromo;

	private GeneticAlgorithm genAlg;

	public Generation(int minTableSize, int maxTableSize, int maxTableNum, int groupNo, int genSize,
			GeneticAlgorithm genAlg) {
		this.minTableSize = minTableSize;
		this.maxTableSize = maxTableSize;
		this.maxTableNum = maxTableNum;
		this.groupNo = groupNo;
		this.genSize = genSize;
		this.genAlg = genAlg;

		totalFitness = 0;
		avgFitness = 0;

		chromosomes = new Chromosome[genSize];
	}

	public void randomize() {
		for (int i = 0; i < chromosomes.length; i++) {
			chromosomes[i] = new Chromosome(maxTableNum, groupNo, genAlg);
			chromosomes[i].randomize();
		}
	}

	public int getTotalFitness() {
		if (totalFitness != 0) {
			return totalFitness;
		}

		bestChromo = chromosomes[0];

		for (Chromosome chromosome : chromosomes) {
			if (bestChromo.getFitness() < chromosome.getFitness()) {
				bestChromo = chromosome;
			}
			totalFitness += chromosome.getFitness();
		}

		if (totalFitness > 0) {
			return totalFitness;
		} else {
			return (totalFitness = 0);
		}
	}

	public Generation generateNewGen() {
		Generation newGen = new Generation(minTableSize, maxTableSize, maxTableNum, groupNo, genSize, genAlg);

		for (int i = 0; i + 1 < genSize; i += 2) {
			Chromosome father1 = selectForBreeding();
			Chromosome father2 = selectForBreeding();

			Chromosome son1 = crossover(father1, father2);
			Chromosome son2 = crossover(father2, father1);

			newGen.chromosomes[i] = son1;
			newGen.chromosomes[i + 1] = son2;

			son1.calculateFitness();
			son2.calculateFitness();
		}

		if (newGen.chromosomes.length != genSize) {
			System.out.println("Warning, expected " + genSize + " but only generated " + newGen.chromosomes.length
					+ " chromosomes");
		}

		return newGen;
	}

	private Chromosome crossover(Chromosome father1, Chromosome father2) {
		Chromosome newChromo = new Chromosome(maxTableNum, groupNo, genAlg);

		long bitmask = (long) (Math.random() * Math.pow(2, father1.getSeats().length)); // UX-Crossover

		for (int i = 0; i < father1.getSeats().length; i++) {
			if (Math.random() < 0.001) {
				newChromo.getSeats()[i] = (int) (Math.random() * maxTableNum);
			} else {
				if (getBit(bitmask, i) == 0) {
					newChromo.getSeats()[i] = father1.getSeats()[i];
				} else {
					newChromo.getSeats()[i] = father2.getSeats()[i];
				}
			}
		}
		return newChromo;
	}

	private Chromosome selectForBreeding() {
		int nextElement = (int) (Math.random() * getTotalFitness());

		Chromosome selected = chromosomes[0];

		for (Chromosome chromosome : chromosomes) {
			selected = chromosome;
			nextElement -= chromosome.getFitness();
			if (nextElement <= 0) {
				break;
			}
		}

		return selected;
	}

	private long getBit(long n, int k) {
		return (n >> k) & 1;
	}

	public int getAvgFitness() {
		if (avgFitness != 0) {
			return avgFitness;
		} else {
			return (avgFitness = totalFitness / genSize);
		}
	}
}
