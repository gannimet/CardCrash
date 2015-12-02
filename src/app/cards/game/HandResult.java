package app.cards.game;

import java.util.List;

public final class HandResult {
	
	private List<HandValue> handTypeBreakdown;
	private HandType handType;

	HandResult(HandType handType, List<HandValue> handTypeBreakdown) {
		this.handType = handType;
		this.handTypeBreakdown = handTypeBreakdown;
	}
	
	HandResult(HandType handType) {
		this(handType, null);
	}

	public HandType getHandType() {
		return handType;
	}

	public List<HandValue> getHandTypeBreakdown() {
		return handTypeBreakdown;
	}
	
}
