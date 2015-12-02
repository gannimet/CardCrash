package app.cards.game;

import java.util.HashSet;
import java.util.Set;

import app.cards.Card;

public class Hand {

	private Set<Card> cards;
	
	public Hand(Set<Card> cards) {
		if (isValidCardSet(cards)) {
			this.cards = cards;
		} else {
			throw new IllegalHandException();
		}
	}
	
	public Hand() {
		cards = new HashSet<>(7);
	}
	
	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
	
	public void addCard(Card card) {
		if (cards.size() <= 6 && !cards.contains(card)) {
			cards.add(card);
		} else {
			throw new IllegalHandException();
		}
	}

	private boolean isValidCardSet(Set<Card> cards) {
		return cards.size() <= 7;
	}
	
	/*
	 * Hand detection functions
	 */
	public HandResult evaluate() {
		return null;
	}
	
}
