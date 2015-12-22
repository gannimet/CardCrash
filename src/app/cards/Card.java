package app.cards;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A {@code Card} object represents a unique card from a deck.
 * 
 * {@code Card} objects are
 * created with the static initializer {@link Card#getCard(Suit, Rank)}. There is no public
 * constructor. Whenever {@code Card} objects with the same rank and suit are requested via
 * {@link Card#getCard(Suit, Rank)}, it is guaranteed that the same object is returned.
 */
public class Card {

	private Suit suit;
	private Rank rank;
	
	private static Map<Suit, Map<Rank, Card>> cardMap = initMap();
	private static Set<Card> randomlyDealt = new HashSet<>();
	
	private Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	/**
	 * Get a {@code Card} instance with the specified {@code suit} and {@code rank}.
	 * 
	 * Subsequent calls with the same {@code suit} and {@code rank} are guaranteed to
	 * get the same object.
	 * @param suit {@link Suit} of the card
	 * @param rank {@link Rank} of the card
	 * @return {@code Card} instance with {@code suit} and {@code rank}
	 */
	public static Card getCard(Suit suit, Rank rank) {
		return cardMap.get(suit).get(rank);
	}
	
	/**
	 * Returns a random card from the deck.
	 * 
	 * Subsequent calls will keep getting different cards until all of the deck is
	 * exhausted. If you call this method after every card has been returned once, an
	 * {@link AllCardsDealtException} will be thrown.
	 * 
	 * Call {@link #resetDeck()} to put all cards back in the deck and "reshuffle".
	 * @return {@code Card} instance with random {@code suit} and {@code rank}
	 * @throws AllCardsDealtException if this method is called after all cards
	 * have been dealt
	 */
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
	
	/**
	 * Creates a {@code Card} object from a shortcut like "As" (Ace of Spades) or
	 * "8c" (Eight of Clubs).
	 * @param shortcut two-character shortcut. The first character identifies the rank
	 * (as used by {@link Rank#fromShortcut(char)}), the second character identifies the
	 * suit (as used by {@link Suit#fromShortcut(char)})
	 * @return {@code Card} object identified by the {@code shortcut}. Subsequent calls
	 * with the same {@code shortcut} are guaranteed to get the same {@code Card} object.
	 * @see Rank#fromShortcut(char)
	 * @see Suit#fromShortcut(char)
	 */
	public static Card fromShortcut(String shortcut) {
		Rank rank = Rank.fromShortcut(shortcut.charAt(0));
		Suit suit = Suit.fromShortcut(shortcut.charAt(1));

		return getCard(suit, rank);
	}
	
	/**
	 * Clears all cards that have been previously dealt by calling {@link #randomCard()} and
	 * makes them available again.
	 */
	public static void resetDeck() {
		randomlyDealt.clear();
	}

	/**
	 * The suit of this card
	 * @return suit of this card
	 */
	public Suit getSuit() {
		return suit;
	}
	
	/**
	 * The rank of this card
	 * @return rank of this card
	 */
	public Rank getRank() {
		return rank;
	}
	
	/**
	 * Returns a human-readable name of this card.
	 * @return A human-readable {@link String} like "Ace of Clubs" or
	 * "Tray of Diamonds"
	 */
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

	/**
	 * Whether this {@code Card} equals the supplied {@link Object}. Cards are deemed equal
	 * if they match in both suit and rank.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Card)) {
			return false;
		}
		
		Card otherCard = (Card) o;
		return rank == otherCard.rank && suit == otherCard.suit;
	}
	
}
