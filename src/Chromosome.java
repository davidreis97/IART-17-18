import java.util.ArrayList;
import java.util.HashMap;

public class Chromosome {

    public int[] getSeats() {
        return seats;
    }

    public void setSeats(int[] seats) {
        fitness = 1;
        this.seats = seats;
    }

    private int[] seats;

	public int maxTableNum;

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

		fitness = 1;
	}

	public void randomize() {
		for (int i = 0; i < seats.length; i++) {
			seats[i] = (int) (Math.random() * (maxTableNum));
		}
	}

	public void randomizeValid(){
	    do {
            randomize();
        }while(getFitness() <= 1);
    }

	public int getFitness() {
		if (fitness > 1) {
			return fitness;
		}

		HashMap<Integer,ArrayList<Group>> tables = new HashMap<>();
		int tableSize[] = new int[maxTableNum];

		for (int i = 0; i < seats.length; i++) {
			tables.computeIfAbsent(seats[i], k -> new ArrayList<>());

			tables.get(seats[i]).add(alg.groups[i]);
			tableSize[seats[i]] += alg.groups[i].numOfPeople;
		}

		for (ArrayList<Group> al : tables.values()) {
			for(Group g: al){
			    double affinity = g.getAffinityWith(al);
				fitness += affinity;
			}
		}

		for(int a: tableSize){
		    if(a > alg.maxTableSize){
		        return (fitness = 1);
            }
        }

		if (fitness > 0) {
			return fitness;
		} else {
			return (fitness = 1);
		}
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
        }while(neighbour.getFitness() <= getFitness());

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

        return neighbour;
    }
}
