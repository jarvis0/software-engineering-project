package it.polimi.ingsw.ps23.server.model.player;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
/**
 * Provides methods to manage {@link PoliticCard} of a {@link Player}
 * @author Akessabdro Erba & Giuseppe Mascellaro & Manzoni Mirco
 *
 */
public class PoliticHandDeck extends HandDeck {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5573053817139705040L;
	private static final String MULTI = "multi";
	/**
	 * Constructs the objects initializing the list with starting {@link PolitcCard}.
	 * @param politicHandCards - list of starting cards
	 */
	public PoliticHandDeck(List<Card> politicHandCards) {
		super();
		addCards(politicHandCards);
	}

	void addCards(List<Card> politicHandCards) {
		for (Card politicHandCard : politicHandCards) {
			addCard(politicHandCard);
		}
	}

	private void checkCards(List<String> removedCards) throws InvalidCardException {
		List<Integer> indexList = new ArrayList<>();
		for (String removeCard : removedCards) {
			boolean found = false;
			for (int i = 0; i < getCards().size() && !found; i++) {
				if (((PoliticCard) getCards().get(i)).getColor().isSameColor(removeCard) && !indexList.contains(i)) {
					found = true;
					indexList.add(i);
				}
			}
			if (!found) {
				throw new InvalidCardException();
			}
		}
	}
	/**
	 * Check the selected {@link PoliticCard} and generate the cost to perform the {@link Action}.
	 * @see {@link BuildingEmporiumPermitTile}
	 * @see [{@link BuildingEmporiumKing}
	 * @param removedCards - cards to calculate the cost
	 * @return the cost of the action
	 * @throws InvalidCardException if the player select a card that not exists in his pool
	 */
	public int checkCost(List<String> removedCards) throws InvalidCardException {
		checkCards(removedCards);
		int cost = 0;
		for (String string : removedCards) {
			if (MULTI.equals(string)) {
				cost--;
			}
		}
		if (removedCards.size() != 4) {
			cost += -(1 + 3 * (4 - removedCards.size()));
		}
		return cost;
	}
	/**
	 * Removes a list of cards from the pool.
	 * @param removedCards - cards to remove
	 */
	public void removeCards(List<String> removedCards) {
		for (String removeCard : removedCards) {
			boolean found = false;
			int i = 0;
			while (!found) {
				if (((PoliticCard) getCards().get(i)).getColor().isSameColor(removeCard)) {
					getCards().remove(i);
					found = true;
				}
				i++;
			}
		}
	}
	/**
	 * Gets a Politic Hand Deck composed by cards owned by a {@link Player} and cards that can satisfy 
	 * the selected {@link Council}.
	 * @param council - council to satisfy
	 * @return a politic new politic hand deck with the selected cards
	 */
	public HandDeck getAvailableCards(Council council) {
		List<Card> returnCards = getColoredCards(council);
		returnCards.addAll(getJokerCards());
		return new PoliticHandDeck(returnCards);
	}

	private List<Card> getColoredCards(Council council) {
		List<Card> cards = new ArrayList<>();
		cards.addAll(getCards());
		List<Card> returnCards = new ArrayList<>();
		for(Councillor councillor : council.getCouncillors()) {
			boolean found = false;
			for (int i = 0; i < cards.size() && !found; i++) {
				if (councillor.getColor() == ((PoliticCard) (cards.get(i))).getColor()) {
					returnCards.add(cards.remove(i));
					found = true;
				}
			}
		}
		return returnCards;
	}

	private List<Card> getJokerCards() {
		List<Card> cards = new ArrayList<>();
		cards.addAll(getCards());
		for (int i = cards.size() - 1; i >= 0; i--) {
			if (!((PoliticCard) (cards.get(i))).isJoker()) {
				cards.remove(i);
			}
		}
		return cards;
	}

	public int getJokerCardsNumber() {
		return getJokerCards().size();
	}
	/**
	 * Return a list of {@link Card} starting from a list of names. 
	 * @param names - list of names of cards
	 * @return list of cards with that names
	 * @throws InvalidCardException if the name of a card doesn't exist in this pool
	 */
	public List<Card> getCardsByName(List<String> names) throws InvalidCardException {
		List<Card> listCard = new ArrayList<>();
		List<Card> returnList = new ArrayList<>();
		listCard.addAll(getCards());
		for (String name : names) {
			boolean found = false;
			for (int i = 0; i < listCard.size() && !found; i++) {
				if (((PoliticCard) listCard.get(i)).getColor().isSameColor(name)) {
					found = true;
					returnList.add(listCard.remove(i));			
				}
			}
			if(!found) {
				throw new InvalidCardException();
			}
		}
		return returnList;
	}

}
