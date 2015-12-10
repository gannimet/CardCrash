package app.cards.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import app.cards.Card;
import app.cards.Rank;
import app.cards.RankBeforeSuitComparator;
import app.cards.Suit;

class EvaluationHelper {

	static CardSequence getLongestSequence(Set<Card> cards) {
		Set<CardSequence> allSequences = new HashSet<>();
		CardSequence currentSequence = new CardSequence();
		allSequences.add(currentSequence);
		List<Card> sortedCards = new ArrayList<>(cards);
		Collections.sort(sortedCards, new RankBeforeSuitComparator());
		
		// build up all sequences
		for (Card card : sortedCards) {
			if (card.getRank() != Rank.ACE && currentSequence.isEmpty()) {
				currentSequence.appendCard(card);
			} else if (card.getRank() != Rank.ACE && card.getRank().getValue() ==
					currentSequence.getRankOfLastElement().getValue() + 1) {
				// current card is successor of last in sequence
				currentSequence.appendCard(card);
			} else if (!currentSequence.isEmpty() &&
					card.getRank() == currentSequence.getRankOfLastElement()) {
				// special case for multiple possible straights
				// (important because one of them might be a straight flush)
				currentSequence.addCardToLastElement(card);
			} else if (card.getRank() == Rank.ACE) {
				// special case for wheel straights
				// we need to re-check all sequences so far
				for (CardSequence oldSequence : allSequences) {
					if (oldSequence.getRankOfFirstElement() == Rank.DEUCE) {
						// put the ace at the beginning of the possible wheel
						oldSequence.prependCard(card);
					} else if (oldSequence.getRankOfLastElement() == Rank.KING) {
						// put the ace at the end of a possible broadway
						oldSequence.appendCard(card);
					} else if (oldSequence.getRankOfFirstElement() == Rank.ACE) {
						// there is already an ace at the beginning, add this one as well
						// to offer more choice in case of a straight flush
						oldSequence.addCardToFirstElement(card);
					} else if (oldSequence.getRankOfLastElement() == Rank.ACE) {
						// there is already an ace at the end, add this one as well
						// to offer more choice in case of a straight flush
						oldSequence.addCardToLastElement(card);
					}
				}
			} else {
				// all cases fell through, sequence has ended
				allSequences.add(currentSequence);
				
				// start new
				currentSequence = new CardSequence();
				currentSequence.appendCard(card);
				allSequences.add(currentSequence);
			}
		}
		
		// flush out the last current sequence
		allSequences.add(currentSequence);
		
		// find the longest sequence
		CardSequence longestSequence = null;
		
		for (CardSequence sequence : allSequences) {
			if (longestSequence == null) {
				// any sequence is better than no sequence
				longestSequence = sequence;
			} else if (sequence.length() > longestSequence.length()) {
				longestSequence = sequence;
			} else if (sequence.length() == longestSequence.length()) {
				// if there's a tie, pick the sequence with the higher card
				if (sequence.getRankOfLastElement().getValue() >
						longestSequence.getRankOfLastElement().getValue()) {
					longestSequence = sequence;
				}
			}
		}
		
		return longestSequence;
	}
	
	public static Map<Rank, Set<Card>> groupCardsByRank(Set<Card> cards) {
		Map<Rank, Set<Card>> groupedRanks = new HashMap<>();
		
		for (Rank rank : Rank.values()) {
			groupedRanks.put(rank, new HashSet<Card>());
		}
		
		for (Card card : cards) {
			Rank rank = card.getRank();
			groupedRanks.get(rank).add(card);
		}
		
		return groupedRanks;
	}
	
	public static Map<Suit, Set<Card>> groupCardsBySuit(Set<Card> cards) {
		Map<Suit, Set<Card>> groupedSuits = new HashMap<>();
		
		for (Suit suit : Suit.values()) {
			groupedSuits.put(suit, new HashSet<Card>());
		}
		
		for (Card card : cards) {
			Suit suit = card.getSuit();
			groupedSuits.get(suit).add(card);
		}
		
		return groupedSuits;
	}
	
	public static List<NOfAKind> getNOfAKinds(Set<Card> cards) {
		Map<Rank, Set<Card>> groupedRanks = groupCardsByRank(cards);
		List<NOfAKind> nOfAKinds = new ArrayList<>();
		
		for (Rank rank : groupedRanks.keySet()) {
			Set<Card> candidate = groupedRanks.get(rank);
			if (candidate.size() > 0) {
				nOfAKinds.add(new NOfAKind(candidate));
			}
		}
		
		Collections.sort(nOfAKinds);
		
		return nOfAKinds;
	}
	
	static boolean areAllCardsOfTheSameSuit(Collection<Card> cards) {
		Suit referenceSuit = null;
		
		for (Card card : cards) {
			if (referenceSuit == null) {
				referenceSuit = card.getSuit();
			}
			
			if (referenceSuit != card.getSuit()) {
				return false;
			}
		}
		
		return true;
	}
	
	static boolean areAllCardsOfTheSameRank(Collection<Card> cards) {
		Rank referenceRank = null;
		
		for (Card card : cards) {
			if (referenceRank == null) {
				referenceRank = card.getRank();
			}
			
			if (referenceRank != card.getRank()) {
				return false;
			}
		}
		
		return true;
	}
	
}
