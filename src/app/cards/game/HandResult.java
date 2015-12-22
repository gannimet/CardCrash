package app.cards.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import app.cards.Card;
import app.cards.RankBeforeSuitComparator;

/**
 * Encapsulates the result of the evaluation of a {@link Hand} initiated by calling
 * {@link Hand#evaluate()}.
 */
public class HandResult implements Comparable<HandResult> {
	
	private List<HandValue> breakdown;
	private HandType handType;
	private String id;

	/**
	 * Creates a new {@code HandResult} with the supplied information
	 * @param handType which {@link HandType} was identified for the {@link Card}s of a {@link Hand}
	 * @param breakdown how the best 5-card combination breaks down into {@link HandValue}s,
	 * from best to worst
	 * @param id the identifier for this {@code HandResult} (see {@link Hand#setId(String)})
	 */
	HandResult(HandType handType, List<HandValue> breakdown, String id) {
		this.handType = handType;
		this.breakdown = breakdown;
		this.id = id;
	}
	
	/**
	 * The {@link HandType} represented by this {@code HandResult}
	 * @return The {@code HandType} represented by this {@code HandResult}
	 */
	public HandType getHandType() {
		return handType;
	}

	/**
	 * <p>A detailed breakdown of all {@link HandValue}s producing the {@link HandType} represented by this
	 * {@code HandResult}</p>
	 * <p>
	 * The total number of {@link Card}s across all these {@code HandValue}s is guaranteed to equal 5.
	 * In case the actual {@code HandType} consists of less than five cards (High card, One Pair, Two Pair, Three of
	 * a kind, Four of a kind), the returned breakdown will be filled the best of the remaining {@code Card}s. The
	 * order inside the returned {@code List} is guaranteed to be best to worst, e. g. a two-pair {@code Hand} will
	 * break down as an {@link NOfAKind} with 2 {@code Card}s representing the better pair, another {@code NOfAKind}
	 * with the worse pair, plus the "best of the rest" {@code Card} to make up the numbers.
	 * </p>
	 * @return The breakdown as a {@link List} of {@code HandValue}s comprising the determined {@code HandType}.
	 */
	public List<HandValue> getBreakdown() {
		return breakdown;
	}
	
	/**
	 * <p>The ID of this {@code HandResult}</p>
	 * <p>
	 * The {@link Hand} object that has created this {@code HandResult} will pass its ID into it upon calling the
	 * constructor. This enables for tracking down the {@code Hand} which produced this {@code HandResult}.
	 * </p>
	 * @return The ID of this {@code HandResult}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Puts the best {@link Card}s from the supplied {@link Set} into the breakdown until it contains exactly 5
	 * {@code Card}s
	 * @param cards a {@code Set} from which to pick the best {@code Card}s (as determined by the
	 * {@link RankBeforeSuitComparator}) until a total of 5 {@code Card}s is in the breakdown
	 */
	void fillBreakdownWithBestUnusedCardsFrom(Set<Card> cards) {
		List<Card> sortedCards = new ArrayList<>();
		sortedCards.addAll(cards);
		
		Collections.sort(sortedCards, new RankBeforeSuitComparator());
		// it's easier when highest cards are on top of the list
		List<Card> reversedCards = Lists.reverse(sortedCards);
		
		for (int i = 0; getNumberOfCardsInBreakdown() < 5; i++) {
			Card card = reversedCards.get(i);
			
			if (!isCardPartOfBreakdown(card)) {
				Set<Card> fillCardSet = new HashSet<Card>();
				fillCardSet.add(card);
				NOfAKind fillCardNOfAKind = new NOfAKind(fillCardSet);
				breakdown.add(fillCardNOfAKind);
			}
		}
	}
	
	private boolean isCardPartOfBreakdown(Card card) {
		for (HandValue handValue : breakdown) {
			Collection<Card> handValueCards = handValue.getCards();
			if (handValueCards.contains(card)) {
				return true;
			}
		}
		
		return false;
	}
	
	private int getNumberOfCardsInBreakdown() {
		int count = 0;
		
		for (HandValue handValue : breakdown) {
			count += handValue.getCards().size();
		}
		
		return count;
	}
	
	/**
	 * Returns a human-readable explanation of this {@code HandResult}
	 * @return A human-readble {@link String} containing the {@link HandType} and breakdown of this
	 * {@code HandResult}
	 */
	@Override
	public String toString() {
		return handType.getName() + ", breaks down as: " + breakdown;
	}

