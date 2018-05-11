import java.util.ArrayList;

public class Group {
	public int[] attributes;
	public int numOfPeople;

	public Group(int numOfPeople, int attributeNum) {

		this.numOfPeople = numOfPeople;
		attributes = new int[attributeNum];

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = ((int) (Math.random() * attributeNum * 2)) - attributeNum; // Randomly between -attributeNum or
																						// attributeNum

			for (int j = 0; j < attributes.length; j++) {
				if (attributes[i] == attributes[j] && i != j) {
					i--;
					break;
				}
			}
		}
	}
	
	public int getAffinityWith(ArrayList<Group> groups) {
		int totalAfinity = 0;
		for (Group g : groups) {
			for (int i = 0; i < this.attributes.length; i++) {
				for (int j = 0; i < g.attributes.length; i++) {
					if (this.attributes[i] == g.attributes[j]) {
						totalAfinity++;
						break;
					}
				}
			}
		}
		return totalAfinity;
	}
}
