package it.polimi.ingsw.ps23.server.model.map.regions;

import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.Region;

public class GroupRegionalCity extends Region {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -937978927421641632L;

	private static final int CARDS_NUMBER_UP = 2;
	
	private Council council;
	private Deck permissionDeckDown;
	private Deck permissionDeckUp;
	private Map<String, List<String>> citiesConnections;
	
	public GroupRegionalCity(String name, Bonus bonus, Map<String, List<String>> citiesConnections) {
		super(name, bonus);
		this.citiesConnections = citiesConnections;
	}
	
	public void setCouncil(Council council) {
		this.council = council;
	}
	
	public void setPermissionDeck(Deck permissionDeck) {
		this.permissionDeckDown = permissionDeck;
		permissionDeckUp = new Deck(permissionDeckDown.pickCards(CARDS_NUMBER_UP));
	}
	
	public Deck getPermissionDeckUp() {
		return permissionDeckUp;
	}
	
	public Council getCouncil() {
		return council;
	}
	
	public Card pickPermissionCard(int index) {
		Card card = permissionDeckUp.getDeck().get(index);
		permissionDeckUp.getDeck().set(index, permissionDeckDown.pickCard());
		return card;
	}

	public void changePermitTiles() {
		int i = 0;
		for(Card card : permissionDeckUp.getDeck()) {
			permissionDeckDown.getDeck().add(card);
			permissionDeckUp.getDeck().set(i, permissionDeckDown.pickCard());
			i++;
		}
	}

	@Override
	public String toString() {
		String print = "> " + getName() + ":\n";
		print += "\t- CITIES:\n";
		StringBuilder loopPrint = new StringBuilder();
		for(City city : getCitiesList()) {
			loopPrint.append("\t\t» " + city + " / connections with: " + citiesConnections.get(city.getName()) + "\n");
		}
		print += loopPrint;
		print += "\t- REGIONAL BONUS TILE: " + getBonusTile();
		if(alreadyUsedBonusTile()) {
			print += " (Already acquired)";
		}
		print += "\n\t- REGIONAL COUNCIL: " + council + "\n\t" + "- PERMISSION DECKS UP:";
		loopPrint = new StringBuilder();
		for(int i = 0; i < permissionDeckUp.getDeck().size(); i++) {
			loopPrint.append("\n\t\t» " + permissionDeckUp.getDeck().get(i).toString());
		}
		return print + loopPrint;
	}

}
