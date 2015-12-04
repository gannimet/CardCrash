package app.cards.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import app.cards.Card;
import app.cards.Rank;
import app.cards.Suit;

public class Straight implements HandValue, Comparable<Straight> {

	private List<Card> cards;
	private boolean isFlush;
	
	private Straight(List<Card> cards, boolean isFlush) {
		if (cards.size() != 5) {
			throw new IllegalStraightException("Straight needs to be exactly 5 cards long.");
		}
		
		this.cards = cards;
		this.isFlush = isFlush;
	}
	
	public static Straight makeBestStraight(CardSequence sequence) {
		if (sequence.length() < 5) {
			throw new IllegalStraightException("Sequence needs to be exactly 5 cards long.");
		}
		
		// find all possible combinations of all sets in the sequence
		List<Set<Card>> allSets = sequence.allSets();
		Set<List<Card>> combinations = Sets.cartesianProduct(allSets);
		
		// find all possible straights (5 card sequences) inside combinations
		List<Straight> allPossibleStraights = new ArrayList<>();
		for (List<Card> cardList : combinations) {
			allPossibleStraights.addAll(findAllStraightsInList(cardList));
		}
		
		// rank found straights
		Collections.sort(allPossibleStraights);
		
		// return the winner
		return allPossibleStraights.get(0);
	}
	
	private static Set<Straight> findAllStraightsInList(List<Card> cardList) {
		Set<Straight> allStraights = new HashSet<>();
		int startIndex = 0;
		
		while (cardList.size() >= startIndex + 5) {
			List<Card> newStraightCards = new ArrayList<>();
			
			for (int i = startIndex; i < startIndex + 5; i++) {
				newStraightCards.add(cardList.get(i));
			}
			
			allStraights.add(new Straight(newStraightCards, EvaluationHelper.areAllCardsOfTheSameSuit(newStraightCards)));
			startIndex++;
		}
		
		return allStraights;
	}
	
	public boolean isFlush() {
		return isFlush;
	}
	
	@Override
	public Set<Card> getCards() {
		return new HashSet<Card>(cards);
	}
	
	public Card getHighestCard() {
		return cards.get(4);
	}
	
	public boolean isRoyal() {
		return isFlush() && getHighestCard().getRank() == Rank.ACE;
	}

	@Override
	public int compareTo(Straight otherStraight) {
		// a straight flush wins over just a straight
		if (this.isFlush() && !otherStraight.isFlush()) {
			// we are a flush and they are not -> we win
			return -1;
		}
		
		if (!this.isFlush() && otherStraight.isFlush()) {
			// they are a flush and we are not -> they win
			return 1;
		}
		
		// either both straights are flushes or both are no flushes
		// the one with the highest card wins
		return otherStraight.getHighestCard().getRank().getValue() - this.getHighestCard().getRank().getValue();
	}

	@Override
	public String toString() {
		return "{ isFlush: " + isFlush() + ", isRoyal: " + isRoyal() + ", cards: " + cards.toString() + " }";
	}
	
	@Test
	public void testStraightDetection() {
		Set<Card> set1 = new HashSet<>();
		Set<Card> set2 = new HashSet<>();
		Set<Card> set3 = new HashSet<>();
		Set<Card> set4 = new HashSet<>();
		Set<Card> set5 = new HashSet<>();
		Set<Card> set6 = new HashSet<>();
		
		set1.add(Card.getCard(Suit.SPADES, Rank.FOUR)); set1.add(Card.getCard(Suit.DIAMONDS, Rank.FOUR)); set1.add(Card.getCard(Suit.CLUBS, Rank.FOUR));
		set2.add(Card.getCard(Suit.SPADES, Rank.FIVE));
		set3.add(Card.getCard(Suit.SPADES, Rank.SIX)); set3.add(Card.getCard(Suit.CLUBS, Rank.SIX));
		set4.add(Card.getCard(Suit.SPADES, Rank.SEVEN)); set4.add(Card.getCard(Suit.HEARTS, Rank.SEVEN));
		set5.add(Card.getCard(Suit.SPADES, Rank.EIGHT));
		set6.add(Card.getCard(Suit.CLUBS, Rank.NINE));
		
		List<Set<Card>> list = new ArrayList<>();
		list.add(set1); list.add(set2); list.add(set3); list.add(set4); list.add(set5); list.add(set6);
		
		Straight bestStraight = Straight.makeBestStraight(new CardSequence(list));
		
		assertEquals(5, bestStraight.cards.size());
		assertEquals(Card.getCard(Suit.SPADES, Rank.FOUR), bestStraight.cards.get(0));
		assertEquals(Card.getCard(Suit.SPADES, Rank.FIVE), bestStraight.cards.get(1));
		assertEquals(Card.getCard(Suit.SPADES, Rank.SIX), bestStraight.cards.get(2));
		assertEquals(Card.getCard(Suit.SPADES, Rank.SEVEN), bestStraight.cards.get(3));
		assertEquals(Card.getCard(Suit.SPADES, Rank.EIGHT), bestStraight.cards.get(4));
		assertTrue(bestStraight.isFlush());
	}
	
}
