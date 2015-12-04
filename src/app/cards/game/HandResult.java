package app.cards.game;

import java.util.List;

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
	
	@Override
	public String toString() {
		return handType.getName() + ": " + breakdown;
	}
	
}
