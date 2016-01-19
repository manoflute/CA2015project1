public class StarShipX extends Card {

	public int getNumber() {
		return 9;
	}

	public String getDescription() {
		return "StarShipX";
	}

	public String getName() {
		return "X";
	}

	public void seen(Player actor, GameHolder field)  throws Exception{
		super.seen(actor, field);
		if (position instanceof HandPosition) {
			Player thePlayer = ((HandPosition) position).getPlayer();
			Card snatch = actor.getHand();
			actor.printMessage("[S6." + getName() + "." + snatch.position.getDescription() + "]" + "You get a card from " + snatch.getPosition().getDescription());
			field.announce("[A5." + thePlayer.getName() + "." + this.getName() + "]" + thePlayer.getName() + " discards " + this.getName());
			field.tellOther(actor, "[O5." + thePlayer.getName() + "." + snatch.getPosition().getDescription() + "]" + thePlayer.getName() + " gets a card from " + snatch.getPosition().getDescription());
			thePlayer.addToDiscardZone(this);
			thePlayer.setHand(snatch);
			actor.setHand(null);
			actor.die(field);
		}
	}
}
