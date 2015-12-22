package app.cards.game;

import java.util.Set;

import app.cards.Card;
import app.cards.Rank;

/**
 * Indicates that an {@link NOfAKind} has been attempted to create from an invalid
 * {@link Set} of {@link Card}s (because their {@link Rank}s did not match).
 */
public class IllegalNOfAKindException extends RuntimeException {

	private static final long serialVersionUID = 9043111269248273521L;

}
