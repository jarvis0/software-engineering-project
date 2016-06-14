package it.polimi.ingsw.ps23.server.model.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;

public class PoliticHandDeck extends HandDeck {

	private static final String MULTI = "multi";

	public PoliticHandDeck(List<Card> politicHandCards) {
		super();
		addCards(politicHandCards);
	}
	
	void addCards(List<Card> politicHandCards) {
		for(Card politicHandCard : politicHandCards) {
			addCard(politicHandCard);
		}
	}
	
	public int removeCards(List<String> removedCards) {
		int cost = 0;
		for(String string : removedCards) {
			if(MULTI.equals(string)){
				cost--;
			}
		}
		if(removedCards.size() != 4) {
			cost += -(1 + 3 * (4 - removedCards.size()));
		}	 
		for(String removeCard : removedCards) {
			boolean found = false;
			for(int i = 0; i < getCards().size() && !found; i++) {
				if(((PoliticCard)getCards().get(i)).getColor().isSameColor(removeCard)) {
					getCards().remove(i);
					found = true;
				}
			}
		}
		return cost;
	}					

	public HandDeck getAvailableCards(Council council) {
		List<Card> returnCards = getColoredCards(council);
		returnCards.addAll(getJokerCards());
		return new PoliticHandDeck(returnCards);
	}
	
	private List<Card> getColoredCards(Council council) {
		List<Card> cards = new ArrayList<>();
		cards.addAll(getCards());
		List<Card> returnCards = new ArrayList<>();
		for(Councillor councillor : council.getCouncil()) {
			boolean found = false;
			for(int i = 0; i < cards.size() && !found; i++) {
				if(councillor.getColor() == ((PoliticCard)(cards.get(i))).getColor()) {
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
		for(int i = cards.size() - 1; i >= 0; i--) {
			if(!((PoliticCard)(cards.get(i))).isJoker()) {
				cards.remove(i);
			}
		}
		return cards;
	}
	
	public Card getCardFromName(String name) throws IOException {
		for (Card card : getCards()) {
			if(((PoliticCard)card).getColor().isSameColor(name)) {
				return card;
			}
		}
		throw new IOException();
	}

}
