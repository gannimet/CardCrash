package app.cards;

/**
 * <p>Indicates that the deck of cards has been exhausted.</p>
 * <p>
 * This results from {@link Card#randomCard()} having been called more times than there are
 * cards in the deck without calling {@link Card#resetDeck()} in between.
 * </p>
 */
public class AllCardsDealtException extends RuntimeException {

	private static final long serialVersionUID = -7796692161542126976L;

}
