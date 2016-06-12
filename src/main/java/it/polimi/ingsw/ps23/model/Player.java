package it.polimi.ingsw.ps23.model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.SuperBonus;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.PermissionCard;
import it.polimi.ingsw.ps23.model.map.Region;

public class Player {
	
	private String name;
	private int coins;
	private int assistants;
	private BuiltEmporiumSet builtEmporiumSet;
	private int victoryPoints;
	private int nobilityTrackPoints;
	private HandDeck permissionHandDeck;
	private HandDeck politicHandDeck;
	private HandDeck permissionUsedHandDeck;
	private BonusTile bonusTile;
	
	private Logger logger;
	
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
		bonusTile = new BonusTile();
		logger = Logger.getLogger(this.getClass().getName());
	}

	public void pickCard(Deck politicDeck, int cardsNumber) {
		((PoliticHandDeck) politicHandDeck).addCards(politicDeck.pickCards(cardsNumber));
	}
	
	public void pickPermitCard(Game game, TurnHandler turnHandler, Region region, int index) {
		Card permissionCard = ((GroupRegionalCity)region).pickPermissionCard(index);
		((PermissionCard)permissionCard).useBonus(game, turnHandler);
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
	
	public void updateSuperBonus(Bonus bonus, List<String> inputs, Game game, TurnHandler turnHandler) {
		((SuperBonus) bonus).acquireSuperBonus(inputs, game, turnHandler);
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
	
	public HandDeck getTotalPermissionHandDeck() {
		HandDeck permissionTotalHandDeck = new PermissionHandDeck();
		permissionTotalHandDeck.getCards().addAll(permissionHandDeck.getCards());
		permissionTotalHandDeck.getCards().addAll(permissionUsedHandDeck.getCards());
		return permissionHandDeck;
	}

	public void updateEmporiumSet(Game game, TurnHandler turnHandler, City city) {
		try {
			builtEmporiumSet.addBuiltEmporium(city);
			game.getGameMap().getCitiesGraph().getBonuses(game, turnHandler, city);	
		} catch (InvalidPositionException e) {
			logger.log(Level.SEVERE, "Cannot initialize the server connection socket.", e);	
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
	
	public int getNobilityTrackPoints() {
		return nobilityTrackPoints;
	}
	
	public int getVictoryPoints() {
		return victoryPoints;
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

	public HandDeck getPermissionUsedHandDeck() {
		return permissionUsedHandDeck;
	}
	
	public BuiltEmporiumSet getEmporiumForRecycleRewardToken() {
		return builtEmporiumSet.getCitiesForRecycleRewardTokens();
	}

	public boolean hasFinished() {
		return builtEmporiumSet.containsTenEmporium();
	}
	
	public void addBonusTile(Bonus bonus) {
		bonusTile.addTile(bonus);
	}

	public void getAllTilePoints(Game game, TurnHandler turnHandler) {
		bonusTile.useBonus(game, turnHandler);
	}
	
	public int getNumberOfPermissionCard() {
		return permissionHandDeck.getHandSize() + permissionUsedHandDeck.getHandSize();
	}
	
	public int getNumberOfPoliticCard() {
		return politicHandDeck.getHandSize();
	}

	public void checkEmporiumsGroups(Game game) {
		Region completedRegion = game.getGameMap().groupRegionalCitiesComplete(builtEmporiumSet);
		if(completedRegion != null) {
			bonusTile.addTile(completedRegion.useBonusTile());
			if(!(game.getKingTileSet().isEmpty())) {
				bonusTile.addTile(game.getKingTileSet().pop());
			}
		}
		completedRegion = game.getGameMap().groupColoredCitiesComplete(builtEmporiumSet);
		if(completedRegion != null) {
			bonusTile.addTile(completedRegion.useBonusTile());
			if(!(game.getKingTileSet().isEmpty())) {
				bonusTile.addTile(game.getKingTileSet().pop());
			}
		}
	}

	@Override
	public String toString() {
		return 	name + " coins: " + coins + " assistants: " + assistants + " victoryPoints: " + victoryPoints + " permissionHandDeck: " + permissionHandDeck.toString() + " Built Emporiums: " + builtEmporiumSet.toString();	
	}

}
