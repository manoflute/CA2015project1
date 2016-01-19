import java.io.*;
import java.util.*;
public class DeckFactory {

	public List<Card> create() {
		List<Card> result = new ArrayList<Card>(16);
		result.add(new StarShip1());
		result.add(new StarShip2());
		result.add(new StarShip3());
		result.add(new StarShip4());
		result.add(new StarShip5());
		for (int i = 0; i < 2; ++i) {
			result.add(new StarShip6());
		}
		for (int i = 0; i < 3; ++i) {
			result.add(new StarShip7());
			result.add(new StarShip8());
			result.add(new StarShipX());
		}
		Collections.shuffle(result);
		return result;
	}
}
