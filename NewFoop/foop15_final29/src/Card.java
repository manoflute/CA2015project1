import java.io.*;
import java.util.*;
public abstract class Card {

	protected Position position;

	public void play(Player p, GameHolder g) throws Exception {
		p.addToDiscardZone(this);
		g.tellOther(p, "[O0." + p.getName() + "." + getName() + "]" + p.getName() + " plays " + getName() + " card");
		eff(p, g);
	}

	public void eff(Player p, GameHolder g) throws Exception {
	}

	public void seen(Player p, GameHolder g) throws Exception {
		g.tellOther(p, "[O1." + p.getName() + "." + position.getDescription() + "]" + p.getName() + " sees a card from " + position.getDescription());
		p.printMessage("[S3." + getName() + "." + position.getDescription() + "]" + "you see " + getName() + " from " + position.getDescription());
	}

	public abstract int getNumber();

	public String getName() {
		return getNumber() + "";
	}

	public abstract String getDescription();// TODO

	public Position getPosition() {
		return position;
	}

	public boolean playable() {
		return true;
	}

	public void setPosition(Position pos) {
		position = pos;
	}
}
