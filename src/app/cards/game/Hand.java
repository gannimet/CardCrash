package app.cards.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import app.cards.Card;
import app.cards.Rank;
import app.cards.Suit;

public class Hand {

	private Set<Card> cards;
	public final static int MAX_NUMBER_OF_CARDS_PER_HAND = 7;
	
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
	
	public static Hand fromShortcuts(String... shortcuts) {
		Set<Card> cards = new HashSet<>();
		
		for (String shortcut : shortcuts) {
			Card card = Card.fromShortcut(shortcut);
			cards.add(card);
		}
		
		return new Hand(cards);
	}
	
	public static Hand fromCards(Card... cards) {
		Set<Card> cardSet = new HashSet<>();
		
		for (Card card : cards) {
			cardSet.add(card);
		}
		
		return new Hand(cardSet);
	}
	
	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
	
	public void addCard(Card card) {
		if (cards.size() < MAX_NUMBER_OF_CARDS_PER_HAND && !cards.contains(card)) {
			cards.add(card);
		} else {
			throw new IllegalHandException();
		}
	}

	private boolean isValidCardSet(Set<Card> cards) {
		return cards.size() <= MAX_NUMBER_OF_CARDS_PER_HAND;
	}
	
	/*
	 * Hand detection
	 */
	public HandResult evaluate() {
		CardSequence longestSequence = EvaluationHelper.getLongestSequence(cards);
		List<HandValue> breakdown = new ArrayList<>();
		List<NOfAKind> nOfAKinds = EvaluationHelper.getNOfAKinds(cards);
		Map<Suit, Set<Card>> cardsGroupedBySuit = EvaluationHelper.groupCardsBySuit(cards);
		Straight straight = null;
		
		// try to find a royal flush
		if (longestSequence.length() >= 5) {
			// a straight is definitely there
			straight = Straight.makeBestStraight(longestSequence);
			breakdown.add(straight);
			
			if (straight.isRoyal()) {
				// best possible hand
				return new HandResult(HandType.ROYAL_FLUSH, breakdown);
			} else if (straight.isFlush()) {
				// second best possible hand
				return new HandResult(HandType.STRAIGHT_FLUSH, breakdown);
			}
			
			// obviously the straight was neither royal nor a straight flush
			// so we just remember it for after we checked for four of a kinds
			// and full houses
		}
		
		// next best hand would be four of a kind
		if (nOfAKinds.get(0).getN() == 4) {
			// there it is
			breakdown.add(nOfAKinds.get(0));
			HandResult result = new HandResult(HandType.FOUR_OF_A_KIND, breakdown);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		// next best hand would be full house
		if (nOfAKinds.size() > 1 && nOfAKinds.get(0).getN() == 3 && nOfAKinds.get(1).getN() >= 2) {
			// there it is
			FullHouse fullHouse = FullHouse.makeBestFullHouse(nOfAKinds);
			breakdown.add(fullHouse);
			return new HandResult(HandType.FULL_HOUSE, breakdown);
		}
		
		// next best hand would be a flush
		if (cardsGroupedBySuit.get(Suit.CLUBS).size() >= 5) {
			// club flush is best
			Flush flush = Flush.makeBestFlush(cardsGroupedBySuit.get(Suit.CLUBS));
			breakdown.add(flush);
			return new HandResult(HandType.FLUSH, breakdown);
		} else if (cardsGroupedBySuit.get(Suit.DIAMONDS).size() >= 5) {
			// diamonds still pretty good
			Flush flush = Flush.makeBestFlush(cardsGroupedBySuit.get(Suit.DIAMONDS));
			breakdown.add(flush);
			return new HandResult(HandType.FLUSH, breakdown);
		} else if (cardsGroupedBySuit.get(Suit.HEARTS).size() >= 5) {
			// hearts still decent
			Flush flush = Flush.makeBestFlush(cardsGroupedBySuit.get(Suit.HEARTS));
			breakdown.add(flush);
			return new HandResult(HandType.FLUSH, breakdown);
		} else if (cardsGroupedBySuit.get(Suit.SPADES).size() >= 5) {
			// spades... well, at least it's still a flush
			Flush flush = Flush.makeBestFlush(cardsGroupedBySuit.get(Suit.SPADES));
			breakdown.add(flush);
			return new HandResult(HandType.FLUSH, breakdown);
		}
		
		// if we found a straight earlier and it survived till now, it means it's
		// the best we can do with this hand!
		if (straight != null) {
			return new HandResult(HandType.STRAIGHT, breakdown);
		}
		
		// next best hand would be a three of a kind
		if (nOfAKinds.size() >= 1 && nOfAKinds.get(0).getN() == 3) {
			breakdown.add(nOfAKinds.get(0));
			HandResult result = new HandResult(HandType.THREE_OF_A_KIND, breakdown);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		// next best hand would be two pair
		if (nOfAKinds.size() >= 2 && nOfAKinds.get(0).getN() == 2 &&
				nOfAKinds.get(1).getN() == 2) {
			breakdown.add(nOfAKinds.get(0));
			breakdown.add(nOfAKinds.get(1));
			HandResult result = new HandResult(HandType.TWO_PAIR, breakdown);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		// next best hand would be one pair
		if (nOfAKinds.size() >= 1 && nOfAKinds.get(0).getN() == 2) {
			breakdown.add(nOfAKinds.get(0));
			HandResult result = new HandResult(HandType.ONE_PAIR, breakdown);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		// now there's only high card left
		if (nOfAKinds.size() >= 1 && nOfAKinds.get(0).getN() == 1) {
			breakdown.add(nOfAKinds.get(0));
			HandResult result = new HandResult(HandType.HIGH_CARD, breakdown);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		throw new InvalidHandEvaluationException();
	}
	
	@Test
	public void testHandEvaluation() {
		Hand hand = new Hand();
		hand.addCard(Card.getCard(Suit.CLUBS, Rank.DEUCE));
		hand.addCard(Card.getCard(Suit.SPADES, Rank.TEN));
		hand.addCard(Card.getCard(Suit.DIAMONDS, Rank.EIGHT));
		hand.addCard(Card.getCard(Suit.HEARTS, Rank.KING));
		hand.addCard(Card.getCard(Suit.DIAMONDS, Rank.QUEEN));
		hand.addCard(Card.getCard(Suit.DIAMONDS, Rank.FIVE));
		hand.addCard(Card.getCard(Suit.DIAMONDS, Rank.JACK));
		
		HandResult result = hand.evaluate();
		
		System.out.println(result);
	}
	
}
