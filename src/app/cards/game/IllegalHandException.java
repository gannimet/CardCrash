package app.cards.game;

import java.util.Set;

import app.cards.Card;

/**
 * Indicates that a {@link Hand} has been attempted to create from an invalid
 * {@link Set} of {@link Card}s.
 */
public class IllegalHandException extends RuntimeException {

	private static final long serialVersionUID = 4828780799925445268L;

}
