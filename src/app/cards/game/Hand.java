package app.cards.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import app.cards.AllCardsDealtException;
import app.cards.Card;
import app.cards.Suit;

/**
 * <p>
 * This class represents a poker hand (hole cards + community cards) and offers a method for
 * evaluating the value of that hand ({@link #evaluate()}).
 * </p>
 * <p>
 * A {@code Hand} can either be created via the constructor {@link #Hand(Set)} supplying
 * a {@link Set} of {@link Card} objects or via one of several static creation methods
 * ({@link #fromCards(Card...)}, {@link #fromCards(Set, Card...)}, {@link #fromCards(Set, Set)} or
 * {@link #fromShortcuts(String...)}). Also, {@code Card}s can be added to the {@code Hand} later on
 * via {@link #addCard(Card)} or {@link #setCards(Set)}.
 * </p>
 * <p>
 * Every {@code Hand} object is automatically assigned a UUID upon creation that can be used to
 * later track down the "source" {@code Hand} of a {@link HandResult} as returned by
 * {@link #evaluate()}.
 * </p>
 */
public class Hand {

	private Set<Card> cards;
	private String id;
	
	/**
	 * The maximum number of cards per hand (7 by default)
	 */
	public static int MAX_NUMBER_OF_CARDS_PER_HAND = 7;
	
	/**
	 * Creates a new {@code Hand} from a {@link Set} of {@link Card}s
	 * @param cards {@code Card}s to be used in the hand
	 * @throws IllegalHandException if {@code cards.size() > MAX_NUMBER_OF_CARDS_PER_HAND}
	 */
	public Hand(Set<Card> cards) {
		if (isValidCardSet(cards)) {
			this.cards = cards;
			this.setId(UUID.randomUUID().toString());
		} else {
			throw new IllegalHandException();
		}
	}
	
	/**
	 * Creates a new {@code Hand} from the supplied shortcuts
	 * @param shortcuts shortcuts for all {@link Card}s to be used in this hand
	 * @return The newly created {@code Hand} object
	 * @throws IllegalHandException if the number of supplied shortcuts is greater than
	 * {@code MAX_NUMBER_OF_CARDS_PER_HAND}
	 * @see Card#fromShortcut(String)
	 */
	public static Hand fromShortcuts(String... shortcuts) {
		Set<Card> cards = new HashSet<>();
		
		for (String shortcut : shortcuts) {
			Card card = Card.fromShortcut(shortcut);
			cards.add(card);
		}
		
		return new Hand(cards);
	}
	
	/**
	 * Creates a new {@code Hand} from the supplied {@link Card}s
	 * @param cards all {@code Card}s to be used in this hand
	 * @return The newly created {@code Hand} object
	 * @throws IllegalHandException if the number of supplied {@code Card}s is greater than
	 * {@code MAX_NUMBER_OF_CARDS_PER_HAND}
	 */
	public static Hand fromCards(Card... cards) {
		Set<Card> cardSet = new HashSet<>();
		
		for (Card card : cards) {
			cardSet.add(card);
		}
		
		return new Hand(cardSet);
	}
	
	/**
	 * <p>Creates a new {@code Hand} from all the supplied {@link Card}s</p>
	 * <p>
	 * Can be used as a convenience method in case the client code holds boards cards and
	 * hole cards in different data structures. All supplied {@code Card}s will be merged into
	 * one {@link Set} from which the {@code Hand} will be created.
	 * </p>
	 * @param baseCards {@code Cards} used as the community cards
	 * @param holeCards {@code Cards} used as the hole cards of a player
	 * @return The newly created {@code Hand} object
	 * @throws IllegalHandException if the total number of supplied {@code Card}s is greater than
	 * {@code MAX_NUMBER_OF_CARDS_PER_HAND}
	 */
	public static Hand fromCards(Set<Card> baseCards, Card... holeCards) {
		Set<Card> cardSet = new HashSet<>();
		cardSet.addAll(baseCards);
		
		for (Card holeCard : holeCards) {
			cardSet.add(holeCard);
		}
		
		return new Hand(cardSet);
	}
	
