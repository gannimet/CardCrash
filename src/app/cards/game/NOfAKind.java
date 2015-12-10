package app.cards.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.cards.Card;
import app.cards.Rank;
import app.cards.RankBeforeSuitComparator;

class NOfAKind implements Comparable<NOfAKind>, HandValue {

	private Set<Card> cards = new HashSet<>();
	
	public NOfAKind(Set<Card> cards) {
		if (!EvaluationHelper.areAllCardsOfTheSameRank(cards)) {
			throw new IllegalNOfAKindException();
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
	
	@Override
	public Collection<Card> getCards() {
		return cards;
	}
	
	public int getN() {
		return cards.size();
	}
	
	public NOfAKind getNBestCardsAsNewNOfAKind(int n) {
		if (getN() == n) {
			// this one is already the desired n of a kind
			return this;
		}
		
		if (getN() < n) {
			// we don't have enough cards
			throw new IllegalRequestException();
		}
		
		List<Card> sortedCards = new ArrayList<>();
		sortedCards.addAll(cards);
		// RankBeforeSuitComparator will sort by suit if ranks are equal
		Collections.sort(sortedCards, new RankBeforeSuitComparator());
		
		// make new n of a kind
		Set<Card> newCards = new HashSet<>();
		for (int i = 0; i < n; i++) {
			newCards.add(sortedCards.get(i));
		}
		
		return new NOfAKind(newCards);
	}

	@Override
	public int compareTo(NOfAKind other) {
		int thisN = this.getN();
		int otherN = other.getN();
		
		int nDiff = otherN - thisN;
		
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
