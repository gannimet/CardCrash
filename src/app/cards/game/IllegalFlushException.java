package app.cards.game;

public class IllegalFlushException extends RuntimeException {

	private static final long serialVersionUID = 8419120466554155800L;
	
	public IllegalFlushException(String message) {
		super(message);
	}

}
