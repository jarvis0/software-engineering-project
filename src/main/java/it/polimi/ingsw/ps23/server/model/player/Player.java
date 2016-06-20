package it.polimi.ingsw.ps23.server.model.player;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.PermissionCard;

public class Player implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 663214081725066833L;
	private String name;
	private int coins;
	private int assistants;
	private BuiltEmporiumsSet builtEmporiumsSet;
	private int victoryPoints;
	private int nobilityTrackPoints;
	private HandDeck permissionHandDeck;
	private HandDeck politicHandDeck;
	private HandDeck permissionUsedHandDeck;
	private BonusTile bonusTile; //TODO maybe we have to print also this
	private boolean online;
	
	public Player(String name, int coins, int assistants, HandDeck politicHandDeck) {
		this.name = name;
		this.coins = coins;
		this.assistants = assistants;
		this.politicHandDeck = politicHandDeck;
		victoryPoints = 0;
		nobilityTrackPoints = 0;
		builtEmporiumsSet = new BuiltEmporiumsSet();
		permissionHandDeck = new PermissionHandDeck();
		permissionUsedHandDeck = new PermissionHandDeck();
		bonusTile = new BonusTile();
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
	
	public void updateCoins(int value) {
		coins += value;
	}
	
	public void updateAssistants(int value) {
		assistants += value;
	}
	
	public void updateSuperBonus(Bonus bonus, List<String> inputs, Game game, TurnHandler turnHandler) {
		((SuperBonus) bonus).acquireSuperBonus(inputs, game, turnHandler);
	}

	public String showSecretStatus() {
		return politicHandDeck.toString();
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
		builtEmporiumsSet.addBuiltEmporium(city);
		game.getGameMap().getCitiesGraph().getBonuses(game, turnHandler, city);	
	}

	public void usePermissionCard(int chosenCard) {
		permissionUsedHandDeck.addCard(permissionHandDeck.getAndRemove(chosenCard));	
	}
	
	public void soldPoliticCards(List<Card> cards) {
		for(Card card : cards) {
			politicHandDeck.removeCard(card);
		}		
	}
	
	public void soldPermissionCards(List<Card> cards) {
		for(Card card : cards) {
			permissionHandDeck.removeCard(card);
		}
	}
	
	public void buyPoliticCards(List<Card> cards) {
		for(Card card : cards) {
			politicHandDeck.addCard(card);
		}		
	}
	
	public void buyPermissionCards(List<Card> cards) {
		for(Card card : cards) {
			permissionHandDeck.addCard(card);
		}
	}

	HandDeck getPermissionUsedHandDeck() {
		return permissionUsedHandDeck;
	}
	
	public BuiltEmporiumsSet getEmporiumForRecycleRewardToken() {
		return builtEmporiumsSet.getCitiesForRecycleRewardTokens();
	}

	public boolean hasFinished() {
		return builtEmporiumsSet.containsMaxEmporium();
	}
	
	void addBonusTile(Bonus bonus) {
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
		Region completedRegion = game.getGameMap().groupRegionalCitiesComplete(builtEmporiumsSet);
		if(completedRegion != null) {
			bonusTile.addTile(completedRegion.acquireBonusTile());
			if(!(game.getKingTileSet().isEmpty())) {
				bonusTile.addTile(game.getKingTileSet().pop());
			}
		}
		completedRegion = game.getGameMap().groupColoredCitiesComplete(builtEmporiumsSet);
		if(completedRegion != null) {
			bonusTile.addTile(completedRegion.acquireBonusTile());
			if(!(game.getKingTileSet().isEmpty())) {
				bonusTile.addTile(game.getKingTileSet().pop());
			}
		}
	}

	@Override
	public String toString() {
		String print = 	name + " coins: " + coins + " assistants: " + assistants + " victoryPoints: " + victoryPoints + " permissionHandDeck: " + permissionHandDeck.toString() + " Built Emporiums: " + builtEmporiumsSet.getCities();	
		if(isOnline()) {
			print += " online";
		}
		else {
			print += " offline";
		}
		return print;
	}

}