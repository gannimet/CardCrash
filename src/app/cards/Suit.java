package app.cards;

public enum Suit {

	CLUBS("Clubs", "C", 1), DIAMONDS("Diamonds", "D", 2), HEARTS("Hearts", "H", 3), SPADES("Spades", "S", 4);
	
	private String name;
	private String shortName;
	private int value;
	
	private Suit(String name, String shortName, int value) {
		this.name = name;
		this.shortName = shortName;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public int getValue() {
		return value;
	}
	
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
