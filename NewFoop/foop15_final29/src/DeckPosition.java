import java.io.*;
import java.util.*;
public class DeckPosition extends ListPosition {

	DeckPosition(List<Card> ls, int index) {
		super(ls, index);
	}

	protected String getHeapName() {
		return "deck";
	}
}
