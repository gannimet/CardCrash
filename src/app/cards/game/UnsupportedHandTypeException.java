package app.cards.game;

public class UnsupportedHandTypeException extends RuntimeException {

	private static final long serialVersionUID = 5481984980164462600L;
	
	public UnsupportedHandTypeException(HandType handType) {
		super("Unsupported hand type: " + handType);
	}

}
