public class HandPosition implements Position {

	Player player;

	HandPosition(Player p) {
		player = p;
	}

	public String getDescription() {
		return player.getName();
	}

	public void setCard(Card card) {
		player.setHand(card);
	}

	//
	public Player getPlayer() {
		return player;
	}

}
