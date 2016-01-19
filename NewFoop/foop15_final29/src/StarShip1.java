public class StarShip1 extends Card {

	public int getNumber() {
		return 1;
	}

	public String getDescription() {
		return "StarShip1";
	}

	public void seen(Player p, GameHolder g) throws Exception {
		super.seen(p, g);
		if (position instanceof HandPosition)
			CardUtility.killCard(this, g);
	}

}
