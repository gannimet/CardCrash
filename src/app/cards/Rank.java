package app.cards;

public enum Rank {

	ACE("Ace", "A", 13), DEUCE("Deuce", "2", 1), TRAY("Tray", "3", 2), FOUR("Four", "4", 3),
	FIVE("Five", "5", 4), SIX("Six", "6", 5), SEVEN("Seven", "7", 6), EIGHT("Eight", "8", 7),
	NINE("Nine", "9", 8), TEN("Ten", "10", 9), JACK("Jack", "J", 10), QUEEN("Queen", "Q", 11),
	KING("King", "K", 12);
	
	private String name;
	private String shortName;
	private int value;
	
	Rank(String name, String shortName, int value) {
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
