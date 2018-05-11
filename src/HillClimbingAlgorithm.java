import java.io.Serializable;
import java.util.ArrayList;

public class HillClimbingAlgorithm implements Serializable {

	public Group[] groups;

	public int minTableSize;
	public int maxTableSize;
	public int maxTableNum;
	public int currTableNum;

	public int[] currentSolution;
	public int currentSolutionScore;

	public HillClimbingAlgorithm(int argMinTableSize, int argMaxTableSize, int argMaxTableNum, Group[] argGroups) {
		minTableSize = argMinTableSize;
		maxTableSize = argMaxTableSize;
		maxTableNum = argMaxTableNum;
		currTableNum = 1;

		groups = argGroups;

		currentSolution = new int[groups.length];
		for (int i = 0; i < currentSolution.length; i++) {
			currentSolution[i] = 0;
		}
		currentSolutionScore = -groups.length * 4;
	}

	public void start(int max_iterations) {
		int iterationsCounter = 0;
		int[] nextSolution;
		do {
			nextSolution = obtainNextSolution();

			if (hasBetterSolution(nextSolution))
				currentSolution = nextSolution;
			iterationsCounter++;

			// printCurrSolution(iterationsCounter);
		} while (iterationsCounter < max_iterations);

	}

	public int[] obtainNextSolution() {
		int number = (int) (Math.random() * 1000);
		if (number < 333) {
			return obtainRandom(); // arbitrary chance of changing randomly
		} else if (number < 666) {
			return obtainSwitch(); // arbitrary chance of changing switching tables
		}
		return obtainNeighbour();
	}

	public int[] obtainNeighbour() {
		int[] nextSolution = new int[currentSolution.length];
		int randomGroup = (int) (Math.random() * groups.length);
		int randomSign = (int) Math.pow(-1, (int) (Math.random() * 2));
		int counter = 0;

		for (int i = 0; i < currentSolution.length; i++) {
			if (i == randomGroup)
				nextSolution[i] = currentSolution[randomGroup] + randomSign;
			else
				nextSolution[i] = currentSolution[i];

			if (nextSolution[i] == currentSolution[randomGroup]) {
				counter++;
			}
		}

		if (counter == 0) {
			for (int i = 0; i < nextSolution.length; i++) {
				if (nextSolution[i] > currentSolution[randomGroup])
					nextSolution[i]--;
			}
		}

		return nextSolution;
	}

	public int[] obtainSwitch() {
		int[] nextSolution = new int[currentSolution.length];
		int randomGroup1 = (int) (Math.random() * currentSolution.length);
		int randomGroup2 = randomGroup1;
		while (randomGroup1 == randomGroup2) {
			randomGroup2 = (int) (Math.random() * currentSolution.length);
		}

		for (int i = 0; i < currentSolution.length; i++) {
			if (i == randomGroup1)
				nextSolution[i] = currentSolution[randomGroup2];
			else if (i == randomGroup2)
				nextSolution[i] = currentSolution[randomGroup1];
			else
				nextSolution[i] = currentSolution[i];
		}

		return nextSolution;
	}

	public int[] obtainRandom() {
		int[] nextSolution = new int[currentSolution.length];
		int randomTable = 1 + (int) (Math.random() * currTableNum);
		int randomGroup = (int) (Math.random() * groups.length);

		for (int i = 0; i < currentSolution.length; i++) {
			if (i == randomGroup)
				nextSolution[i] = randomTable;
			else
				nextSolution[i] = currentSolution[i];
		}

		return nextSolution;
	}

	public boolean hasBetterSolution(int[] nextSol) {
		/*
		 * Score system: group has no table (assigned to table 0): -4 points group has a
		 * table: 0 points group sharing table (gst): 2 point gst with X affinity: X*4
		 * points group has table, but table doesnt have enough people: 1 point
		 */
		ArrayList<Table> tables = new ArrayList<Table>();
		for (int i = 1; i < maxTableNum + 1; i++) {
			tables.add(new Table(i, minTableSize, maxTableSize));
		}
		int nextSolutionScore = 0;
		int max = 0;

		for (int i = 0; i < nextSol.length; i++) {
			if (nextSol[i] > max)
				max = nextSol[i];

			if (nextSol[i] >= 0 && nextSol[i] <= maxTableNum) {
				if (nextSol[i] == 0)
					nextSolutionScore -= 4;
				else {
					switch (tables.get(nextSol[i] - 1).groups.size()) {
					case 0:
						break;
					case 1:
						nextSolutionScore += 2 * 2;
						nextSolutionScore += 2 * groups[i].getAffinityWith(tables.get(nextSol[i] - 1).groups);
						break;
					default:
						nextSolutionScore += 2;
						nextSolutionScore += 4 * groups[i].getAffinityWith(tables.get(nextSol[i] - 1).groups);
						break;
					}
					tables.get(nextSol[i] - 1).groups.add(this.groups[i]);

					nextSolutionScore = tables.get(nextSol[i] - 1).available(nextSolutionScore, nextSol.length);
				}

			} else {
				nextSolutionScore = -nextSol.length * 4;
				break;
			}
		}

		if (nextSolutionScore <= currentSolutionScore)
			return false;

		if (max >= currTableNum)
			currTableNum = max + 1;
		currentSolutionScore = nextSolutionScore;
		return true;
	}

	public void printSol(int[] sol) {
		System.out.print("Solution [ ");

		for (int i = 0; i < sol.length; i++) {
			System.out.print(sol[i]);
			if (i != sol.length - 1)
				System.out.print(", ");
		}

		System.out.print(" ]");
		System.out.println();
	}

	public void printFinalSolution(long genAlgTotalTime) {
		System.out.println("Hill Climbing Algorithm Results:");
		System.out.println();

		long ms = genAlgTotalTime % 1000;
		long s = genAlgTotalTime / 1000;
		System.out.println("	Code runtime - " + s + "." + ms + " seconds");
		System.out.println();

		System.out.println("	Table disposition: (maximum of " + this.maxTableNum + " with " + this.minTableSize
				+ " to " + this.maxTableSize + " seats)");
		ArrayList<Table> tables = new ArrayList<Table>();
		for (int i = 1; i < maxTableNum + 1; i++) {
			tables.add(new Table(i, minTableSize, maxTableSize));
		}
		for (int i = 0; i < this.currentSolution.length; i++) {
			tables.get(currentSolution[i] - 1).groups.add(this.groups[i]);
			tables.get(currentSolution[i] - 1).groupIDs.add(i + 1);
		}
		for (Table t : tables) {
			if (t.groups.isEmpty())
				break;

			System.out.println();
			System.out.println("		Table " + t.tableNumber + ":");
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

		System.out.println();
		System.out.println("========================================================================");
		System.out.println();

	}
}
