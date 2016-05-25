package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.map.BonusSlot;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.PermissionCard;
import it.polimi.ingsw.ps23.model.map.PermissionDeck;

public class PermissionDecksFactory extends DeckFactory {

	private static final int BONUSES_NUMBER = 6;
	
	//super-class is never used
	public PermissionDecksFactory() {
		super();
	}
	
	public Map<String, Deck> makeDecks(List<String[]> rawPermissionCards, Map<String, City> cities) {
		List<Card> cards;
		Map<String, List<Card>> cardsMap = new HashMap<>();
		BonusCache.loadCache();
		String[] fields = rawPermissionCards.remove(rawPermissionCards.size() - 1);
		for(String[] rawPermissionCard : rawPermissionCards) {
			String[] rawBonuses = subString(rawPermissionCard);
			BonusSlot permissionCard = new PermissionCard();
			
			String cityName;
			for(int i = BONUSES_NUMBER + 1; i < rawPermissionCard.length; i++) {
				cityName = new String();
				cityName = cityName + rawPermissionCard[i];
				((PermissionCard) permissionCard).addCity(cities.get(cityName));
			}
			
			String regionName = rawPermissionCard[BONUSES_NUMBER];
			if(!cardsMap.containsKey(regionName)) {
				cards = new ArrayList<>();
				cardsMap.put(regionName, cards);
			}
			cardsMap.get(regionName).add((PermissionCard) new BonusesFactory().makeBonuses(fields, rawBonuses, permissionCard));
		}
		Map<String, Deck> permissionDecks = new HashMap<>();
		for(Entry<String, List<Card>> decks : cardsMap.entrySet()) {
			permissionDecks.put(decks.getKey(), new PermissionDeck(decks.getValue()));
		}
		return permissionDecks;
	}
	
	private String[] subString(String[] rawPermissionCard) {
		String[] rawBonuses = new String[BONUSES_NUMBER];
		for(int i = 0; i < BONUSES_NUMBER; i++) {
			rawBonuses[i] = rawPermissionCard[i];
		}
		return rawBonuses;
	}

}
