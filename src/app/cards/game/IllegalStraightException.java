package app.cards.game;

import java.util.List;

import app.cards.Card;

/**
 * Indicates that a {@link Straight} has been attempted to create from an invalid
 * {@link List} of {@link Card}s (with a length other than 5).
 */
public class IllegalStraightException extends RuntimeException {

	private static final long serialVersionUID = 6319609970614274610L;
	
	/**
	 * Creates a new {@code IllegalStraightException} with the supplied message
	 * @param message a message describing the exception
	 */
	public IllegalStraightException(String message) {
		super(message);
	}

}
