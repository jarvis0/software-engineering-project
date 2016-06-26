package it.polimi.ingsw.ps23.server.model;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.initialization.Initialization;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameMap;
import it.polimi.ingsw.ps23.server.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.server.model.map.board.King;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.market.Market;
import it.polimi.ingsw.ps23.server.model.player.KingTilesSet;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
import it.polimi.ingsw.ps23.server.model.state.StateCache;

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
	private KingTilesSet kingTiles;
	private NobilityTrack nobilityTrack;
	private PlayersSet playersSet;
	private Player currentPlayer;
	private Market currentMarket;
	private StateCache stateCache;

	public Game(List<String> playersName) {
		Initialization init = new Initialization(playersName);
		mapType = init.getChosenMap();
		politicDeck = init.getPoliticDeck();
		freeCouncillors = init.getFreeCouncillors();
		gameMap = init.getGameMap();
		king = init.getKing();
		kingTiles = init.getKingTiles();
		nobilityTrack = init.getNobilityTrack();
		playersSet = init.getPlayersSet();
		stateCache = new StateCache();
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
	 
	public KingTilesSet getKingTilesSet() {
		return kingTiles;
	}

	public NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}

	public StateCache getStateCache() {
		return stateCache;
	}
	
	public void createNewMarket() {
		currentMarket = new Market(playersSet);
	}
	
	public int getPlayersNumber() {
		return playersSet.playersNumber();
	}
	
	public int getMarketPlayersNumber() {
		return playersSet.marketPlayersNumber();
	}
	
	public Market getMarket() {
		return currentMarket;
	}
	
}
