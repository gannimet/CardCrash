package app.cards.game;

import app.cards.Card;
import app.cards.Rank;

/**
 * Indicates that an invalid {@link Card} (with non-matching {@link Rank}) has been
 * attempted to add to an {@link NOfAKind}.
 */
class IllegalCardException extends RuntimeException {

	private static final long serialVersionUID = -2467988980588569443L;

}
