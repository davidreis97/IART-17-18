import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GeneticAlgorithm implements Serializable {
	public Group[] groups;

	public int minTableSize;
	public int maxTableSize;
	public int maxTableNum;
	public int groupNo;
	public int genSize;

	public Generation currGen;

	public GeneticAlgorithm(int argMinTableSize, int argMaxTableSize, int argMaxTableNum, int argGroupNo,
			int argGenSize, Group[] argGroups) {
		minTableSize = argMinTableSize;
		maxTableSize = argMaxTableSize;
		maxTableNum = argMaxTableNum;

		groupNo = argGroupNo;
		genSize = argGenSize;
		groups = argGroups;

		currGen = new Generation(minTableSize, maxTableSize, maxTableNum, groupNo, genSize, this);
		currGen.randomize();
	}

	public void start(long threshold, long maxGens) {
		long score = 0;
		int generations = 0;
		do {
			generations++;
			score = currGen.getTotalFitness();
			/*
			 * System.out.println("Generation " + generations + ": (TF=" + score + "|AF=" +
			 * currGen.getAvgFitness() + "|BCF=" + currGen.bestChromo.getFitness() + ")");
			 * // TS - Total Score BCF - Best Chromosome
			 */ // Fitness

			currGen = currGen.generateNewGen();
		} while ((score < threshold || threshold == -1) && (maxGens > generations || maxGens == -1));
	}

	public void printArray(int[] a) {
		System.out.print("[");
		for (int elem : a) {
			System.out.print(elem + "/");
		}
		System.out.println("]");
	}

	public void printFinalSolution(long genAlgTotalTime) {
		System.out.println("Genetic Algorithm Results:");
		System.out.println();

		long ms = genAlgTotalTime % 1000;
		long s = genAlgTotalTime / 1000;
		System.out.println("	Code runtime - " + s + "." + ms + " seconds");
		System.out.println();
		ArrayList<Chromosome> diffSolutions = new ArrayList<Chromosome>();

		for (int k = 0; k < this.genSize; k++) {
			boolean canAdd = true;
			for (Chromosome c : diffSolutions) {
				if (Arrays.equals(c.seats, this.currGen.chromosomes[k].seats)) {
					canAdd = false;
					break;
				}
			}
			if (canAdd)
				diffSolutions.add(this.currGen.chromosomes[k]);
		}

		System.out.println("	Different Solutions: " + diffSolutions.size());

		int index = 1;
		for (Chromosome c : diffSolutions) {
			System.out.println();
			System.out.println("				-= POSSIBILITY " + (index++) + " =-");
			System.out.println();

			System.out.println("	Table disposition: (maximum of " + this.maxTableNum + " with " + this.minTableSize
					+ " to " + this.maxTableSize + " seats)");
			ArrayList<Table> tables = new ArrayList<Table>();
			for (int i = 1; i < maxTableNum + 1; i++) {
				tables.add(new Table(i, minTableSize, maxTableSize));
			}
			for (int i = 0; i < c.seats.length; i++) {
				tables.get(c.seats[i]).groups.add(this.groups[i]);
				tables.get(c.seats[i]).groupIDs.add(i + 1);
			}
			
			int tableCounter = 1;
			for (Table t : tables) {
				if (!t.groups.isEmpty()) {
					System.out.println();
					System.out.println("		Table " + tableCounter++ + ":");
					int i = 0;
					for (Group g : t.groups) {
						System.out.print(
								"	Group " + t.groupIDs.get(i++) + " - " + g.numOfPeople + " people		Interests: [ ");
						for (int j = 0; j < g.attributes.length; j++) {
							System.out.print(g.attributes[j]);
							if (j + 1 == g.attributes.length)
								System.out.println(" ]");
							else
								System.out.print(", ");
						}
					}
				}
			}

		}

		System.out.println();
		System.out.println("========================================================================");
		System.out.println();

	}
}
