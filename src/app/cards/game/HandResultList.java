package app.cards.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class HandResultList {

	private List<HandResult> allSingleResults = new ArrayList<>();
	private Multimap<Integer, HandResult> groupedResults = HashMultimap.create();
	private boolean dirty = false;
	
	public static HandResultList fromResults(HandResult... results) {
		HandResultList list = new HandResultList();
		
		for (HandResult result : results) {
			list.addResult(result);
		}
		
		return list;
	}
	
	public boolean addResult(HandResult result) {
		dirty = true;
		return allSingleResults.add(result);
	}
	
	public boolean addHand(Hand hand) {
		dirty = true;
		HandResult result = hand.evaluate();
		return allSingleResults.add(result);
	}
	
	public Collection<HandResult> getResultsRankedAt(int k) {
		if (dirty) {
			// resorting necessary
			sort();
		}
		
		return groupedResults.get(k);
	}
	
	public int getNumberOfSingleResults() {
		return allSingleResults.size();
	}
	
	public int getNumberOfPlaces() {
		if (dirty) {
			sort();
		}
		
		return groupedResults.keySet().size();
	}
	
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
