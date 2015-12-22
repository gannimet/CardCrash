package app.cards.game;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import app.cards.Card;
import app.cards.Rank;

/**
 * <p>
 * A {@code CardSequence} encapsulates a list of {@link Card} object with consecutive {@link Rank}s.
 * </p>
 * 
 * <p>
 * More specifically, each position in the list can take a {@link Set} of {@link Card}s with
 * equal rank. The {@code Set} at the next position in the list then contains cards of the
 * next highest rank and so on. A valid {@code CardSequence} could look like the following
 * (each {@code Set} is grouped in square brackets):
 * </p>
 * 
 * <p>
 * [[6s], [7h, 7s], [8c], [9d, 9h]]
 * </p>
 * 
 * <p>
 * A {@code CardSequence} object can then be used as the basis for {@link Straight} detection.
 * </p>
 */
class CardSequence implements Iterable<Set<Card>> {

	private List<Set<Card>> sequence;
	
	/**
	 * Creates an initially empty {@code CardSequence}
	 */
	CardSequence() {
		sequence = new ArrayList<>();
	}
	
	/**
	 * Creates a {@code CardSequence} from the supplied {@link List}
	 * @param sequence A {@link List} of {@link Set} of {@link Card}s containing the card sequence
	 * 
	 * The supplied list should stick to the rules described in the class description: each set
	 * containing only cards of equal rank) and consecutive sets containing cards of consecutive ranks.
	 */
	CardSequence(List<Set<Card>> sequence) {
		this.sequence = sequence;
	}
	
	/**
	 * Whether the sequence contains no elements
	 * @return {@code true}, if the sequence contains 0 elements, {@code false} otherwise
	 * @see List#isEmpty()
	 */
	boolean isEmpty() {
		return sequence.isEmpty();
	}
	
	/**
	 * The number of {@link Set}s in the sequence.
	 * @return The number of {@code Set}s in the sequence
	 * 
	 * NOTE: does not equal the total number of {@link Card}s over all sets! Can be used for straight
	 * detection (where a length of at least 5 would be necessary).
	 * @see List#size()
	 */
	int length() {
		return sequence.size();
	}
	
	/**
	 * Return the {@link Set} of {@link Card}s at position {@code index} in the list
	 * @param index position of the {@code Set} to be returned
	 * @return {@code Set} of {@code Card}s at position {@code index} in the list.
	 * @see List#get(int)
	 */
	Set<Card> cardsAt(int index) {
		return sequence.get(index);
	}
	
	/**
	 * Returns a {@link Card} from the {@link Set} at position {@code index}.
	 * @param index position in the list from which a card should be returned
	 * @return Any {@code Card} object from the {@code Set} at position {@code index}
	 * @see Set#iterator()
	 * @see Iterator#next()
	 */
	Card anyCardAt(int index) {
		return sequence.get(index).iterator().next();
	}
	
	/**
	 * Returns the internal representation of this sequence
	 * @return The internal representation of this sequence  as a {@link List} of {@link Set}s of {@link Card}s
	 */
	List<Set<Card>> allSets() {
		return sequence;
	}
	
	/**
	 * The highest rank in this sequence
	 * @return The rank of a {@link Card} in the last {@link Set} in the {@link List}, or {@code null} if the list
	 * is empty
	 */
	Rank getRankOfLastElement() {
		if (!isEmpty()) {
			return sequence.get(sequence.size() - 1).iterator().next().getRank();
		}
		
		return null;
	}
	
	/**
	 * The lowest rank in this sequence
	 * @return The rank of a {@link Card} in the first {@link Set} in the {@link List}, or {@code null} if the list
	 * is empty
	 */
	Rank getRankOfFirstElement() {
		if (!isEmpty()) {
			return sequence.get(0).iterator().next().getRank();
		}
		
		return null;
	}
	
	/**
	 * Adds a new {@link Set} at the end of the {@link List} and adds the supplied {@link Card} to it
	 * @param card {@code Card} object to be added inside a new {@code Set} at the end of the {@code List}
	 */
	void appendCard(Card card) {
		Set<Card> newSequenceEntry = new HashSet<>();
		newSequenceEntry.add(card);
		sequence.add(newSequenceEntry);
	}
	
	/**
	 * Adds a new {@link Set} at the beginning of the {@link List} and adds the supplied {@link Card} to it
	 * @param card {@code Card} object to be added inside a new {@code Set} at the beginning of the {@code List}
	 */
	void prependCard(Card card) {
		Set<Card> newSequenceEntry = new HashSet<>();
		newSequenceEntry.add(card);
		sequence.add(0, newSequenceEntry);
	}
	
	/**
	 * Adds a {@link Card} to the {@link Set} at the beginning of the {@link List}
	 * @param card {@code Card} object to be added to the first {@code Set} in the {@code List}
	 */
	void addCardToFirstElement(Card card) {
		sequence.get(0).add(card);
	}
	
	/**
	 * Adds a {@link Card} to the {@link Set} at the end of the {@link List}
	 * @param card {@code Card} object to be added to the last {@code Set} in the {@code List}
	 */
	void addCardToLastElement(Card card) {
		sequence.get(sequence.size() - 1).add(card);
	}

	/**
	 * An {@link Iterator} for the {@link List} encapsulated by an object of this class
	 * @see Iterator
	 */
	@Override
	public Iterator<Set<Card>> iterator() {
		return sequence.iterator();
	}

	/**
	 * A human-readable representation of the {@link List} encapsulated by an object of this class
	 * @see AbstractCollection#toString() 
	 */
	@Override
	public String toString() {
		return sequence.toString();
	}
	
}
