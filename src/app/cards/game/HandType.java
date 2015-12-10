package app.cards.game;

public enum HandType {

	HIGH_CARD("High Card", 1), ONE_PAIR("One Pair", 2), TWO_PAIR("Two Pair", 3), THREE_OF_A_KIND("Three of a Kind", 4),
	STRAIGHT("Straight", 5), FLUSH("Flush", 6), FULL_HOUSE("Full House", 7), FOUR_OF_A_KIND("Four of a Kind", 8),
	STRAIGHT_FLUSH("Straight Flush", 9), ROYAL_FLUSH("Royal Flush", 10);
	
	private String name;
	private int value;
	
	HandType(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
