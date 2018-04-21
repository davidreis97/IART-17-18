public class Generation {

    private Chromosome[] chromosomes;

    private int minTableSize;
    private int maxTableSize;
    private int maxTableNum;
    private int groupNo;
    private int genSize;

    public Generation(int minTableSize, int maxTableSize, int maxTableNum, int groupNo, int genSize) {
        this.minTableSize = minTableSize;
        this.maxTableSize = maxTableSize;
        this.maxTableNum = maxTableNum;
        this.groupNo = groupNo;
        this.genSize = genSize;

        chromosomes = new Chromosome[genSize];
    }

    public void randomize() {

    }
}
