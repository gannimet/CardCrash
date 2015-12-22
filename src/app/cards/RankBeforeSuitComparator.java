package app.cards;

import java.util.Comparator;

/**
 * {@link Comparator} to compare {@link Card} objects based on their rank, and
 * then based on suit only in case of rank equality.
 */
public class RankBeforeSuitComparator implements Comparator<Card> {

	/**
	 * Compares two {@link Card} objects
	 * @return A negative number if {@code card2} is better, a positive number if {@code card1} is better,
	 * 0 if they're equal
	 */
	@Override
	public int compare(Card card1, Card card2) {
		int compareValue = card1.getRank().getValue() - card2.getRank().getValue();
		
		if (compareValue != 0) {
			return compareValue;
		}
		
		// if it's a tie in terms of rank, use the suit as next criterion
		return card1.getSuit().getValue() - card2.getSuit().getValue();
	}

}
