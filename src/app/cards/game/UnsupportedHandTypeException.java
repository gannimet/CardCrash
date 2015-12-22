package app.cards.game;

/**
 * Indicates that a {@link HandResult} was compared to another one with an invalid
 * {@link HandType}.
 */
public class UnsupportedHandTypeException extends RuntimeException {

	private static final long serialVersionUID = 5481984980164462600L;
	
	/**
	 * Creates a new {@code UnsupportedHandTypeException} with the {@link HandType} found in
	 * the {@link HandResult} to be compared
	 * @param handType the encountered {@code HandType}
	 */
	public UnsupportedHandTypeException(HandType handType) {
		super("Unsupported hand type: " + handType);
	}

}
