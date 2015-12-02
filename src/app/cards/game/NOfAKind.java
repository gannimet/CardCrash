package app.cards.game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import app.cards.Card;
import app.cards.Rank;

class NOfAKind implements Comparable<NOfAKind>, HandValue {

	private Set<Card> cards = new HashSet<>();
	
	public NOfAKind(Set<Card> cards) {
		if (cards.size() > 0) {
			Iterator<Card> iter = cards.iterator();
			Rank rank = iter.next().getRank();
			
			while (iter.hasNext()) {
				if (rank != iter.next().getRank()) {
					throw new IllegalCardException();
				}
			}
		}
		
		this.cards = cards;
	}
	
	public void addCard(Card card) {
		if (!cards.isEmpty() && cards.iterator().next().getRank() == card.getRank()) {
			cards.add(card);
		} else {
			throw new IllegalCardException();
		}
	}
	
	public Rank getRank() {
		if (!cards.isEmpty()) {
			return cards.iterator().next().getRank();
		}
		
		return null;
	}
	
	public Set<Card> getCards() {
		return cards;
	}
	
	public int getN() {
		return cards.size();
	}

	@Override
	public int compareTo(NOfAKind other) {
		int nDiff = other.getN() - this.getN();
		
		// the more cards the better
		if (nDiff != 0) {
			return nDiff;
		}
		
		// same number of cards means rank is the tie breaker
		return other.getRank().getValue() - this.getRank().getValue();
	}
	
	@Override
	public String toString() {
		return "{" + getN() + " of a kind: " + cards.toString() + "}";
	}
	
}
