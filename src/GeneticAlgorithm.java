import java.util.ArrayList;

public class GeneticAlgorithm {
    private Group[] groups;

    private ArrayList<Generation> generations;

    private int minTableSize;
    private int maxTableSize;
    private int maxTableNum;
    private int maxGroupSize;
    private int minGroupSize;
    private int groupNo;
    private int genSize;

    public GeneticAlgorithm(int minTableSize, int maxTableSize, int maxTableNum, int maxGroupSize, int minGroupSize, int groupNo, int genSize){
        this.minTableSize = minTableSize;
        this.maxTableSize = maxTableSize;
        this.maxTableNum = maxTableNum;

        this.maxGroupSize = maxGroupSize;
        this.minGroupSize = minGroupSize;
        this.groupNo = groupNo;
        this.genSize = genSize;

        groups = new Group[groupNo];
        generateRandomGroups();

        generations = new ArrayList<>();
        generateRandomGeneration();
    }

    private void generateRandomGroups() {
        for(int i = 0; i < groupNo; i++){
            int nextGroupSize = (int) (minGroupSize + (Math.random() * (maxGroupSize - minGroupSize)));

            groups[i] = new Group(nextGroupSize);
        }
    }

    private void generateRandomGeneration() {
        Generation gen = new Generation(minTableSize,maxTableSize,maxTableNum,groupNo,genSize);
        gen.randomize();
    }


}
