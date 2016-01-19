import java.io.*;
import java.util.*;
public class StarShip3 extends Card {

	public int getNumber() {
		return 3;
	}

	public String getDescription() {
		return "StarShip3";
	}

	public void eff(Player actor, GameHolder field) throws Exception {
		List<Card> ruin_handWithoutMe = new ArrayList<Card>(16);
		ruin_handWithoutMe.addAll(field.getRuin());
		for (Player p : field.getPlayers())
			if (p.isAlive() && (actor != p))
				ruin_handWithoutMe.add(p.getHand());
		Card selected = CardUtility.selectCard(actor, ruin_handWithoutMe);
		selected.seen(actor, field);
		if (5 == selected.getNumber()) {
			field.setWinner(actor);
		}
	}

}
