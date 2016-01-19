import java.util.List;

public class RuinPosition extends ListPosition {

	RuinPosition(List<Card> ls, int index) {
		super(ls, index);
	}

	protected String getHeapName() {
		return "ruin";
	}	

}
