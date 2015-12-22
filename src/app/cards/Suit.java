package app.cards;

/**
 * Lists all possible card suits and stores their human-readable names as well as an integer
 * value to enable comparisons. Values start at 1 (Clubs) and go up to 4 (Spades).
 */
public enum Suit {

	CLUBS("Clubs", 'C', 1), DIAMONDS("Diamonds", 'D', 2), HEARTS("Hearts", 'H', 3), SPADES("Spades", 'S', 4);
	
	private String name;
	private char shortName;
	private int value;
	
	private Suit(String name, char shortName, int value) {
		this.name = name;
		this.shortName = shortName;
		this.value = value;
	}

	/**
	 * The full name of the suit
	 * @return Full name of the suit (e. g. "Clubs", "Diamonds", "Hearts" or "Spades")
	 */
	public String getName() {
		return name;
	}

	/**
	 * The abbreviation character of the suit
	 * @return Abbreviation character of the suit (e. g. 'C', 'D', 'H' or 'S')
	 */
	public char getShortName() {
		return shortName;
	}

	/**
	 * The numeric value of the suit
	 * @return Numeric value of the suit, from 1 (Clubs) up to 4 (Spades)
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns the appropriate {@code Suit} for the supplied {@code shortcut},
	 * which is matched against the shortName property of the {@code Suit} objects.
	 * @param shortcut short name for which the {@code Suit} should be looked up (case-insensitive).
	 * Must be one of 'C' (Clubs), 'D' (Diamonds), 'H' (Hearts) or 'S' (Spades).
	 * @return {@code Suit} object with matching {@code shortName}, or null for an invalid
	 * {@code shortcut}
	 */
	public static Suit fromShortcut(char shortcut) {
		switch (Character.toUpperCase(shortcut)) {
			case 'C':
				return Suit.CLUBS;
			case 'D':
				return Suit.DIAMONDS;
			case 'H':
				return Suit.HEARTS;
			case 'S':
				return Suit.SPADES;
			default:
				return null;
		}
	}
	
}
