package app.cards.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * <p>
 * This class maintains a {@link List} of {@link HandResult}s and offers funtionality to rank these results based on
 * their {@link HandValue}s, including tie detection.
 * </p>
 * <p>
 * Newly created {@code HandResult}s will automatically be ranked inside the list, so subsequent calls asking for
 * the ranking of a specific {@code HandResult} will get the correct answer without any extra work.
 * </p>
 */
public class HandResultList {

	private List<HandResult> allSingleResults = new ArrayList<>();
	private Multimap<Integer, HandResult> groupedResults = HashMultimap.create();
	private boolean dirty = false;
	
	/**
	 * Creates a new {@code HandResultList} from the supplied {@link HandResult}s
	 * @param results the {@code HandResult}s to rank
	 * @return The newly created {@code HandResultList}
	 */
	public static HandResultList fromResults(HandResult... results) {
		HandResultList list = new HandResultList();
		
		for (HandResult result : results) {
			list.addResult(result);
		}
		
		return list;
	}
	
	/**
	 * Adds a {@link HandResult} to this {@code HandResultList}
	 * @param result the {@code HandResult} to be added
	 * @return whether the addition of the {@code HandResult} was successful
	 */
	public boolean addResult(HandResult result) {
		dirty = true;
		return allSingleResults.add(result);
	}
	
	/**
	 * Evaluates a {@link Hand} and adds the {@link HandResult} to this {@code HandResultList}
	 * @param hand the {@code Hand} whose evaluated {@code HandResult} is to be added
	 * @return whether the addition of {@code hand}'s {@code HandResult} was successful
	 */
	public boolean addHand(Hand hand) {
		dirty = true;
		HandResult result = hand.evaluate();
		return allSingleResults.add(result);
	}
	
	/**
	 * All {@link HandResult}s ranked at the supplied position
	 * @param k the position in the ranking for which to return the {@code HandResult}s ranked there
	 * @return A {@link Collection} of {@code HandResult}s ranked at position {@code k} inside this {@code HandResultList}.
	 * The {@code Collection} may contain more than one item in case of a tie.
	 */
	public Collection<HandResult> getResultsRankedAt(int k) {
		if (dirty) {
			// resorting necessary
			sort();
		}
		
		return groupedResults.get(k);
	}
	
	/**
	 * The total number of {@link HandResult}s that have been added to this {@code HandResultList}
	 * @return The total number of {@link HandResult}s that have been added to this {@code HandResultList} (including the ones added
	 * indirectly via {@link #addHand(Hand)})
	 */
	public int getNumberOfSingleResults() {
		return allSingleResults.size();
	}
	
	/**
	 * <p>The number of unique positions in the ranking</p>
	 * <p>
	 * There may be positions containing multiple {@link HandResult}s in case of a tie, which will only count as one place in terms
	 * of this method.
	 * </p>
	 * @return The number of unique positions in the ranking
	 */
	public int getNumberOfPlaces() {
		if (dirty) {
			sort();
		}
		
		return groupedResults.keySet().size();
	}
	
	/**
	 * The position in the ranking for the {@link HandResult} with the supplied id
	 * @param id the id of the {@code HandResult} for which to look up the position
	 * @return The position in the ranking of the {@code HandResult} with ID {@code id}
	 * @throws ResultIdNotPresentInResultListException if a {@code HandResult} with ID {@code id} could not be found
	 */
	public int getPlaceForResultId(String id) {
		if (dirty) {
			sort();
		}
		
		for (int i = 1; i <= getNumberOfPlaces(); i++) {
			if (doesPlaceContainResultId(i, id)) {
				return i;
			}
		}
		
		throw new ResultIdNotPresentInResultListException(id);
	}
	
	private boolean doesPlaceContainResultId(int place, String id) {
		Collection<HandResult> results = groupedResults.get(place);
		
		for (HandResult result : results) {
			if (result.getId().equals(id)) {
				return true;
			}
		}
		
		return false;
	}
	
	private void sort() {
		// sort the list of results so order is guaranteed
		Collections.sort(allSingleResults);
		
		// group equal results together
		int currentPlace = 1;
		for (HandResult result : allSingleResults) {
			if (!groupedResults.keySet().contains(currentPlace)) {
				// place not defined, so put the current result there as first entry
				groupedResults.put(currentPlace, result);
			} else {
				// check whether the current result is equal to one in the current place
				// if so, put it there
				HandResult referenceResult = groupedResults.get(currentPlace).iterator().next();
				if (result.compareTo(referenceResult) == 0) {
					// yes, they are equal
					groupedResults.put(currentPlace, result);
				} else {
					// no, not equal, so current result is one place worse
					currentPlace++;
					groupedResults.put(currentPlace, result);
				}
			}
		}
		
		// all clean again
		dirty = false;
	}
	
}
