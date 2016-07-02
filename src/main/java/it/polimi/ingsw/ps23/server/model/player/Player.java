package it.polimi.ingsw.ps23.server.model.player;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
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
/**
 * Provides all methods to manage a player during the game
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class Player implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 663214081725066833L;
	private static final int MAX_EMPORIUMS_POINTS_REACHED = 3;//TODO rimettere a 10
	
	private String name;
	private int coins;
	private int assistants;
	private int victoryPoints;
	private int nobilityTrackPoints;
	private BuiltEmporiumsSet builtEmporiumsSet;
	private HandDeck permitHandDeck;
	private HandDeck permitUsedHandDeck;
	private BonusTilesSet bonusTiles;
	private HandDeck politicHandDeck;
	private boolean online;
	/**
	 * Construct the player for the current game and initialize all variables.
	 * @param name - the name of the player
	 * @param coins - the initial coins of the player
	 * @param assistants - the initial assistants of the player
	 * @param politicHandDeck - the initial politic card of the player
	 */
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
	/**
	 * Add a number of {@link PoliticCard} to player's {@link PoliticHandDeck}.
	 * @param politicDeck - the politic deck of the game
	 * @param cardsNumber - amount of card that the player have to pick
	 */
	public void pickCard(Deck politicDeck, int cardsNumber) {
		((PoliticHandDeck) politicHandDeck).addCards(politicDeck.pickCards(cardsNumber));
	}
	/**
	 * Add a number of {@link BusinessPermitTile} to player's {@link PermitHandDeck} and use bonuses present on
	 * that card.
	 * @param game - the current game to take bonuses
	 * @param turnHandler - the turn handler to take bonuses
	 * @param region - selected region to pick the card from
	 * @param index - selected permit tile
	 */
	public void pickPermitCard(Game game, TurnHandler turnHandler, Region region, int index) {
		Card permissionCard = ((GroupRegionalCity)region).pickPermitTile(index);
		((BusinessPermitTile)permissionCard).useBonus(game, turnHandler);
		((PermitHandDeck) permitHandDeck).addCard(permissionCard);
	}
	/**
	 * Update value of victory points
	 * @param value - Increase or decrease value of victory points
	 */
	public void updateVictoryPoints(int value) {
		victoryPoints += value;
	}
	/**
	 * Update value of nobility track points
	 * @param value - Increase or decrease value of nobility track points
	 */
	public void updateNobilityPoints(int value) { 
		nobilityTrackPoints += value;
	}
	/**
	 * Update value of coins
	 * @param value - Increase or decrease value of coins
	 */
	public void updateCoins(int value) {
		coins += value;
	}
	/**
	 * Update value of assistants
	 * @param value - Increase or decrease value of assistants
	 */
	public void updateAssistants(int value) {
		assistants += value;
	}
	
	public void updateSuperBonus(Bonus bonus, List<String> inputs, Game game, TurnHandler turnHandler) throws InvalidCardException, InvalidCityException {
		((SuperBonus) bonus).acquireSuperBonus(inputs, game, turnHandler);
	}
	/**
	 * Show the value of {@link PoliticHandDeck} of that player.
	 * @return a string with all the {@link PoliticCard} of the player.
	 */
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
	/**
	 * Adds the selected {@link City} to the list of the emporiums of the player and he takes the {@link Bonus}
	 * of this city and the nearest city where he built. If the player reaches the maximum size of emporiums 
	 * built, he recives {@link VictoryPointBonus}.
	 * @param game - current game for references to cities
	 * @param turnHandler - turnhandler to apply bonuses
	 * @param city - city wheer the player wants to build
	 */
	public void updateEmporiumSet(Game game, TurnHandler turnHandler, City city) {
		builtEmporiumsSet.addBuiltEmporium(city);
		if(game.canTakeBonusLastEmporium() && builtEmporiumsSet.containsMaxEmporium()) {
			updateVictoryPoints(MAX_EMPORIUMS_POINTS_REACHED);
			game.lastEmporiumBuilt();
		}
		game.getGameMap().getCitiesGraph().rewardTokenGiver(game, turnHandler, city);	
	}
	/**
	 * Moves the chosen {@link BusinessPermitTile} from the {@link PermitHandDeck} to the used {@link PermitHandDeck}.
	 * @param chosenCard - the chosen permit tile.
	 */
	public void usePermitCard(int chosenCard) {
		permitUsedHandDeck.addCard(permitHandDeck.getAndRemove(chosenCard));	
	}
	
	/**
	 * Removes all the {@link PoliticCard} from the player.
	 * @param cards - cards to remove
	 */
	public void sellPoliticCards(List<Card> cards) {
		for(Card card : cards) {
			politicHandDeck.removeCard(card);
		}		
	}
	/**
	 * Removes all the {@link BusinessPermitTile} from the player.
	 * @param cards - cards to remove
	 */
	public void sellPermitCards(List<Card> cards) {
		for(Card card : cards) {
			permitHandDeck.removeCard(card);
		}
	}
	/**
	 * Add all the {@link PoliticCard} from the player.
	 * @param cards - cards to add
	 */
	public void buyPoliticCards(List<Card> cards) {
		for(Card card : cards) {
			politicHandDeck.addCard(card);
		}		
	}
	/**
	 * Add all the {@link BusinessPermitTile} from the player.
	 * @param cards - cards to add
	 */
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
	/**
	 * Calculate if a player has built all the emporiums.
	 * @return true if the player has built max emporiums, false if not.
	 */
	public boolean hasFinished() {
		return builtEmporiumsSet.containsMaxEmporium();
	}
	/**
	 * The player takes all the bonus tiles that he has obtained until now.
	 * @param game - the current game to take bonuses
	 * @param turnHandler - the turn handler to take bonuses
	 */
	public void getAllTilesPoints(Game game, TurnHandler turnHandler) {
		bonusTiles.useBonus(game, turnHandler);
	}
	
	public int getNumberOfPermitCards() {
		return permitHandDeck.getHandSize() + permitUsedHandDeck.getHandSize();
	}
	
	public int getNumberOfPoliticCards() {
		return politicHandDeck.getHandSize();
	}
	
	/**
	 * Check if the player can take bonuses to complete a {@link Region} after building an emporium in a {@link City}.
	 * If the player can take these bonuses the method checks if there are some bonuses in {@link KingRewardTilesSet}
	 * available.
	 * @param game - current game
	 */
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
		String print = 	name + " coins: " + coins + " assistants: " + assistants + " victoryPoints: " + victoryPoints + " nobilityTrackPoints: " + nobilityTrackPoints + " permitHandDeck: " + permitHandDeck.toString() + " built emporiums: " + builtEmporiumsSet.getCitiesPrint() + " status:";	
		if(isOnline()) {
			print += " online";
		}
		else {
			print += " offline";
		}
		return print;
	}

}