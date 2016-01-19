import java.io.*;
import java.util.*;

public class DiscardZonePosition extends ListPosition {

	Player player;

	DiscardZonePosition(Player player, int index) {
		super(player.getDiscardZone(), index);
		this.player = player;
	}

	protected String getHeapName() {
		return player.getName();
	}

}
