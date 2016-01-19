import java.io.*;
import java.util.*;

public class Player {

	private String name;
	private Card hand;
	private List<Card> discardZone;
	private boolean alive;
	private DataInputStream socketInput;
	private DataOutputStream socketOutput;

	public DataInputStream getSocketInput() {
		return socketInput;
	}

	public void setSocketInput(DataInputStream socketInput) {
		this.socketInput = socketInput;
	}

	public DataOutputStream getSocketOutput() {
		return socketOutput;
	}

	public void setSocketOutput(DataOutputStream socketOutput) {
		this.socketOutput = socketOutput;
	}

	Player(String _name, Card first) {
		name = _name;
		setHand(first);
		discardZone = new LinkedList<Card>();
		alive = true;
	}

	public String getName() {
		return name;
	};

	public Card getHand() {
		return hand;
	}

	public List<Card> getDiscardZone() {
		return discardZone;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setHand(Card c) {
		if (null != c)
			c.setPosition(new HandPosition(this));
		hand = c;
	}

	public Card turnPlay(Card drawn) throws Exception {
		List<Card> selections = new ArrayList<Card>(2);
		List<String> descriptions = new ArrayList<String>(2);
		String handName = "hand." + hand.getName();
		String drawnName = "drawn." + drawn.getName();
		if (hand.playable()) {
			descriptions.add(handName);
			selections.add(hand);
		}
		if (drawn.playable()) {
			descriptions.add(drawnName);
			selections.add(drawn);
		}// TODO: map
		String msg = "";
		// msg += handName + "\n";
		msg += "[S2." + drawn.getName() + "]You draw " + drawn.getName() + "\n";
		msg += "choose a card:";
		for (int i = 0; i < selections.size(); ++i) {
			msg += (" (" + (i) + ")" + descriptions.get(i));
		}
		int selectIndex = CardUtility.selectInRange(this, selections.size(),
				msg);
		Card selected = selections.get(selectIndex);
		if (hand == selected)
			setHand(drawn);
		return selected;
	}

	public Card findTreasure(List<Card> selections) throws Exception {
		printMessage("[S4]Go to find out the teasure(5)!");
		return CardUtility.selectCard(this, selections);
	}

	public void die(GameHolder game)  throws Exception{
		game.announce("[A4." + name + "]" + name + " dies!");// TODO
		alive = false;
		if (hand != null) {
			game.announce("[A5." + name + "." + hand.getName() + "]" + name
					+ " discards " + hand.getName());
			addToDiscardZone(hand);
			hand = null;
		}
	}

	public void addToDiscardZone(Card card) {
		card.setPosition(new DiscardZonePosition(this, discardZone.size()));
		discardZone.add(card);
	}

	public void printMessage(String msg) throws Exception {
		socketOutput.writeUTF(msg);
		System.out.println("Send to " + getName() + ": " + msg);
	}

	public int receiveMessage() throws Exception {
		int input = socketInput.readInt();
		System.out.println("Receive from " + getName() + ": " + input);
		return input;
	}
}
