package app.cards.game;

import java.util.Set;

import app.cards.Card;

/**
 * Indicates that a {@link Flush} has been attempted to create with an invalid
 * {@link Set} of {@link Card}s.
 */
public class IllegalFlushException extends RuntimeException {

	private static final long serialVersionUID = 8419120466554155800L;
	
	/**
	 * Creates a new {@code IllegalFlushException} with the supplied message
	 * @param message a message describing the exception
	 */
	public IllegalFlushException(String message) {
		super(message);
	}

}
