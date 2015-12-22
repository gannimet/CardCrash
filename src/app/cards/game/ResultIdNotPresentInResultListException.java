package app.cards.game;

/**
 * Indicates that the ranking position of a {@link HandResult} within an {@link HandResultList}
 * was requested via the {@code HandResult}'s ID, but no {@code HandResult} with such ID was
 * found in the {@code HandResultList}.
 */
public class ResultIdNotPresentInResultListException extends RuntimeException {

	private String id;
	private static final long serialVersionUID = -7983245731014952653L;
	
	/**
	 * Creates a new {@code ResultIdNotPresentInResultListException} with the supplied ID of the
	 * originating request
	 * @param id the ID used for the original request to look up a {@link HandResult}
	 */
	public ResultIdNotPresentInResultListException(String id) {
		this.id = id;
	}

	/**
	 * The ID used for the original request to look up a {@link HandResult}
	 * @return the ID used for the original request to look up a {@code HandResult}
	 */
	public String getId() {
		return id;
	}

}
