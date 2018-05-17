import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
	public double[] attributes;
	public int numOfPeople;

	public Group(int numOfPeople, int attributeNum) {

		this.numOfPeople = numOfPeople;
		attributes = new double[attributeNum];

		for (int i = 0; i < attributes.length; i++) {
			attributes[i] = (Math.random() * 2) - 1;
		}
	}
	
	public int getAffinityWith(ArrayList<Group> groups) {
	    if(groups.size() <= 1){
	        return 0;
        }

		double totalAffinity = 0;
		for (Group g : groups) {
			if(g != this){ //Prevent group from comparing to itself
				totalAffinity += getAffinityWithGroup(g);
			}
		}

		return (int)totalAffinity;
	}

	public double getAffinityWithGroup(Group g){
		double totalAffinity = 0;
		for (int i = 0; i < this.attributes.length; i++) {
			totalAffinity += 10 / (Math.max(0.01,Math.abs(this.attributes[i] - g.attributes[i])));
		}

		return totalAffinity;
	}
}
