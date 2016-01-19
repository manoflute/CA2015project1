import java.io.*;
import java.util.*;
public class StarShip8 extends Card {

	public int getNumber() {
		return 8;
	}

	public String getDescription() {
		return "StarShip8";
	}

	public void eff(Player actor, GameHolder field)  throws Exception{
		List<Card> handWithoutMe = new ArrayList<Card>(16);
		for (Player p : field.getPlayers())
			if (p.isAlive() && (actor != p))
				handWithoutMe.add(p.getHand());
		Card selected = CardUtility.selectCard(actor, handWithoutMe);
		Player seenPlayer = ((HandPosition) selected.getPosition()).getPlayer();
		selected.seen(actor, field);
		if (!actor.isAlive() || !seenPlayer.isAlive())// TODO:logic
			return;
		boolean swapping = CardUtility.YesOrNo(actor,
				"[S71]Do you want to swap?(0/1)");
		if (swapping){
			field.tellOther(actor, "[O3." + actor.getName() + "." + selected.getPosition().getDescription() + "]" + actor.getName() + " swaps hand card with " + selected.getPosition().getDescription());
			CardUtility.swap(actor.getHand(), selected);
		}
	}

}
