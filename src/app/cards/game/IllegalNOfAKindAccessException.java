package app.cards.game;

/**
 * Indicates that {@link NOfAKind#getNBestCardsAsNewNOfAKind(int)} has been called with a
 * parameter value greater than the number of cards in that {@link NOfAKind}.
 */
public class IllegalNOfAKindAccessException extends RuntimeException {

	private static final long serialVersionUID = -7673783023632013547L;

}
