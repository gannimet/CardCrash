package app.cards;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Card {

	private Suit suit;
	private Rank rank;
	
	private static Map<Suit, Map<Rank, Card>> cardMap = initMap();
	private static Set<Card> randomlyDealt = new HashSet<>();
	
	private Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	public static Card getCard(Suit suit, Rank rank) {
		return cardMap.get(suit).get(rank);
	}
	
	public static Card randomCard() {
		// Check whether there can possibly be any cards left in the deck
		int totalNumberOfCards = Suit.values().length * Rank.values().length;
		int numberOfCardsAlreadyDealt = randomlyDealt.size();
		if (numberOfCardsAlreadyDealt >= totalNumberOfCards) {
			throw new AllCardsDealtException();
		}
		
		Card randomCard;
		
		// Generate a random card as long as we have one that wasn't there already
		do {
			int randomSuitIndex = (int) (Math.random() * Suit.values().length);
			int randomRankIndex = (int) (Math.random() * Rank.values().length);
			
			Suit randomSuit = Suit.values()[randomSuitIndex];
			Rank randomRank = Rank.values()[randomRankIndex];
			
			randomCard = getCard(randomSuit, randomRank);
		} while (randomlyDealt.contains(randomCard));
		
		// Remember the newly generated card
		randomlyDealt.add(randomCard);
		
		return randomCard;
	}
	
	public static Card fromShortcut(String shortcut) {
		Rank rank = Rank.fromShortcut(shortcut.charAt(0));
		Suit suit = Suit.fromShortcut(shortcut.charAt(1));

		return getCard(suit, rank);
	}
	
	public void resetDeck() {
		randomlyDealt.clear();
	}

	public Suit getSuit() {
		return suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		return rank.getName() + " of " + suit.getName();
	}

	private static Map<Suit, Map<Rank, Card>> initMap() {
		Map<Suit, Map<Rank, Card>> cardMap = new HashMap<>();
		
		for (Suit suit : Suit.values()) {
			Map<Rank, Card> rankMap = new HashMap<>();
			for (Rank rank : Rank.values()) {
				rankMap.put(rank, new Card(suit, rank));
			}
			cardMap.put(suit, rankMap);
		}
		
		return cardMap;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Card)) {
			return false;
		}
		
		Card otherCard = (Card) o;
		return rank == otherCard.rank && suit == otherCard.suit;
	}
	
}
