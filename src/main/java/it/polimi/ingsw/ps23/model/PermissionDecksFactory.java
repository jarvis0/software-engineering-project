package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.map.BonusSlot;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.PermissionCard;
import it.polimi.ingsw.ps23.model.map.PermissionDeck;

public class PermissionDecksFactory {

	private static final int BONUSES_NUMBER = 6;
	
	private List<String[]> rawPermissionCards;
	private Map<String, City> cities;
	
	public PermissionDecksFactory(List<String[]> rawPermissionCards, Map<String, City> cities) {
		super();
		this.rawPermissionCards = rawPermissionCards;
		this.cities = cities;
	}
	
	private String[] subString(String[] rawPermissionCard) {
		String[] rawBonuses = new String[BONUSES_NUMBER];
		for(int i = 0; i < BONUSES_NUMBER; i++) {
			rawBonuses[i] = rawPermissionCard[i];
		}
		return rawBonuses;
	}
	
	private BonusSlot addCitiesToPermissionCard(String[] rawPermissionCard) {
		String cityName;
		BonusSlot permissionCard = new PermissionCard();
		for(int i = BONUSES_NUMBER + 1; i < rawPermissionCard.length; i++) {
			cityName = new String();
			cityName = cityName + rawPermissionCard[i];
			((PermissionCard) permissionCard).addCity(cities.get(cityName));
		}
		return permissionCard;
	}

	private Map<String, Deck> toDecks(Map<String, List<Card>> cardsMap) {
		Map<String, Deck> permissionDecks = new HashMap<>();
		Set<Entry<String, List<Card>>> cardsMapEntrySet = cardsMap.entrySet();
		for(Entry<String, List<Card>> decks : cardsMapEntrySet) {
			permissionDecks.put(decks.getKey(), new PermissionDeck(decks.getValue()));
		}
		return permissionDecks;
	}
	
	public Map<String, Deck> makeDecks() {
		List<Card> permissionCards;
		Map<String, List<Card>> cardsMap = new HashMap<>();
		BonusCache.loadCache();
		String[] fields = rawPermissionCards.remove(rawPermissionCards.size() - 1);
		for(String[] rawPermissionCard : rawPermissionCards) {
			String[] rawBonuses = subString(rawPermissionCard);
			BonusSlot permissionCard = addCitiesToPermissionCard(rawPermissionCard);
			String regionName = rawPermissionCard[BONUSES_NUMBER];
			if(!cardsMap.containsKey(regionName)) {
				permissionCards = new ArrayList<>();
				cardsMap.put(regionName, permissionCards);
			}
			cardsMap.get(regionName).add((Card) new BonusesFactory().makeBonuses(fields, rawBonuses, permissionCard));
		}
		return toDecks(cardsMap);
	}

}
