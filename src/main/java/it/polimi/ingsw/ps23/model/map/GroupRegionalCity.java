package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class GroupRegionalCity extends Region {
	
	private Council council;
	private Deck permissionDeckDown;
	private Deck permissionDeckUp;

	
	public GroupRegionalCity(String name, Bonus bonus) {
		super(name, bonus);
	}
	
	public void setCouncil(Council council) {
		this.council = council;
	}
	
	public void setPermissionDeck(Deck permissionDeck) {
		this.permissionDeckDown = permissionDeck;
	}

	public Deck getPermissionDeck() {
		return permissionDeckDown;
	}
	
	//pick 2 from deckDown -> deckUp
	
	@Override
	public String toString() {
		return super.toString() + "[Council: " + council + "]" + "\n" + "permissionDeckDown: " + permissionDeckDown + "\n";
	}

}
