import java.io.*;

public class RandomGroupGenerator implements Serializable{
	public Group[] groups;

	public int maxGroupSize;
	public int minGroupSize;
	public int groupNo;

	public int attributeNum;

	public RandomGroupGenerator(int argMaxGroupSize, int argMinGroupSize, int argGroupNo, int argAttributeNum){

		maxGroupSize = argMaxGroupSize;
		minGroupSize = argMinGroupSize;
		groupNo = argGroupNo;

		attributeNum = argAttributeNum;

		groups = new Group[groupNo];

		generateRandomGroups();

	}

	public void generateRandomGroups() {
		for (int i = 0; i < groupNo; i++) {
			int nextGroupSize = (int) (minGroupSize + (Math.random() * (maxGroupSize - minGroupSize)));

			groups[i] = new Group(nextGroupSize, attributeNum);
		}
	}

	public void save(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
	}

	public static RandomGroupGenerator load(String filename){
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            RandomGroupGenerator rgg = (RandomGroupGenerator) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("File " + filename + " loaded");
            System.out.println("GroupNo: " + rgg.groupNo);
            System.out.println("MinGroupSize: " + rgg.minGroupSize);
            System.out.println("MaxGroupSize: " + rgg.maxGroupSize);
            return rgg;
        } catch (Exception i) {
            i.printStackTrace();
            System.exit(1);
        }

        return null;
    }

}
