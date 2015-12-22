package app.cards;

/**
 * Lists all possible card ranks and stores their human-readable names as well as an integer
 * value to enable comparisons. Values start at 1 (Deuce) and go up to 13 (Ace).
 */
public enum Rank {

	ACE("Ace", 'A', 13), DEUCE("Deuce", '2', 1), TRAY("Tray", '3', 2), FOUR("Four", '4', 3),
	FIVE("Five", '5', 4), SIX("Six", '6', 5), SEVEN("Seven", '7', 6), EIGHT("Eight", '8', 7),
	NINE("Nine", '9', 8), TEN("Ten", 'T', 9), JACK("Jack", 'J', 10), QUEEN("Queen", 'Q', 11),
	KING("King", 'K', 12);
	
	private String name;
	private char shortName;
	private int value;
	
	private Rank(String name, char shortName, int value) {
		this.name = name;
		this.shortName = shortName;
		this.value = value;
	}
	
	/**
	 * Get the full name of the rank
	 * @return Full name of the rank (e. g. "King", "Ace", "Deuce" etc.)
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the abbreviation character of the rank
	 * @return Abbreviation character of the rank (e. g. 'K', 'A', '2' etc.)
	 */
	public char getShortName() {
		return shortName;
	}
	
	/**
	 * Get the numeric value of the rank
	 * @return Numeric value of the rank, from 1 (Deuce) up to 13 (Ace)
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns the appropriate {@code Rank} for the supplied {@code shortcut},
	 * which is matched against the shortName property of the {@code Rank} objects.
	 * @param shortcut short name for which the {@code Rank} should be looked up (case-insensitive).
	 * Must be one of 'A' (Ace), '2' through '9' (Deuce through Nine), 'T' (Ten), 'J' (Jack),
	 * 'Q' (Queen) or 'K' (King).
	 * @return {@code Rank} object with matching {@code shortName}, or null for an invalid
	 * {@code shortcut}
	 */
	public static Rank fromShortcut(char shortcut) {
		switch (Character.toUpperCase(shortcut)) {
			case 'A':
				return Rank.ACE;
			case '2':
				return Rank.DEUCE;
			case '3':
				return Rank.TRAY;
			case '4':
				return Rank.FOUR;
			case '5':
				return Rank.FIVE;
			case '6':
				return Rank.SIX;
			case '7':
				return Rank.SEVEN;
			case '8':
				return Rank.EIGHT;
			case '9':
				return Rank.NINE;
			case 'T':
				return Rank.TEN;
			case 'J':
				return Rank.JACK;
			case 'Q':
				return Rank.QUEEN;
			case 'K':
				return Rank.KING;
			default:
				return null;
		}
	}
	
}
