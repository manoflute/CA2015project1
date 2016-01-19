public class StarShip2 extends Card {
	public int getNumber() {
		return 2;
	}

	public String getDescription() {
		return "StarShip2";
	}

	public void eff(Player actor, GameHolder field) throws Exception {
		if (field.getDeck().isEmpty())
			return;
		int lastCardIndex = field.getDeck().size() - 1;
		Card seeing = field.getDeck().get(lastCardIndex);
		seeing.seen(actor, field);
		boolean swapping = CardUtility.YesOrNo(actor,
				"[S71]Do you want to swap?(0/1)");
		if (swapping) {
			field.tellOther(actor, "[O3." + actor.getName() + "." + seeing.getPosition().getDescription() + "]" + actor.getName() + " swaps hand card with " + seeing.getPosition().getDescription());
			CardUtility.swap(actor.getHand(), seeing);
		}
	}

}
