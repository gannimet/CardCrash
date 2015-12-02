package app.cards.game;

import java.util.List;

import app.cards.Card;

public class Player {

	private String name;
	private List<Card> holeCards;
	
	public Player(String name, List<Card> holeCards) {
		this.name = name;
		this.holeCards = holeCards;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Card> getHoleCards() {
		return holeCards;
	}
	
	public void setHoleCards(List<Card> holeCards) {
		this.holeCards = holeCards;
	}
	
}
