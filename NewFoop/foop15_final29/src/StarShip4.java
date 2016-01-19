import java.io.*;
import java.util.*;
public class StarShip4 extends Card {

	public int getNumber() {
		return 4;
	}

	public String getDescription() {
		return "StarShip4";
	}

	public void eff(Player actor, GameHolder field)  throws Exception {
		List<Card> handWithoutMe = new ArrayList<Card>(16);
		for (Player p : field.getPlayers())
			if (p.isAlive() && (actor != p))
				handWithoutMe.add(p.getHand());
		Card selected = CardUtility.selectCard(actor, handWithoutMe);
		if (9 == selected.getNumber()) {
			CardUtility.killCard(selected, field);
		}
		selected.seen(actor, field);// TODO:strange logic?
	}

}
