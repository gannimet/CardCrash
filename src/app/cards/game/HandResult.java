package app.cards.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import app.cards.Card;
import app.cards.RankBeforeSuitComparator;

public final class HandResult {
	
	private List<HandValue> breakdown;
	private HandType handType;

	HandResult(HandType handType, List<HandValue> breakdown) {
		this.handType = handType;
		this.breakdown = breakdown;
	}
	
	public HandType getHandType() {
		return handType;
	}

	public List<HandValue> getBreakdown() {
		return breakdown;
	}
	
	public void fillBreakdownWithBestUnusedCardsFrom(Set<Card> cards) {
		List<Card> sortedCards = new ArrayList<>();
		sortedCards.addAll(cards);
		
		Collections.sort(sortedCards, new RankBeforeSuitComparator());
		// it's easier when highest cards are on top of the list
		List<Card> reversedCards = Lists.reverse(sortedCards);
		
		for (int i = 0; breakdown.size() < 5; i++) {
			Card card = reversedCards.get(i);
			
			if (!isCardPartOfBreakdown(card)) {
				Set<Card> fillCardSet = new HashSet<Card>();
				fillCardSet.add(card);
				NOfAKind fillCardNOfAKind = new NOfAKind(fillCardSet);
				breakdown.add(fillCardNOfAKind);
			}
		}
	}
	
	private boolean isCardPartOfBreakdown(Card card) {
		for (HandValue handValue : breakdown) {
			Set<Card> handValueCards = handValue.getCards();
			if (handValueCards.contains(card)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return handType.getName() + ", breaks down as: " + breakdown;
	}
	
}
