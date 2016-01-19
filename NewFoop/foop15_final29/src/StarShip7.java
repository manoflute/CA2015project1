public class StarShip7 extends Card {

	public int getNumber() {
		return 7;
	}

	public String getDescription() {
		return "StarShip7";
	}

	public void eff(Player actor, GameHolder field)  throws Exception {
		Card selected = CardUtility.selectCard(actor, field.getRuin());
		selected.seen(actor,field);
		boolean swapping = CardUtility.YesOrNo(actor,
				"[S71]Do you want to swap?(0/1)");
		if (swapping){
			field.tellOther(actor, "[O3." + actor.getName() + "." + selected.getPosition().getDescription() + "]" + actor.getName() + " swaps hand card with " + selected.getPosition().getDescription());
			CardUtility.swap(actor.getHand(), selected);
		}
	}

}
