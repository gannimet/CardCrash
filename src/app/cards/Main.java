package app.cards;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import app.cards.game.Hand;
import app.cards.game.HandResult;
import app.cards.game.HandType;

public class Main {

	private static Hand generateHandWithNCards(int n) {
		Card.resetDeck();
		Set<Card> cards = new HashSet<>();
		for (int i = 0; i < n; i++) {
			Card card = Card.randomCard();
			cards.add(card);
		}
		
		return new Hand(cards);
	}
	
	public static void main(String[] args) {
		System.out.println("Calculating …");
		
		Map<HandType, Integer> results = new HashMap<>();
		HandResult currentResult;
		HandType currentHandType;
		
		for (int i = 0; i < 1e6; i++) {
			currentResult = generateHandWithNCards(7).evaluate();
			currentHandType = currentResult.getHandType();
			if (results.containsKey(currentHandType)) {
				results.put(currentHandType, results.get(currentHandType) + 1);
			} else {
				results.put(currentHandType, 1);
			}
		}
		
		int sum = 0;
		for (HandType handType : results.keySet()) {
			sum += results.get(handType);
		}
		
		System.out.format("%-15s%20s%10s%n", "Hand type", "#", "%");
		System.out.println("---------------------------------------------");
		for (HandType handType : results.keySet()) {
			int count = results.get(handType);
			double share = (double) count / (double) sum * 100;
			System.out.format("%-15s%20d%10.4f%n", handType.getName(), count, share);
		}
	}
	
}
