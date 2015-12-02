package app.cards.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import app.cards.Card;
import app.cards.Rank;

public class CardSequence implements Iterable<Set<Card>> {

	private List<Set<Card>> sequence;
	
	public CardSequence() {
		sequence = new ArrayList<>();
	}
	
	public CardSequence(List<Set<Card>> sequence) {
		this.sequence = sequence;
	}
	
	public boolean isEmpty() {
		return sequence.isEmpty();
	}
	
	public int length() {
		return sequence.size();
	}
	
	public Set<Card> cardsAt(int index) {
		return sequence.get(index);
	}
	
	public Card anyCardAt(int index) {
		return sequence.get(index).iterator().next();
	}
	
	public List<Set<Card>> allSets() {
		return sequence;
	}
	
	public Rank getRankOfLastElement() {
		if (!isEmpty()) {
			return sequence.get(sequence.size() - 1).iterator().next().getRank();
		}
		
		return null;
	}
	
	public Rank getRankOfFirstElement() {
		if (!isEmpty()) {
			return sequence.get(0).iterator().next().getRank();
		}
		
		return null;
	}
	
	public void appendCard(Card card) {
		Set<Card> newSequenceEntry = new HashSet<>();
		newSequenceEntry.add(card);
		sequence.add(newSequenceEntry);
	}
	
	public void prependCard(Card card) {
		Set<Card> newSequenceEntry = new HashSet<>();
		newSequenceEntry.add(card);
		sequence.add(0, newSequenceEntry);
	}
	
	public void addCardToFirstElement(Card card) {
		sequence.get(0).add(card);
	}
	
	public void addCardToLastElement(Card card) {
		sequence.get(sequence.size() - 1).add(card);
	}

	@Override
	public Iterator<Set<Card>> iterator() {
		return sequence.iterator();
	}

	@Override
	public String toString() {
		return sequence.toString();
	}
	
}
