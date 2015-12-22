package app.cards.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.cards.Card;

/**
 * <p>
 * This class encapsulates a three of a kind and a pair that together make
 * a full house.
 * </p>
 * <p>
 * A {@code FullHouse} instance is created via {@link #makeBestFullHouse(List)} from a sorted
 * {@link List} of {@code NOfAKind}s.
 * </p>
 */
public class FullHouse implements HandValue, Comparable<FullHouse> {

	private NOfAKind threeOfAKind;
	private NOfAKind pair;
	
	private FullHouse(NOfAKind threeOfAKind, NOfAKind pair) {
		this.threeOfAKind = threeOfAKind;
		this.pair = pair;
	}
	
	/**
	 * The three of a kind of this {@code FullHouse}
	 * @return The three of a kind of this {@code FullHouse}, i. e. an {@link NOfAKind}
	 * with exactly 3 {@link Card}s 
	 */
	public NOfAKind getThreeOfAKind() {
		return threeOfAKind;
	}

	/**
	 * The pair of this {@code FullHouse}
	 * @return The pair of this {@code FullHouse}, i. e. an {@link NOfAKind}
	 * with exactly 2 {@link Card}s
	 */
	public NOfAKind getPair() {
		return pair;
	}
	
	@Override
	public Set<Card> getCards() {
		Set<Card> cards = new HashSet<Card>();
		cards.addAll(threeOfAKind.getCards());
		cards.addAll(pair.getCards());
		
		return cards;
	}
	
	/**
	 * Creates the best possible {@code FullHouse} from the provided sorted {@link List}
	 * of {@link NOfAKind}s
	 * @param nOfAKinds {@link List} of {@code NOfAKind}s that has to be sorted from
	 * best to worst 
	 * @return The best possible {@code FullHouse} found in the supplied {@code List}
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

	/**
	 * <p>Compares this {@code FullHouse} to another</p>
	 * <p>A full house {@code f1} is better than a full house {@code f2} if its three of a kind is
	 * better. If the three of a kinds of both full houses are equal, the full house with the
	 * better pair wins.</p>
	 * @return A negative number if {@code other} is better than this {@code FullHouse}, a positive
	 * number if this {@code FullHouse} is better than {@code other}, 0 if they're equal
	 */
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
	
	/**
	 * A human-readable representation of this {@code FullHouse} as the three of a kind comma-separated
	 * from the pair, both enclosed in curly braces
	 */
	@Override
	public String toString() {
		return "{" + threeOfAKind + ", " + pair + " }";
	}

}
