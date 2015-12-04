package app.cards.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import app.cards.Card;

class SequenceBuilder {

	public static CardSequence buildSequence(String sequenceString) {
		String[] parts = sequenceString.split(Pattern.quote("|"));
		trimAll(parts);

		List<Set<Card>> allSets = new ArrayList<>();
		for (String part : parts) {
			Set<Card> currentSet = new HashSet<>();

			String[] setParts = part.split(",");
			for (String setPart : setParts) {
				currentSet.add(Card.fromShortcut(setPart.trim()));
			}

			allSets.add(currentSet);
		}
		
		return new CardSequence(allSets);
	}

	private static void trimAll(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].trim();
		}
	}
	
	public static void main(String[] args) {
		CardSequence sequence = buildSequence("4s, 4d, 4c | 5s | 6s, 6c | 7s, 7h | 8s | 9c");
		Straight bestStraight = Straight.makeBestStraight(sequence);
		
		System.out.println(bestStraight);
	}
	
}
