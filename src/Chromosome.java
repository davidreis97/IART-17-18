import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chromosome {

    public int[] getSeats() {
        return seats;
    }

    public void setSeats(int[] seats) {
        System.arraycopy(seats,0,this.seats,0,seats.length);
    }

    private int[] seats;

	public int maxTableNum;

    public int getFitness() {
        if(fitness == 0){
            System.out.println("WARNING - Fitness is yet to be calculated");
        }
        return fitness;
    }

    private int fitness;

	public Algorithm alg;

	public Chromosome(Chromosome oldChromosome){
		this.alg=oldChromosome.alg;
		this.maxTableNum=oldChromosome.maxTableNum;
		this.fitness=oldChromosome.fitness;

		this.seats= new int [oldChromosome.seats.length];
		for(int i=0;i<oldChromosome.seats.length; i++){
			this.seats[i]=oldChromosome.seats[i];
		}
	}

	public Chromosome(int maxTableNum, int groupNo, Algorithm alg) {
		seats = new int[groupNo];

		this.maxTableNum = maxTableNum;
		this.alg = alg;

		fitness = 0;
	}

	public void randomize() {
		for (int i = 0; i < seats.length; i++) {
			seats[i] = (int) (Math.random() * (maxTableNum));
		}

		this.calculateFitness();
	}

	public void randomizeValid(){
	    do {
	        fitness = 0;
            randomize();
        }while(fitness <= 1);
    }

	public void calculateFitness() {
	    int newFitness = 0;

        HashMap<Integer,ArrayList<Group>> tables = new HashMap<>();
        int tableScores[] = new int[maxTableNum];
		int tableSize[] = new int[maxTableNum];

		for (int i = 0; i < seats.length; i++) {
			tables.computeIfAbsent(seats[i], k -> new ArrayList<>());

			tables.get(seats[i]).add(alg.groups[i]);
			tableSize[seats[i]] += alg.groups[i].numOfPeople;
		}

		for(Map.Entry<Integer, ArrayList<Group>> entry : tables.entrySet()){
            for(Group g: entry.getValue()){
			    double affinity = g.getAffinityWith(entry.getValue());
                tableScores[entry.getKey()] += affinity;
				newFitness += affinity;
			}
		}

		for(int i = 0; i < tableSize.length; i++){
		    if(tableSize[i] > alg.maxTableSize){
		        newFitness -= tableScores[i] * 2;
            }
        }

		if (newFitness <= 0) {
			newFitness = 1;
		}

        if (fitness == newFitness) {
            System.out.println("WARNING - Unnecessary fitness calculation");
        }

		fitness = newFitness;
	}

	//For Hill Climbing Algorithm
    public Chromosome getImprovedNeighbour() {
	    Chromosome neighbour;

	    int max_tries = 10000000;

	    do{
            neighbour = getNeighbour();
            if(max_tries <= 0){
	            System.out.println("Could not find better solution");
	            return null;
            }
            max_tries--;
        }while(neighbour.fitness <= fitness);

	    return neighbour;
    }

    public Chromosome getValidNeighbour(){
		Chromosome neighbour;

		int max_tries = 10000000;

		do{
			neighbour = getNeighbour();
			if(max_tries <= 0){
				System.out.println("Could not find better solution");
				return null;
			}
			max_tries--;
		}while(neighbour.fitness <= 1);

		return neighbour;
	}

    public Chromosome getNeighbour(){
        Chromosome neighbour = new Chromosome(maxTableNum, seats.length, alg);
        neighbour.setSeats(this.seats);

        int moves = (int) ((Math.random() * (alg.largestJump-1)) + 1);

        for(;moves > 0;moves--){
            int movingGroup = (int) (Math.random() * seats.length);
            neighbour.seats[movingGroup] = (int) (Math.random() * (maxTableNum));
        }

        neighbour.calculateFitness();

        return neighbour;
    }
}
