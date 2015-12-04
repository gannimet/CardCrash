package app.cards.game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import app.cards.Card;
import app.cards.Rank;
import app.cards.Suit;

public class FullHouse implements HandValue, Comparable<FullHouse> {

	private NOfAKind threeOfAKind;
	private NOfAKind pair;
	
	private FullHouse(NOfAKind threeOfAKind, NOfAKind pair) {
		this.threeOfAKind = threeOfAKind;
		this.pair = pair;
	}
	
	public NOfAKind getThreeOfAKind() {
		return threeOfAKind;
	}

	public NOfAKind getPair() {
		return pair;
	}
	
	/**
	 * Returns the best possible full house out of the provided SORTED list
	 * of n-of-a-kinds
	 * @param nOfAKinds sorted list of NOfAKinds in the hand
	 * @return the best possible full house
	 */
	public static FullHouse makeBestFullHouse(List<NOfAKind> nOfAKinds) {
		if (nOfAKinds.size() < 2 || nOfAKinds.get(0).getN() != 3 ||
				nOfAKinds.get(1).getN() < 2) {
			throw new IllegalFullHouseException();
		}
		
		// in any case the highest ranked three of a kind is the best
		NOfAKind bestThreeOfAKind = nOfAKinds.get(0);
		
		// sort the rest of the list purely based on rank, because the best pair
		// can also come from another three of a kind. but it is important that
		// we ignore single cards
		List<NOfAKind> remainingNOfAKinds = new ArrayList<>();
		remainingNOfAKinds.addAll(nOfAKinds);
		remainingNOfAKinds.remove(0);
		Collections.sort(remainingNOfAKinds, new Comparator<NOfAKind>() {
			
			@Override
			public int compare(NOfAKind first, NOfAKind second) {
				// only two of a kinds plus should be considered
				if (first.getN() == 1) {
					if (second.getN() == 1) {
						// rank single cards by rank
						return second.getRank().getValue() - first.getRank().getValue();
					}
					
					// second wins because it has more than one card
					return 1;
				} else if (second.getN() == 1) {
					// first wins because it has more than one card
					return -1;
				}
				
				return second.getRank().getValue() - first.getRank().getValue();
			}
			
		});
		
		// pick n of a kind with best rank
		NOfAKind bestRanked = remainingNOfAKinds.get(0);
		NOfAKind bestPair = bestRanked.getNBestCardsAsNewNOfAKind(2);
		
		return new FullHouse(bestThreeOfAKind, bestPair);
	}

	@Override
	public int compareTo(FullHouse other) {
		int threeOfAKindDiff = threeOfAKind.getRank().getValue() -
			other.threeOfAKind.getRank().getValue();
		
		if (threeOfAKindDiff != 0) {
			// better three of a kind wins
			return threeOfAKindDiff;
		}
		
		// three of a kinds have same rank, so the pair decides
		return pair.getRank().getValue() - other.pair.getRank().getValue();
	}
	
	@Override
	public String toString() {
		return "{" + threeOfAKind + ", " + pair + " }";
	}
	
	@Test
	public void testFullHouseMakingWithBestPairInPair() {
		Set<Card> set1 = new HashSet<>();
		set1.add(Card.getCard(Suit.SPADES, Rank.QUEEN));
		set1.add(Card.getCard(Suit.CLUBS, Rank.QUEEN));
		set1.add(Card.getCard(Suit.DIAMONDS, Rank.QUEEN));
		
		Set<Card> set2 = new HashSet<>();
		set2.add(Card.getCard(Suit.SPADES, Rank.SIX));
		set2.add(Card.getCard(Suit.CLUBS, Rank.SIX));
		set2.add(Card.getCard(Suit.DIAMONDS, Rank.SIX));
		
		Set<Card> set3 = new HashSet<>();
		set3.add(Card.getCard(Suit.SPADES, Rank.JACK));
		set3.add(Card.getCard(Suit.CLUBS, Rank.JACK));
		
		Set<Card> set4 = new HashSet<>();
		set4.add(Card.getCard(Suit.SPADES, Rank.TEN));
		set4.add(Card.getCard(Suit.CLUBS, Rank.TEN));
		
		List<NOfAKind> noaks = new ArrayList<>();
		noaks.add(new NOfAKind(set1));
		noaks.add(new NOfAKind(set2));
		noaks.add(new NOfAKind(set3));
		noaks.add(new NOfAKind(set4));
		
		FullHouse fh = FullHouse.makeBestFullHouse(noaks);
		
		NOfAKind threeOfAKind = fh.getThreeOfAKind();
		NOfAKind pair = fh.getPair();
		
		assertEquals(3, threeOfAKind.getCards().size());
		assertEquals(2, pair.getCards().size());
		
		assertTrue(threeOfAKind.getCards().contains(Card.getCard(Suit.SPADES, Rank.QUEEN)));
		assertTrue(threeOfAKind.getCards().contains(Card.getCard(Suit.CLUBS, Rank.QUEEN)));
		assertTrue(threeOfAKind.getCards().contains(Card.getCard(Suit.DIAMONDS, Rank.QUEEN)));
		
		assertTrue(pair.getCards().contains(Card.getCard(Suit.SPADES, Rank.JACK)));
		assertTrue(pair.getCards().contains(Card.getCard(Suit.CLUBS, Rank.JACK)));
	}
	
	@Test
	public void testFullHouseMakingWithBestPairInThreeOfAKind() {
		Set<Card> set1 = new HashSet<>();
		set1.add(Card.getCard(Suit.SPADES, Rank.QUEEN));
		set1.add(Card.getCard(Suit.CLUBS, Rank.QUEEN));
		set1.add(Card.getCard(Suit.DIAMONDS, Rank.QUEEN));
		
		Set<Card> set2 = new HashSet<>();
		set2.add(Card.getCard(Suit.SPADES, Rank.JACK));
		set2.add(Card.getCard(Suit.CLUBS, Rank.JACK));
		set2.add(Card.getCard(Suit.DIAMONDS, Rank.JACK));
		
		Set<Card> set3 = new HashSet<>();
		set3.add(Card.getCard(Suit.SPADES, Rank.TEN));
		set3.add(Card.getCard(Suit.CLUBS, Rank.TEN));
		
		Set<Card> set4 = new HashSet<>();
		set4.add(Card.getCard(Suit.SPADES, Rank.SIX));
		set4.add(Card.getCard(Suit.CLUBS, Rank.SIX));
		
		List<NOfAKind> noaks = new ArrayList<>();
		noaks.add(new NOfAKind(set1));
		noaks.add(new NOfAKind(set2));
		noaks.add(new NOfAKind(set3));
		noaks.add(new NOfAKind(set4));
		
		FullHouse fh = FullHouse.makeBestFullHouse(noaks);
		
		NOfAKind threeOfAKind = fh.getThreeOfAKind();
		NOfAKind pair = fh.getPair();
		
		assertEquals(3, threeOfAKind.getCards().size());
		assertEquals(2, pair.getCards().size());
		
		assertTrue(threeOfAKind.getCards().contains(Card.getCard(Suit.SPADES, Rank.QUEEN)));
		assertTrue(threeOfAKind.getCards().contains(Card.getCard(Suit.CLUBS, Rank.QUEEN)));
		assertTrue(threeOfAKind.getCards().contains(Card.getCard(Suit.DIAMONDS, Rank.QUEEN)));
		
		assertTrue(pair.getCards().contains(Card.getCard(Suit.DIAMONDS, Rank.JACK)));
		assertTrue(pair.getCards().contains(Card.getCard(Suit.CLUBS, Rank.JACK)));
	}

}
