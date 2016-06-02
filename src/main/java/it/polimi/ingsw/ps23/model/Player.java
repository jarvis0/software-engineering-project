package it.polimi.ingsw.ps23.model;


import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;

public class Player {
	
	private String name;
	private int coin; //esiste un limite massimo? nel gioco è 20
	private int assistant;
	private BuiltEmporiumSet builtEmporiumSet;
	private int victoryPoints;
	private int nobilityTrackPoints;
	private HandDeck permissionHandDeck;
	private HandDeck politicHandDeck;	

	public Player(String name, int coin, int assistant, HandDeck politicHandDeck) {
		this.name = name;
		this.coin = coin;
		this.assistant = assistant;
		this.politicHandDeck = politicHandDeck;
		victoryPoints = 0;
		nobilityTrackPoints = 0;
		builtEmporiumSet = new BuiltEmporiumSet();
		permissionHandDeck = new PermissionHandDeck();
		
	}

	public void pickCard(Deck politicDeck, int cardsNumber) {
		((PoliticHandDeck) politicHandDeck).addCards(politicDeck.pickCards(cardsNumber));
	}
	
	public void updateVictoryPoints(int value) {
		victoryPoints += value;
	}
	
	public void updateNobilityPoints(int value) {
		nobilityTrackPoints += value;
	}
	
	public void updateCoins(int value) throws InsufficientResourcesException {
		if(coin + value >= 0) {
			coin += value;
		}
		else{
			throw new InsufficientResourcesException();
		}
	}
	
	public void updateAssistants(int value) throws InsufficientResourcesException {
		if(assistant + value >= 0){
			assistant += value;
		}
		else{
			throw new InsufficientResourcesException();
		}
	}
	
	@Override
	public String toString() {
		return 	name + " " + coin + " " + assistant + " " + victoryPoints + " " + permissionHandDeck.toString() + "Built Emporiums " +builtEmporiumSet.toString();	
	}

	public String showSecretStatus() {
		return " " + politicHandDeck.toString();
	}
	
	public String getName() {
		return name;
	}
	
	public HandDeck getPoliticHandDeck() {
		return politicHandDeck;
	}

	public void updateEmporiumSet(City city) {
		try {
			builtEmporiumSet.addBuiltEmporium(city);
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}
}
