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

/**
 * <p>Represents an assortment of {@link Card}s with equal {link Rank}.</p>
 * <p>
 * An {@code NOfAKind} is the basis for poker hands like {@link HandType#ONE_PAIR}, {@link HandType#TWO_PAIR},
 * {@link HandType#THREE_OF_A_KIND}, {@link HandType#FULL_HOUSE} and {@link HandType#FOUR_OF_A_KIND}.
 * </p>
 */
class NOfAKind implements Comparable<NOfAKind>, HandValue {

	private Set<Card> cards = new HashSet<>();
	
	/**
	 * Creates a new {@code NOfAKind} from the supplied {@link Set} of {@link Card}s
	 * @param cards the {@code Card}s to be used in the {@code NOfAKind}
	 * @throws IllegalNOfAKindException if not all of the supplied {@code Card}s are of the same {@link Rank} (and
	 * therefore don't make a valid {@code NOfAKind})
	 */
	public NOfAKind(Set<Card> cards) {
		if (!EvaluationHelper.areAllCardsOfTheSameRank(cards)) {
			throw new IllegalNOfAKindException();
		}
		
		this.cards = cards;
	}
	
	/**
	 * Adds a {@link Card} to this {@code NOfAKind}
	 * @param card the {@code Card} to be added
	 * @throws IllegalCardException if the {@link Rank} of {@code card} does not match the other {@code Card}s'
	 * {@code Rank}s
	 */
	public void addCard(Card card) {
		if (!cards.isEmpty() && cards.iterator().next().getRank() == card.getRank()) {
			cards.add(card);
		} else {
			throw new IllegalCardException();
		}
	}
	
	/**
	 * The {@link Rank} of all {@link Card}s in this {@code NOfAKind}
	 * @return The {@link Rank} of all {@link Card}s in this {@code NOfAKind}
	 */
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
	
	/**
	 * The number of {@link Card}s in this {@code NOfAKind}
	 * @return The number of {@link Card}s in this {@code NOfAKind}
	 */
	public int getN() {
		return cards.size();
	}
	
	/**
	 * The {@code n} best {@link Card}s of this {@code NOfAKind} as a new {@code NOfAKind}
	 * @param n the number of {@code Card}s to pack into a new {@code NOfAKind}
	 * @return A newly created {@code NOfAKind} containing the {@code n} best {@code Card}s
	 * (as determined by the {@link RankBeforeSuitComparator}) from the original {@link NOfAKind}
	 * @throws IllegalNOfAKindAccessException if there are less than {@code n} {@code Card}s
	 * in the original {@code NOfAKind}
	 */
	public NOfAKind getNBestCardsAsNewNOfAKind(int n) {
		if (getN() == n) {
			// this one is already the desired n of a kind
			return this;
		}
		
		if (getN() < n) {
			// we don't have enough cards
			throw new IllegalNOfAKindAccessException();
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
