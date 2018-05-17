import java.util.ArrayList;

public abstract class Algorithm{
    public Group[] groups;

    public int minTableSize;
    public int maxTableSize;
    public int maxTableNum;
    public int groupNo;
    public int genSize;
    public int largestJump;

    public Algorithm(int argMinTableSize, int argMaxTableSize, int argMaxTableNum, int argGroupNo, Group[] argGroups) {
        minTableSize = argMinTableSize;
        maxTableSize = argMaxTableSize;
        maxTableNum = argMaxTableNum;

        groupNo = argGroupNo;
        groups = argGroups;
    }

    public String printArray(double[] a) {
        String s = "[";
        for (double elem : a) {
            s += elem + "/";
        }
        s += "]";
        return s;
    }

    public String printArray(int[] a) {
        String s = "[";
        for (int elem : a) {
            s += elem + "/";
        }
        s += "]";
        return s;
    }

    public String printChromosomeInfo(Chromosome chromo){
        String s = "\nFITNESS - " + chromo.getFitness() + "\n";

        String tables[] = new String[maxTableNum];

        int tableSize[] = new int[maxTableNum];

        for (int i = 0; i < chromo.getSeats().length; i++) {
            tableSize[chromo.getSeats()[i]] += groups[i].numOfPeople;
        }

        for(int i = 0; i < tables.length; i++){
            if(tableSize[i] > 0){
                tables[i] = "Table " + i + " (" + tableSize[i] + "/" + maxTableSize + ") :\n";
            }else{
                tables[i] = "";
            }
        }

        for (int gno = 0; gno < chromo.getSeats().length; gno++) {
            int table = chromo.getSeats()[gno];
            tables[table] += "Group " + gno + " (" + groups[gno].numOfPeople + ") : " + printArray(groups[gno].attributes) + "\n";
        }

        for(String table : tables){
            s += table;
        }

        return s;
    }
}
