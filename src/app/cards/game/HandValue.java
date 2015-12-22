package app.cards.game;

import java.util.Collection;

import app.cards.Card;

/**
 * This interface represents any assortment of {@link Card}s that have showdown value.
 * By default it requires nothing but the implementing class to offer a method
 * {@link #getCards()} to expose a {@link Collection} of its cards.
 */
public interface HandValue {

	/**
	 * A {@link Collection} of all {@link Card}s of this {@code HandValue}
	 * @return All {@code Card}s whose combination constitutes this specific {@code HandValue} 
	 */
	public Collection<Card> getCards();
	
}
