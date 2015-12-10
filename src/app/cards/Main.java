package app.cards;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import app.cards.game.Hand;
import app.cards.game.HandResult;
import app.cards.game.HandResultList;

public class Main {
	public static void main(String[] args) {
		Set<Card> board = new HashSet<>();
		for (int i = 0; i < 5; i++) {
			board.add(Card.randomCard());
		}
		
		System.out.println("Board: " + board);
		
		HandResultList resultList = new HandResultList();
		for (int i = 1; i <= 9; i++) {
			Card card1 = Card.randomCard();
			Card card2 = Card.randomCard();
			System.out.println("Player " + i + " hole cards: " + card1 + " and " + card2);
			
			Hand hand = Hand.fromCards(board, card1, card2);
			hand.setId("Player " + i);
			resultList.addHand(hand);
		}
		
		System.out.println("---");
		
		for (int place = 1; place <= resultList.getNumberOfPlaces(); place++) {
			Collection<HandResult> results = resultList.getResultsRankedAt(place);
			System.out.println("Place " + place + " contains " + results.size() + " results and they are:");
			for (HandResult result : results) {
				System.out.println("  -- " + result.getId() + " with: " + result);
			}
			System.out.println();
		}
	}
	
}
