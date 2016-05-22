package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.PermissionCard;
import it.polimi.ingsw.ps23.model.map.PermissionDeck;

public class PermissionDeckFactory extends DeckFactory {

	@Override
	public Deck makeDeck(List<String[]> rawPermissionCards) {
		ArrayList<Card> permissionCards = new ArrayList<>();
		String[] fields = rawPermissionCards.remove(rawPermissionCards.size() - 1);
		BonusCache.loadCache();
		for(String[] rawPermissionCard : rawPermissionCards) {
			int i = 0;
			PermissionCard permissionCard = new PermissionCard();
			for(String rawPermissionCardField : rawPermissionCard) {
				int bonusValue = Integer.parseInt(rawPermissionCardField);
				if(bonusValue > 0) {
					Bonus bonus = (Bonus) BonusCache.getBonus(fields[i]);
					bonus.setValue(bonusValue);
					permissionCard.addBonus(bonus);
					i++;
				}
			}
			permissionCards.add(permissionCard);
		}
		return new PermissionDeck(permissionCards);
	}
}
