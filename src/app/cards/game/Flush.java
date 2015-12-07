package app.cards.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.cards.Card;
import app.cards.Rank;
import app.cards.RankBeforeSuitComparator;

public class Flush implements HandValue, Comparable<Flush> {

	private List<Card> cards;
	
	private Flush(List<Card> cards) {
		if (!EvaluationHelper.areAllCardsOfTheSameSuit(cards)) {
			throw new IllegalFlushException("All cards need to be of same suit.");
		}
		
		this.cards = cards;
	}
	
	/**
	 * Assumes that a straight has been ruled out
	 * @param suitedCards Set of cards of the same suit, possibly more than 5
	 * @return sorted list of the five cards within suitedCards that
	 * make the best flush. Straight flushes are not considered.
	 */
	public static Flush makeBestFlush(Set<Card> suitedCards) {
		if (!EvaluationHelper.areAllCardsOfTheSameSuit(suitedCards)) {
			throw new IllegalFlushException("All cards need to be of same suit.");
		}
		
		List<Card> sortedCards = new ArrayList<>();
		sortedCards.addAll(suitedCards);
		
		// sort cards for comparing purposes
		Collections.sort(sortedCards, new RankBeforeSuitComparator());
		
		List<Card> flushCards = new ArrayList<>();
		int length = sortedCards.size();
		for (int i = length - 5; i < length; i++) {
			flushCards.add(sortedCards.get(i));
		}
		
		return new Flush(flushCards);
	}
	
	@Override
	public Set<Card> getCards() {
		return new HashSet<Card>(cards);
	}
	
	public Card getNthHighestCard(int n) {
		return cards.get(cards.size() - n);
	}
	
	public Card getHighestCard() {
		return getNthHighestCard(1);
	}
	
	@Override
	public int compareTo(Flush other) {
		for (int i = 1; i <= 5; i++) {
			Rank thisRank = this.getNthHighestCard(i).getRank();
			Rank otherRank = other.getNthHighestCard(i).getRank();
			if (thisRank != otherRank) {
				return thisRank.getValue() - otherRank.getValue();
			}
		}
		
		// all cards are obviously equal in rank, so the two flushes are equal
		return 0;
	}
	
	@Override
	public String toString() {
		return "{ " + cards + " }";
	}

}
