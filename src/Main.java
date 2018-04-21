public class Main {
    public static void main(String []args) {
        int minTableSize = Integer.parseInt(args[0]);
        int maxTableSize = Integer.parseInt(args[1]);
        int maxTableNum = Integer.parseInt(args[2]);

        int maxGroupSize = maxTableSize;
        int minGroupSize = 0;
        int groupNum = Integer.parseInt(args[3]);

        int generationSize = Integer.parseInt(args[4]);

        GeneticAlgorithm genAlg = new GeneticAlgorithm(minTableSize,maxTableSize,maxTableNum,maxGroupSize,minGroupSize,groupNum,generationSize);
    }
}
