import java.util.ArrayList;

public class Table {
	public int tableNumber;
	public int minTableSize;
	public int maxTableSize;
	public ArrayList<Group> groups;
	public ArrayList<Integer> groupIDs;

	public Table(int tableNo, int minTableSize, int maxTableSize) {
		this.tableNumber = tableNo;
		this.minTableSize = minTableSize;
		this.maxTableSize = maxTableSize;
		groups = new ArrayList<Group>();
		groupIDs = new ArrayList<Integer>();
	}

	public int available(int currScore, int totalgroups) {
		int seats = 0;
		for (Group g : groups) {
			seats += g.numOfPeople;
		}

		if (seats > maxTableSize)
			return -totalgroups * 4;

		if (seats < minTableSize)
			return ++currScore;

		return currScore;
	}
}
