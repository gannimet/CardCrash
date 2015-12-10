package app.cards.game;

public class ResultIdNotPresentInResultListException extends RuntimeException {

	private String id;
	private static final long serialVersionUID = -7983245731014952653L;
	
	public ResultIdNotPresentInResultListException(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
