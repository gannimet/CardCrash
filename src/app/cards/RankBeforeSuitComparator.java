package app.cards;

import java.util.Comparator;

public class RankBeforeSuitComparator implements Comparator<Card> {

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
