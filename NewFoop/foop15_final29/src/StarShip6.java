public class StarShip6 extends Card {

	public int getNumber() {
		return 6;
	}

	public String getDescription() {
		return "StarShip6";
	}

	public void eff(Player actor, GameHolder field) throws Exception {
		if (field.getDeck().isEmpty())
			return;
		int lastCardIndex = field.getDeck().size() - 1;
		Card seeing = field.getDeck().get(lastCardIndex);
		seeing.seen(actor, field);
		field.getDeck().remove(lastCardIndex);
		field.addToRuin(seeing);
		actor.printMessage("[S5." + getName() + "."
				+ seeing.getPosition().getDescription() + "]"
				+ "You put a card to " + seeing.getPosition().getDescription());
		field.tellOther(actor, "[O4." + actor.getName() + "."
				+ seeing.getPosition().getDescription() + "]" + actor.getName()
				+ " puts a card to " + seeing.getPosition().getDescription());
		boolean shuffling = CardUtility.YesOrNo(actor,
				"[S72]Do you want to shuffle?(0/1)");
		if (shuffling)
			field.ruinShuffle();
	}

}
