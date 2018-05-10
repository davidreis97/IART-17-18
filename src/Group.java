public class Group {
	public int[] attributes;
	public int numOfPeople;

	public Group(int numOfPeople, int attributeNum) {

		this.numOfPeople = numOfPeople;
		attributes = new int[attributeNum];

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = ((int) (Math.random() * numOfPeople * 2)) - numOfPeople; // Randomly between -numOfPeople or
																						// numOfPeople
		}
	}
}
