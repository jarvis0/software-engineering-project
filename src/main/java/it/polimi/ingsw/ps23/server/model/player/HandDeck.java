package it.polimi.ingsw.ps23.server.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.map.Card;
/**
 * Provides methods to manage cards of a {@link player}
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public abstract class HandDeck implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1199458593907360821L;
	private List<Card> cards;

	protected HandDeck() {
		cards = new ArrayList<>();
	}
	
	void addCard(Card card) {
		cards.add(card);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	/**
	 * @return the number of cards in the specified deck.
	 */
	public int getHandSize() {
		return cards.size();
	}

	void removeCard(Card card) {
		 cards.remove(card);
	}

	Card getAndRemove(int chosenCard) {
		Card removedCard = cards.get(chosenCard);
		removeCard(removedCard);
		return removedCard;
	}
	/**
	 * Return a card in position index.
	 * @param index - the position of the card
	 * @return the chosen card.
	 * @throws InvalidCardException - If there is no card in that position throw an exception.
	 */
	public Card getCardInPosition(int index) throws InvalidCardException {
		if(index < 0 || index >= getHandSize()) {
			throw new InvalidCardException();
		}
		return cards.get(index);
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}
}