	/**
	 * <p>Compares this {@code HandResult} to another</p>
	 * <p>
	 * Whether one {@code HandResult} is better than another is determined by the usual poker hand ranking (see
	 * also {@link HandType}, specifically {@link HandType#getValue()}).
	 * </p>
	 * @return A negative number if this {@code HandResult} is better than {@code other}, a positive number if
	 * {@code other} is better than this {@code HandResult}, 0 if they're equal
	 */
	@Override
	public int compareTo(HandResult other) {
		HandType thisHandType = this.handType;
		HandType otherHandType = other.handType;
		
		if (thisHandType.getValue() != otherHandType.getValue()) {
			return otherHandType.getValue() - thisHandType.getValue();
		}
		
		// same type of hand, so make detailed analysis
		int compareResult;
		NOfAKind thisSecondCard, thisThirdCard, thisFourthCard, thisFifthCard,
			otherSecondCard, otherThirdCard, otherFourthCard, otherFifthCard;
		switch (thisHandType) {
			case ROYAL_FLUSH:
			case STRAIGHT_FLUSH:
			case STRAIGHT:
				Straight thisStraight = (Straight) this.breakdown.get(0);
				Straight otherStraight = (Straight) other.breakdown.get(0);
				return thisStraight.compareTo(otherStraight);
			case FOUR_OF_A_KIND:
				NOfAKind thisFourOfAKind = (NOfAKind) this.breakdown.get(0);
				NOfAKind otherFourOfAKind = (NOfAKind) other.breakdown.get(0);
				compareResult = thisFourOfAKind.compareTo(otherFourOfAKind);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if the four of a kinds tie, compare the fifth card
				thisFifthCard = (NOfAKind) this.breakdown.get(1);
				otherFifthCard = (NOfAKind) other.breakdown.get(1);
				return thisFifthCard.compareTo(otherFifthCard);
			case FULL_HOUSE:
				FullHouse thisFullHouse = (FullHouse) this.breakdown.get(0);
				FullHouse otherFullHouse = (FullHouse) other.breakdown.get(0);
				return thisFullHouse.compareTo(otherFullHouse);
			case FLUSH:
				Flush thisFlush = (Flush) this.breakdown.get(0);
				Flush otherFlush = (Flush) other.breakdown.get(0);
				return thisFlush.compareTo(otherFlush);
			case THREE_OF_A_KIND:
				NOfAKind thisThreeOfAKind = (NOfAKind) this.breakdown.get(0);
				NOfAKind otherThreeOfAKind = (NOfAKind) other.breakdown.get(0);
				compareResult = thisThreeOfAKind.compareTo(otherThreeOfAKind);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if the three of a kinds tie, compare the remaining two cards
				thisFourthCard = (NOfAKind) this.breakdown.get(1);
				otherFourthCard = (NOfAKind) other.breakdown.get(1);
				compareResult = thisFourthCard.compareTo(otherFourthCard);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if the fourth cards tie, compare the fifth card
				thisFifthCard = (NOfAKind) this.breakdown.get(2);
				otherFifthCard = (NOfAKind) other.breakdown.get(2);
				return thisFifthCard.compareTo(otherThreeOfAKind);
			case TWO_PAIR:
				NOfAKind thisFirstPair = (NOfAKind) this.breakdown.get(0);
				NOfAKind otherFirstPair = (NOfAKind) other.breakdown.get(0);
				compareResult = thisFirstPair.compareTo(otherFirstPair);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if the first pair ties, compare the second pair
				NOfAKind thisSecondPair = (NOfAKind) this.breakdown.get(1);
				NOfAKind otherSecondPair = (NOfAKind) other.breakdown.get(1);
				compareResult = thisSecondPair.compareTo(otherSecondPair);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if the second pair ties, compare the fifth and last card
				thisFifthCard = (NOfAKind) this.breakdown.get(2);
				otherFifthCard = (NOfAKind) other.breakdown.get(2);
				return thisFifthCard.compareTo(otherFifthCard);
			case ONE_PAIR:
				NOfAKind thisPair = (NOfAKind) this.breakdown.get(0);
				NOfAKind otherPair = (NOfAKind) other.breakdown.get(0);
				compareResult = thisPair.compareTo(otherPair);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if the pair ties, compare the third card
				thisThirdCard = (NOfAKind) this.breakdown.get(1);
				otherThirdCard = (NOfAKind) other.breakdown.get(1);
				compareResult = thisThirdCard.compareTo(otherThirdCard);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if the third card ties, compare the fourth card
				thisFourthCard = (NOfAKind) this.breakdown.get(2);
				otherFourthCard = (NOfAKind) other.breakdown.get(2);
				compareResult = thisFourthCard.compareTo(otherFourthCard);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if the fourth card ties, compare the fifth and last card
				thisFifthCard = (NOfAKind) this.breakdown.get(3);
				otherFifthCard = (NOfAKind) other.breakdown.get(3);
				return thisFifthCard.compareTo(otherFifthCard);
			case HIGH_CARD:
				NOfAKind thisHighCard = (NOfAKind) this.breakdown.get(0);
				NOfAKind otherHighCard = (NOfAKind) other.breakdown.get(0);
				compareResult = thisHighCard.compareTo(otherHighCard);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if high card ties, compare the second card
				thisSecondCard = (NOfAKind) this.breakdown.get(1);
				otherSecondCard = (NOfAKind) other.breakdown.get(1);
				compareResult = thisSecondCard.compareTo(otherSecondCard);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if second card ties, compare the third card
				thisThirdCard = (NOfAKind) this.breakdown.get(2);
				otherThirdCard = (NOfAKind) other.breakdown.get(2);
				compareResult = thisThirdCard.compareTo(otherThirdCard);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if third card ties, compare the fourth card
				thisFourthCard = (NOfAKind) this.breakdown.get(3);
				otherFourthCard = (NOfAKind) other.breakdown.get(3);
				compareResult = thisFourthCard.compareTo(otherFourthCard);
				if (compareResult != 0) {
					return compareResult;
				}
				
				// if fourth card ties, compare the fifth and last card
				thisFifthCard = (NOfAKind) this.breakdown.get(4);
				otherFifthCard = (NOfAKind) other.breakdown.get(4);
				return thisFifthCard.compareTo(otherFifthCard);
			default:
				throw new UnsupportedHandTypeException(thisHandType);
		}
	}
	
}
