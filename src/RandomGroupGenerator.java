import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class RandomGroupGenerator {
	public Group[] groups;

	public int maxGroupSize;
	public int minGroupSize;
	public int groupNo;

	public int attributeNum;

	private PrintWriter writer;

	public RandomGroupGenerator(int argMaxGroupSize, int argMinGroupSize, int argGroupNo) {

		maxGroupSize = argMaxGroupSize;
		minGroupSize = argMinGroupSize;
		groupNo = argGroupNo;

		attributeNum = 5;

		groups = new Group[groupNo];
		
		generateRandomGroups();

		saveGroupDetails("groups.txt");
	}

	public void generateRandomGroups() {
		for (int i = 0; i < groupNo; i++) {
			int nextGroupSize = (int) (minGroupSize + (Math.random() * (maxGroupSize - minGroupSize)));

			groups[i] = new Group(nextGroupSize, attributeNum);
		}
	}

	public void saveGroupDetails(String groupDetails) {
		File file = new File(groupDetails);

		try {
			file.createNewFile();
			writer = new PrintWriter(groupDetails, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < groups.length; i++) {
			int groupNumber = i+1;
			String groupString = "Group " + groupNumber;
			writer.println(groupString);
			
			String size = "Size: " + groups[i].numOfPeople + " people";
			writer.println(size);
			
			String attributes  = "Attributes[ ";
			for (int j = 0; j < groups[i].attributes.length; j++) {
				attributes = attributes + groups[i].attributes[j] + " ";
				if (j+1 < groups[i].attributes.length) attributes = attributes + ", ";
			}
			attributes = attributes + "]";
			writer.println(attributes);
			writer.println();
		}
		
		writer.close();
	}

}
