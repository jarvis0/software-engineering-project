package it.polimi.ingsw.ps23.server.model;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.initialization.Initialization;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameMap;
import it.polimi.ingsw.ps23.server.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.server.model.map.board.King;
import it.polimi.ingsw.ps23.server.model.map.board.KingRewardTilesSet;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.market.Market;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
import it.polimi.ingsw.ps23.server.model.state.StateCache;

/**
 * This class provides all game components and their relative
 * getters.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class Game implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9200411232887252524L;
	private String mapType;
	private Deck politicDeck;
	private FreeCouncillorsSet freeCouncillors;
	private GameMap gameMap;
	private King king;
	private KingRewardTilesSet kingTiles;
	private NobilityTrack nobilityTrack;
	private PlayersSet playersSet;
	private Player currentPlayer;
	private Market currentMarket;
	private StateCache stateCache;
	private boolean lastEmporiumBuilt;
	private String lastActionPerformed;

	/**
	 * Create a new game initialization object taking game player names
	 * and then stores all references to game resources in class attributes.
	 * <p>
	 * Useful to split game initialization part (with raw configuration files) from
	 * an higher level game representation of resources.
	 * @param playerNames - to be part of the new creating game
	 */
	public Game(List<String> playerNames) {
		Initialization init = new Initialization(playerNames);
		mapType = init.getChosenMap();
		politicDeck = init.getPoliticDeck();
		freeCouncillors = init.getFreeCouncillors();
		gameMap = init.getGameMap();
		king = init.getKing();
		kingTiles = init.getKingTiles();
		nobilityTrack = init.getNobilityTrack();
		playersSet = init.getPlayersSet();
		stateCache = new StateCache();
		lastEmporiumBuilt = false;
		lastActionPerformed = new String();
	}
	
	public String getMapType() {
		return mapType;
	}
	
	public GameMap getGameMap() {
		return gameMap;
	}
	
	public PlayersSet getGamePlayersSet() {
		return playersSet;
	}

	public Deck getPoliticDeck() {
		return politicDeck;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public FreeCouncillorsSet getFreeCouncillors() {
		return freeCouncillors;
	}
	
	public King getKing() {
		return king;
	}
	 
	public KingRewardTilesSet getKingTilesSet() {
		return kingTiles;
	}

	public NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}

	public StateCache getStateCache() {
		return stateCache;
	}
	/**
	 * Create a new instance of market and save the references.
	 */
	public void createNewMarket() {
		currentMarket = new Market(playersSet);
	}
	
	/**
	 * @return the original number of player for this game.
	 */
	public int getPlayersNumber() {
		return playersSet.playersNumber();
	}
	
	/**
	 * @return the number of players participating to the market phase.
	 */
	public int getMarketPlayersNumber() {
		return playersSet.marketPlayersNumber();
	}
	
	public Market getMarket() {
		return currentMarket;
	}
	/**
	 * Set the condition to take check if a player can take the bonus of last emporium built
	 */
	public void lastEmporiumBuilt() {
		lastEmporiumBuilt = true;
	}
	/**
	 * Calculate if the current {@link Player} can take the bonus for last emporium build
	 * @return true if can, false if can't
	 */
	public boolean canTakeBonusLastEmporium() {
		return !lastEmporiumBuilt;
	}
	
	public void setLastActionPerformed(Action action) {
		lastActionPerformed = action.toString();
	}
	
	public String getLastActionPerformed() {
		return lastActionPerformed;
	}
	/**
	 * refresh the status of the last action performed to the initial state
	 */
	public void refreshLastActionPerformed() {
		lastActionPerformed = new String();
	}
	
}
