package it.polimi.ingsw.ps23.server.model.player;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.BusinessPermitTile;

public class Player implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 663214081725066833L;
	private static final int POINTS_MAX_EMPORIUMS_REACHED = 3;
	private String name;
	private int coins;
	private int assistants;
	private BuiltEmporiumsSet builtEmporiumsSet;
	private int victoryPoints;
	private int nobilityTrackPoints;
	private HandDeck permitHandDeck;
	private HandDeck politicHandDeck;
	private HandDeck permitUsedHandDeck;
	private BonusTilesSet bonusTiles;
	private boolean online;
	
	public Player(String name, int coins, int assistants, HandDeck politicHandDeck) {
		this.name = name;
		this.coins = coins;
		this.assistants = assistants;
		this.politicHandDeck = politicHandDeck;
		victoryPoints = 0;
		nobilityTrackPoints = 0;
		builtEmporiumsSet = new BuiltEmporiumsSet();
		permitHandDeck = new PermitHandDeck();
		permitUsedHandDeck = new PermitHandDeck();
		bonusTiles = new BonusTilesSet();
		online = true;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public BuiltEmporiumsSet getEmporiums(){
		return builtEmporiumsSet;
	}

	public int getAssistants() {
		return assistants;
	}
	
	public int getVictoryPoints() {
		return victoryPoints;
	}

	public int getNobilityTrackPoints() {
		return nobilityTrackPoints;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isOnline() {
		return online;
	}

	public void pickCard(Deck politicDeck, int cardsNumber) {
		((PoliticHandDeck) politicHandDeck).addCards(politicDeck.pickCards(cardsNumber));
	}
	
	public void pickPermitCard(Game game, TurnHandler turnHandler, Region region, int index) {
		Card permissionCard = ((GroupRegionalCity)region).pickPermitTile(index);
		((BusinessPermitTile)permissionCard).useBonus(game, turnHandler);
		((PermitHandDeck) permitHandDeck).addCard(permissionCard);
	}
	
	public void updateVictoryPoints(int value) {
		victoryPoints += value;
	}

	public void updateNobilityPoints(int value) { 
		nobilityTrackPoints += value;
	}
	
	public void updateCoins(int value) {
		coins += value;
	}
	
	public void updateAssistants(int value) {
		assistants += value;
	}
	
	public void updateSuperBonus(Bonus bonus, List<String> inputs, Game game, TurnHandler turnHandler) throws InvalidCardException {
		((SuperBonus) bonus).acquireSuperBonus(inputs, game, turnHandler);
	}

	public String showSecretStatus() {
		return "\npoliticHandDeck: " + politicHandDeck.toString();
	}

	public HandDeck getPoliticHandDeck() {
		return politicHandDeck;
	}

	public HandDeck getPermitHandDeck() {
		return permitHandDeck;
	}
	
	public HandDeck getAllPermitHandDeck() {
		HandDeck permissionTotalHandDeck = new PermitHandDeck();
		permissionTotalHandDeck.getCards().addAll(permitHandDeck.getCards());
		permissionTotalHandDeck.getCards().addAll(permitUsedHandDeck.getCards());
		return permissionTotalHandDeck;
	}

	public void updateEmporiumSet(Game game, TurnHandler turnHandler, City city) {
		builtEmporiumsSet.addBuiltEmporium(city);
		if(game.canTakeBonusLastEmporium()) {
			updateVictoryPoints(POINTS_MAX_EMPORIUMS_REACHED);
			game.lastEmporiumBuilt();
		}
		game.getGameMap().getCitiesGraph().rewardTokenGiver(game, turnHandler, city);	
	}

	public void usePermitCard(int chosenCard) {
		permitUsedHandDeck.addCard(permitHandDeck.getAndRemove(chosenCard));	
	}
	
	public void sellPoliticCards(List<Card> cards) {
		for(Card card : cards) {
			politicHandDeck.removeCard(card);
		}		
	}
	
	public void sellPermissionCards(List<Card> cards) {
		for(Card card : cards) {
			permitHandDeck.removeCard(card);
		}
	}
	
	public void buyPoliticCards(List<Card> cards) {
		for(Card card : cards) {
			politicHandDeck.addCard(card);
		}		
	}
	
	public void buyPermitCards(List<Card> cards) {
		for(Card card : cards) {
			permitHandDeck.addCard(card);
		}
	}

	public HandDeck getPermitUsedHandDeck() {
		return permitUsedHandDeck;
	}
	
	public BuiltEmporiumsSet getEmporiumForRecycleRewardToken() {
		return builtEmporiumsSet.getCitiesForRecycleRewardTokens();
	}

	public boolean hasFinished() {
		return builtEmporiumsSet.containsMaxEmporium();
	}

	public void getAllTilesPoints(Game game, TurnHandler turnHandler) {
		bonusTiles.useBonus(game, turnHandler);
	}
	
	public int getNumberOfPermitCards() {
		return permitHandDeck.getHandSize() + permitUsedHandDeck.getHandSize();
	}
	
	public int getNumberOfPoliticCards() {
		return politicHandDeck.getHandSize();
	}
	

	public void checkEmporiumsGroup(Game game) {
		Region completedRegion = game.getGameMap().groupRegionalCitiesComplete(builtEmporiumsSet);
		if(completedRegion != null) {
			bonusTiles.addTile(completedRegion.acquireBonusTile());
			if(!(game.getKingTilesSet().isEmpty())) {
				bonusTiles.addTile(game.getKingTilesSet().pop());
			}
		}
		completedRegion = game.getGameMap().groupColoredCitiesComplete(builtEmporiumsSet);
		if(completedRegion != null) {
			bonusTiles.addTile(completedRegion.acquireBonusTile());
			if(!(game.getKingTilesSet().isEmpty())) {
				bonusTiles.addTile(game.getKingTilesSet().pop());
			}
		}
	}

	@Override
	public String toString() {
		String print = 	name + " coins: " + coins + " assistants: " + assistants + " victoryPoints: " + victoryPoints + " permissionHandDeck: " + permitHandDeck.toString() + " built emporiums: " + builtEmporiumsSet.getCitiesPrint() + " status:";	
		if(isOnline()) {
			print += " online";
		}
		else {
			print += " offline";
		}
		return print;
	}

}