import java.util.ArrayList;

public class Group {
    ArrayList<Person> people;

    public Group(int numOfPeople){
        people = new ArrayList<>();

        for(int i = 0; i < numOfPeople; i++){
            people.add(new Person((int) (Math.random() * 100)));
        }
    }
}
