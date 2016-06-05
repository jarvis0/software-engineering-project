package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.Councillor;
import it.polimi.ingsw.ps23.model.map.PoliticCard;

public class PoliticHandDeck extends HandDeck {

	private static final String MULTI = "multi";

	public PoliticHandDeck(List<Card> politicHandCards) {
		super();
		addCards(politicHandCards);
	}
	
	public void addCards(List<Card> politicHandCards) {
		for (Card politicHandCard : politicHandCards) {
			addCard(politicHandCard);
		}
	}
	
	public int removeCards(List<String> removedCards) {
		int cost = 0;
		for (String string : removedCards) {
			if(MULTI.equals(string)){
				cost--;
			}
		}
		if(removedCards.size() != 4) {
			cost += -(1 + 3 * (4 - removedCards.size()));
		}
	 
		for (String removeCard : removedCards) {
			boolean found = false;
			for(int i = 0; i < getCards().size() && !found; i++) {
				if(((PoliticCard)getCards().get(i)).getColor().getName().equals(removeCard)) {
					getCards().remove(i);
					found = true;
				}
			}
		}
		return cost;
	}					



	public HandDeck getAvailableCards(Council council) {
		List<Card> returnCards = getColoredCards(council);
		try {
			returnCards.addAll(getJollyCards());
		}
		catch (NullPointerException e) {
			//il player non ha carte multi
		}
		return new PoliticHandDeck(returnCards);
	}
	
	private List<Card> getColoredCards(Council council) {
		List<Card> cards = new ArrayList<>();
		cards.addAll(getCards());
		List<Card> returnCards = new ArrayList<>();
		for (Councillor councillor : council.getCouncil()) {
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
	
	private List<Card> getJollyCards() {
		List<Card> cards = new ArrayList<>();
		cards.addAll(getCards());
		for(int i = 0; i < cards.size(); i++) {
			if(!((PoliticCard)(cards.get(i))).isJolly()) {
				cards.remove(i);
				i--;
			}
		}
		return cards;
	}
	


}
