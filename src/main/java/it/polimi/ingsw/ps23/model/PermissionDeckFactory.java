package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.PermissionCard;
import it.polimi.ingsw.ps23.model.map.PermissionDeck;

public class PermissionDeckFactory extends DeckFactory {

	@Override
	public Deck makeDeck(List<String[]> rawPermissionCards) {
		ArrayList<Card> permissionCards = new ArrayList<>();
		BonusCache.loadCache();
		String[] fields = rawPermissionCards.remove(rawPermissionCards.size() - 1);
		for(String[] rawPermissionCard : rawPermissionCards) {
			PermissionCard permissionCard = new PermissionCard();
			permissionCards.add((PermissionCard) new BonusesFactory().makeBonuses(fields, rawPermissionCard, permissionCard));
		}
		return new PermissionDeck(permissionCards);
	}
}
