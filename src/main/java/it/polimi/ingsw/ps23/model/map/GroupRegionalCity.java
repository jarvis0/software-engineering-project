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
		permissionDeckUp = new Deck(permissionDeckDown.pickCards(2));
	}

	public Deck getPermissionDeck() {
		return permissionDeckDown;
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
		int i=0;
		for(Card card : permissionDeckUp.getDeck()) {
			permissionDeckDown.getDeck().add(card);
			permissionDeckUp.getDeck().set(i, permissionDeckDown.pickCard());
			i++;
		}
	}
	
	@Override
	public String toString() {
		String print = super.toString() + "\n\n\t- REGIONAL COUNCIL: " + council + "\n\n\t" + "- PERMISSION DECKS UP: ";
		print += permissionDeckUp.getDeck().get(0).toString() + "\n\t\t\t       " + permissionDeckUp.getDeck().get(1).toString();
		return print;
	}

}
