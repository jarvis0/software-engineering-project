package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.PermissionCard;
import it.polimi.ingsw.ps23.model.map.PermissionDeck;

public class PermissionDeckFactory extends DeckFactory {

	public PermissionDeckFactory() {
		super();
	}
	
	public Deck makeDeck(List<String[]> rawPermissionCardsBonuses, List<String[]> rawPermissionCardsCities) {
		BonusCache.loadCache();
		String[] fields = rawPermissionCardsBonuses.remove(rawPermissionCardsBonuses.size() - 1);
		int i = 0;
		for(String[] rawPermissionCard : rawPermissionCardsBonuses) {
			PermissionCard permissionCard = new PermissionCard();
			for(String rawCity : rawPermissionCardsCities.get(i)) {
				permissionCard.addCity(rawCity);
			}
			getCards().add((PermissionCard) new BonusesFactory().makeBonuses(fields, rawPermissionCard, permissionCard));
			i++;
		}
		return new PermissionDeck(getCards());
	}
	
}