	/**
	 * <p>Creates a new {@code Hand} from all the supplied {@link Card}s</p>
	 * <p>
	 * Can be used as a convenience method in case the client code holds boards cards and
	 * hole cards in different data structures. All supplied {@code Card}s will be merged into
	 * one {@link Set} from which the {@code Hand} will be created.
	 * </p>
	 * @param baseCards {@code Cards} used as the community cards
	 * @param holeCards {@code Cards} used as the hole cards of a player
	 * @return The newly created {@code Hand} object
	 * @throws IllegalHandException if the total number of supplied {@code Card}s is greater than
	 * {@code MAX_NUMBER_OF_CARDS_PER_HAND}
	 */
	public static Hand fromCards(Set<Card> baseCards, Set<Card> holeCards) {
		Set<Card> cardSet = new HashSet<>();
		cardSet.addAll(baseCards);
		cardSet.addAll(holeCards);
		
		return new Hand(cardSet);
	}
	
	/**
	 * Creates a new {@code Hand} with random {@link Card}s
	 * @param numberOfCards number of random {@code Card}s to be generated for the {@code Hand}
	 * @return The newly created {@code Hand}
	 * @throws AllCardsDealtException if {@code numberOfCards} is greater than the total number of
	 * cards in the deck
	 */
	public static Hand randomHand(int numberOfCards) {
		Set<Card> cards = new HashSet<>();
		
		for (int i = 0; i < numberOfCards; i++) {
			cards.add(Card.randomCard());
		}
		
		return new Hand(cards);
	}
	
	/**
	 * All {@link Card}s in this {@code Hand}
	 * @return A {@link Set} of all {@code Card}s in this {@code Hand}
	 */
	public Set<Card> getCards() {
		return cards;
	}

	/**
	 * Set the {@link Card}s of this {@code Hand}
	 * @param cards a {@link Set} of {@code Card}s to be used in this {@code Hand}
	 * @throws IllegalHandException if {@code cards.size() > MAX_NUMBER_OF_CARDS_PER_HAND}
	 */
	public void setCards(Set<Card> cards) {
		if (!isValidCardSet(cards)) {
			throw new IllegalHandException();
		}
		
		this.cards = cards;
	}
	
	/**
	 * Add a {@link Card} to this {@code Hand}
	 * @param card the {@code Card} to be added to the {@code Hand}
	 * @throws IllegalHandException if {@code cards.size() > MAX_NUMBER_OF_CARDS_PER_HAND}
	 */
	public void addCard(Card card) {
		if (cards.size() < MAX_NUMBER_OF_CARDS_PER_HAND && !cards.contains(card)) {
			cards.add(card);
		} else {
			throw new IllegalHandException();
		}
	}

	/**
	 * The ID of this {@code Hand}
	 * @return ID of this {@code Hand}.
	 * @see #setId(String)
	 */
	public String getId() {
		return id;
	}

	/**
	 * <p>Set the ID of this {@code Hand}</p>
	 * <p>
	 * The ID will be passed into the {@link HandResult} object when {@link #evaluate()} is
	 * called to allow for hand tracking. If the client code holds a player's name associated
	 * with this hand, it could be reasonable to use that as the {@code Hand}'s ID to be able
	 * to later identify which player's {@code Hand} yielded which {@code HandResult}.
	 * </p>
	 * <p>
	 * By default, a UUID is automatically generated upon creation of a {@code Hand}.
	 * </p>
	 * @param id the ID to be used for this {@code Hand}
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Whether the supplied {@link Set} contains at most {@code MAX_NUMBER_OF_CARDS_PER_HAND}
	 * {@link Card}s
	 * @param cards the {@code Set} to be investigated
	 * @return {@code true}, if {@code cards.size() <= MAX_NUMBER_OF_CARDS_PER_HAND}, {@code false}
	 * otherwise
	 */
	private boolean isValidCardSet(Set<Card> cards) {
		return cards.size() <= MAX_NUMBER_OF_CARDS_PER_HAND;
	}
	
