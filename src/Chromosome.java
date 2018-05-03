public class Chromosome {

    public int []seats;

    public int maxTableNum;

    private int fitness;

    public GeneticAlgorithm genAlg;

    public Chromosome(int maxTableNum, int groupNo, GeneticAlgorithm genAlg){
        seats = new int[groupNo];

        this.maxTableNum = maxTableNum;
        this.genAlg = genAlg;

        fitness = 0;
    }

    public void randomize(){
        for(int i = 0; i < seats.length; i++){
            seats[i] = (int) (Math.random() * (maxTableNum));
        }
    }

    public int getFitness(){

        if(fitness != 0){
            return fitness;
        }

        int [][]fitnessKeeper = new int[maxTableNum][genAlg.attributeNum];
        int []tableSizeKeeper = new int[maxTableNum];

        for(int i = 0; i < seats.length; i++) {
            for(int j = 0; j < fitnessKeeper[seats[i]].length; j++){
                fitnessKeeper[seats[i]][j] += genAlg.groups[i].attributes[j];
            }
            tableSizeKeeper[seats[i]] +=  genAlg.groups[i].numOfPeople;
        }

        for(int i = 0; i < fitnessKeeper.length; i++){
            for(int j = 0; j < fitnessKeeper[i].length; j++){
                fitness += Math.abs(fitnessKeeper[i][j]);
            }
            if(tableSizeKeeper[i] > genAlg.maxTableSize){
                fitness -= (tableSizeKeeper[i] - genAlg.maxTableSize) * 10; //TODO - This value needs testing and adjusting
            }
        }

        if(fitness > 0){
            return fitness;
        }else{
            return (fitness=0);
        }
    }
}
