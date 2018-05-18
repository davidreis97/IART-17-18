import java.util.Random;

/*
* In this algorithm the probability of reaching the ideal point (X) will depend on the Temperature (T), the fitness of X(Fx)
* and the group of possible states Z.
*
* Pr(ending in X) =  exp(Fx/T) / Z
*
* */



public class SimulatedAnnealingAlgorithm  extends Algorithm{
    public Chromosome bestSolution;

    SimulatedAnnealingAlgorithm(int argMinTableSize, int argMaxTableSize, int argMaxTableNum, Group[] argGroups) {
        super(argMinTableSize, argMaxTableSize, argMaxTableNum, argGroups.length, argGroups);

        bestSolution = new Chromosome(maxTableNum, groupNo, this);
        bestSolution.randomize();
    }


    boolean acceptNewSolution(int currentFitness, int newFitness, double temperature){

        //In case the new solution is better that the current one, the jump is made.
        //The algorithm in this case is basically Hill Climbing
        if (newFitness > currentFitness) {
            return true;
        }

        //In the case the new solution is worse, we calculate the chance of the jump.
        //The temperature affects this probability directly; the bigger the temperature is, the more likely the jump will happen.
        //The difference between the solutions affects this probability inversely; the less ideal the new solution is, the less likely the jump is made.
        Random r = new Random();
        double rand = r.nextInt(1000) / 1000.0;
        if( Math.exp((newFitness - currentFitness) / temperature) >=  rand){
            return true;
        }

        return false;
    }


    public void start(double temperature, double coolingRate) {

        Chromosome currentSolution = new Chromosome(bestSolution);

        while(temperature>1){

            //Get New Possible Solution from Neighbourhood
            Random r = new Random();
            this.largestJump=r.nextInt(1000);
            Chromosome newSolution = currentSolution.getNeighbour();

            //Get Fitness of both solutions
            int currentFitness = currentSolution.getFitness();
            int newFitness = newSolution.getFitness();

            //Check If We Accept The New Solution
            if(acceptNewSolution(currentFitness, newFitness, temperature)){
                currentSolution=newSolution;
            }

            //Save the best Solution
            if(currentSolution.getFitness()>bestSolution.getFitness()){
                bestSolution=currentSolution;
            }

            //Reduce Temerature
            temperature *= 1 - coolingRate;
        }

    }

    public void printFinalSolution(long algTotalTime) {
        System.out.println("Simulated Annealing Algorithm Results:");
        System.out.println();

        long ms = algTotalTime % 1000;
        long s = algTotalTime / 1000;
        System.out.println("Code runtime - " + s + "." + ms + " seconds");

        System.out.println(printChromosomeInfo(bestSolution));
    }
}
