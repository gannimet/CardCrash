package app.cards.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import app.cards.Card;
import app.cards.Rank;
import app.cards.RankBeforeSuitComparator;

/**
 * <p>
 * This class encapsulate five {@link Card}s that make a flush.
 * </p>
 * 
 * <p>
 * A {@code Flush} instance is created via {@link #makeBestFlush(Set)} from a {@link Set} of
 * {@code Card}s.
 * </p>
 */
public class Flush implements HandValue, Comparable<Flush> {

	private List<Card> cards;
	
	private Flush(List<Card> cards) {
		if (!EvaluationHelper.areAllCardsOfTheSameSuit(cards)) {
			throw new IllegalFlushException("All cards need to be of same suit.");
		}
		
		this.cards = cards;
	}
	
	/**
	 * Creates the best possible {@code Flush} from the provided {@link Set} of {@link Card}s
	 * @param suitedCards Set of cards of the same suit, possibly more than 5
	 * @return Sorted list of the five cards within suitedCards that
	 * make the best flush. Straight flushes are not considered; instead, they are classified
	 * as {@link Straight}s and will be detected by calling {@link Straight#makeBestStraight(CardSequence)}.
	 */
	public static Flush makeBestFlush(Set<Card> suitedCards) {
		if (!EvaluationHelper.areAllCardsOfTheSameSuit(suitedCards)) {
			throw new IllegalFlushException("All cards need to be of same suit.");
		}
		
		if (suitedCards.size() < 5) {
			throw new IllegalFlushException("At least five cards are necessary for a flush.");
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
	public Collection<Card> getCards() {
		return cards;
	}
	
	/**
	 * The n-th highest {@link Card} of the {@code Flush}
	 * @param n position of the desired card, from {@code 1} (best card) to {@code 5} (worst card)
	 * @return The n-th best {@code Card} as determined by the {@link RankBeforeSuitComparator}
	 */
	public Card getNthHighestCard(int n) {
		return cards.get(cards.size() - n);
	}
	
	/**
	 * The best {@link Card} of the {@code Flush} (shortcut for {@code getNthHighestCard(1)})
	 * @return The best {@code Card} as determined by the {@link RankBeforeSuitComparator}
	 */
	public Card getHighestCard() {
		return getNthHighestCard(1);
	}
	
	/**
	 * <p>Compares this {@code Flush} to another.</p>
	 * <p>A flush {@code f1} is better than a flush {@code f2} if its highest card is better. If
	 * the highest cards of both flushes are equal, the flush with the better second-highest card wins
	 * and so on.</p>
	 * @return A negative number if {@code other} is better than this {@code Flush}, a positive
	 * number if this {@code Flush} is better than {@code other}, 0 if they're equal.
	 */
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
	
	/**
	 * A human-readable representation of this {@code Flush} as the {@link List} of its cards enclosed
	 * in curly braces
	 */
	@Override
	public String toString() {
		return "{ " + cards + " }";
	}

}
