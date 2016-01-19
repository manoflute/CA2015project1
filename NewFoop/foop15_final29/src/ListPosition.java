import java.util.List;

public abstract class ListPosition implements Position {

	private List<Card> heap;
	private int index;

	ListPosition(List<Card> _heap, int _index) {
		heap = _heap;
		index = _index;
	}

	public String getDescription() {
		return getHeapName() + "." + index;
	}

	public void setCard(Card card) {
		card.setPosition(this);
		heap.set(index, card);
	}

	protected abstract String getHeapName();

}
