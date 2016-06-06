package it.polimi.ingsw.ps23.model;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.NobilityTrack;
import it.polimi.ingsw.ps23.model.map.PermissionCard;
import it.polimi.ingsw.ps23.model.map.Region;

public class Player {

	private String name;
	private int coins; //esiste un limite massimo? nel gioco è 20
	private int assistants;
	private BuiltEmporiumSet builtEmporiumSet;
	private int victoryPoints;
	private int nobilityTrackPoints;
	private HandDeck permissionHandDeck;
	private HandDeck politicHandDeck;
	private HandDeck permissionUsedHandDeck;

	public Player(String name, int coins, int assistants, HandDeck politicHandDeck) {
		this.name = name;
		this.coins = coins;
		this.assistants = assistants;
		this.politicHandDeck = politicHandDeck;
		victoryPoints = 0;
		nobilityTrackPoints = 0;
		builtEmporiumSet = new BuiltEmporiumSet();
		permissionHandDeck = new PermissionHandDeck();
		permissionUsedHandDeck = new PermissionHandDeck();
		
	}

	public void pickCard(Deck politicDeck, int cardsNumber) {
		((PoliticHandDeck) politicHandDeck).addCards(politicDeck.pickCards(cardsNumber));
	}
	
	public void pickPermitCard(Region region, int index, TurnHandler turnHandler) {
		Card permissionCard = ((GroupRegionalCity)region).pickPermissionCard(index);
		((PermissionCard)permissionCard).useBonus(this, turnHandler);
		((PermissionHandDeck) permissionHandDeck).addCard(permissionCard);
	}
	
	public void updateVictoryPoints(int value) {
		victoryPoints += value;
	}
	
	public void updateNobilityPoints(int value) {
		nobilityTrackPoints += value;
	}
	
	public void updateCoins(int value) throws InsufficientResourcesException {
		if(coins + value >= 0) {
			coins += value;
		}
		else{
			throw new InsufficientResourcesException();
		}
	}
	
	public void updateAssistants(int value) throws InsufficientResourcesException {
		if(assistants + value >= 0){
			assistants += value;
		}
		else{
			throw new InsufficientResourcesException();
		}
	}
	
	@Override
	public String toString() {
		return 	name + " coins: " + coins + " assistants: " + assistants + " victoryPoints: " + victoryPoints + " permissionHandDeck: " + permissionHandDeck.toString() + " Built Emporiums: " + builtEmporiumSet.toString();	
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

	public HandDeck getPermissionHandDeck() {
		return permissionHandDeck;
	}

	public void updateEmporiumSet(City city, CitiesGraph citiesGraph ) {
		try {
			builtEmporiumSet.addBuiltEmporium(city);
			citiesGraph.getBonuses(this, city);	
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	public BuiltEmporiumSet getEmporiums(){
		return builtEmporiumSet;
	}
	
	public int getAssistants() {
		return assistants;
	}
	
	public int getCoins() {
		return coins;
	}

	public void usePermissionCard(int chosenCard) {
		permissionUsedHandDeck.addCard(permissionHandDeck.getAndRemove(chosenCard));	
	}
	
	public void soldPoliticCards(List<Card> cards) {
		for (Card card : cards) {
			politicHandDeck.removeCard(card);
		}		
	}
	
	public void soldPermissionCards(List<Card> cards) {
		for (Card card : cards) {
			permissionHandDeck.removeCard(card);
		}
	}
	
	public void buyPoliticCards(List<Card> cards) {
		for (Card card : cards) {
			politicHandDeck.addCard(card);
		}		
	}
	
	public void buyPermissionCards(List<Card> cards) {
		for (Card card : cards) {
			permissionHandDeck.addCard(card);
		}
	}
}