	/**
	 * Evaluate the {@link Card}s of this {@code Hand} and report the result as a {@link HandResult}
	 * @return The {@code HandResult} encapsulating the type of hand found and a detailed breakdown.
	 * The {@code HandResult} will also be passed this {@code Hand}'s ID to allow for identifying
	 * which {@code Hand} has generated the {@code HandResult}.
	 * @throws InvalidHandEvaluationException if none of the {@link HandType}s could be identified
	 * within this {@code Hand}'s cards
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
				return new HandResult(HandType.ROYAL_FLUSH, breakdown, id);
			} else if (straight.isFlush()) {
				// second best possible hand
				return new HandResult(HandType.STRAIGHT_FLUSH, breakdown, id);
			}
			
			// obviously the straight was neither royal nor a straight flush
			// so we just remember it for after we checked for four of a kinds
			// and full houses
		}
		
		// next best hand would be four of a kind
		if (nOfAKinds.get(0).getN() == 4) {
			// there it is
			breakdown.add(nOfAKinds.get(0));
			HandResult result = new HandResult(HandType.FOUR_OF_A_KIND, breakdown, id);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		// next best hand would be full house
		if (nOfAKinds.size() > 1 && nOfAKinds.get(0).getN() == 3 && nOfAKinds.get(1).getN() >= 2) {
			// there it is
			FullHouse fullHouse = FullHouse.makeBestFullHouse(nOfAKinds);
			breakdown.add(fullHouse);
			return new HandResult(HandType.FULL_HOUSE, breakdown, id);
		}
		
		// next best hand would be a flush
		if (cardsGroupedBySuit.get(Suit.CLUBS).size() >= 5) {
			// club flush is best
			Flush flush = Flush.makeBestFlush(cardsGroupedBySuit.get(Suit.CLUBS));
			breakdown.add(flush);
			return new HandResult(HandType.FLUSH, breakdown, id);
		} else if (cardsGroupedBySuit.get(Suit.DIAMONDS).size() >= 5) {
			// diamonds still pretty good
			Flush flush = Flush.makeBestFlush(cardsGroupedBySuit.get(Suit.DIAMONDS));
			breakdown.add(flush);
			return new HandResult(HandType.FLUSH, breakdown, id);
		} else if (cardsGroupedBySuit.get(Suit.HEARTS).size() >= 5) {
			// hearts still decent
			Flush flush = Flush.makeBestFlush(cardsGroupedBySuit.get(Suit.HEARTS));
			breakdown.add(flush);
			return new HandResult(HandType.FLUSH, breakdown, id);
		} else if (cardsGroupedBySuit.get(Suit.SPADES).size() >= 5) {
			// spades... well, at least it's still a flush
			Flush flush = Flush.makeBestFlush(cardsGroupedBySuit.get(Suit.SPADES));
			breakdown.add(flush);
			return new HandResult(HandType.FLUSH, breakdown, id);
		}
		
		// if we found a straight earlier and it survived till now, it means it's
		// the best we can do with this hand!
		if (straight != null) {
			return new HandResult(HandType.STRAIGHT, breakdown, id);
		}
		
		// next best hand would be a three of a kind
		if (nOfAKinds.size() >= 1 && nOfAKinds.get(0).getN() == 3) {
			breakdown.add(nOfAKinds.get(0));
			HandResult result = new HandResult(HandType.THREE_OF_A_KIND, breakdown, id);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		// next best hand would be two pair
		if (nOfAKinds.size() >= 2 && nOfAKinds.get(0).getN() == 2 &&
				nOfAKinds.get(1).getN() == 2) {
			breakdown.add(nOfAKinds.get(0));
			breakdown.add(nOfAKinds.get(1));
			HandResult result = new HandResult(HandType.TWO_PAIR, breakdown, id);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		// next best hand would be one pair
		if (nOfAKinds.size() >= 1 && nOfAKinds.get(0).getN() == 2) {
			breakdown.add(nOfAKinds.get(0));
			HandResult result = new HandResult(HandType.ONE_PAIR, breakdown, id);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		// now there's only high card left
		if (nOfAKinds.size() >= 1 && nOfAKinds.get(0).getN() == 1) {
			breakdown.add(nOfAKinds.get(0));
			HandResult result = new HandResult(HandType.HIGH_CARD, breakdown, id);
			result.fillBreakdownWithBestUnusedCardsFrom(cards);
			return result;
		}
		
		throw new InvalidHandEvaluationException();
	}
	
}
