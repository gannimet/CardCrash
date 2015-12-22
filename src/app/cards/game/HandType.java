package app.cards.game;

/**
 * List all possible poker hand types with their names and an integer value to enable comparisons.
 * Values start at 1 (High card) and go up to 10 (Royal flush).
 */
public enum HandType {

	HIGH_CARD("High Card", 1), ONE_PAIR("One Pair", 2), TWO_PAIR("Two Pair", 3), THREE_OF_A_KIND("Three of a Kind", 4),
	STRAIGHT("Straight", 5), FLUSH("Flush", 6), FULL_HOUSE("Full House", 7), FOUR_OF_A_KIND("Four of a Kind", 8),
	STRAIGHT_FLUSH("Straight Flush", 9), ROYAL_FLUSH("Royal Flush", 10);
	
	private String name;
	private int value;
	
	private HandType(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * The full name of the {@code HandType}
	 * @return Full name of the {@code HandType}
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * The numeric value of the {@code HandType}
	 * @return Numeric value of the {@code HandType}, from 1 (High card) up to 10 (Royal flush)
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns the full name of the {@code HandType}
	 * @return The full name of the {@code HandType}
	 */
	@Override
	public String toString() {
		return name;
	}
	
}
