import java.io.*;
import java.util.*;
public class CardUtility {

	public static void swap(Card A, Card B) {
		Position ownerA = A.getPosition();
		Position ownerB = B.getPosition();
		ownerA.setCard(B);
		ownerB.setCard(A);
	}

	public static boolean YesOrNo(Player actor, String requestMsg) throws Exception {
		int input;
		do {
			actor.printMessage(requestMsg);
			actor.printMessage("Please enter an integer: ");
			input = actor.receiveMessage();
		} while (input != 0 && input != 1);
		return (1 == input) ? true : false;
	}

	public static void killCard(Card card, GameHolder game) throws Exception{
		Player killed = ((HandPosition) card.getPosition()).getPlayer();
		killed.die(game);
	}

	public static int selectInRange(Player actor, int range, String msg) throws Exception {
		int input;
		do {
				actor.printMessage(msg);
				actor.printMessage("Please enter an integer: ");
				input = actor.receiveMessage();
		} while (0 > input || input >= range);
		return input;
	}

	public static Card selectCard(Player actor, List<Card> selections) throws Exception {
		String tmp = "]select from:";
		String msg = "[S8";
		int count = 0;
		for (Card printing : selections) {
			String des = printing.getPosition().getDescription();
			msg = String.join(".", msg, des);
			tmp += (" (" + (count++) + ")" + des);
		}
		msg += tmp;
		return selections.get(selectInRange(actor, selections.size(), msg));
	}
}
