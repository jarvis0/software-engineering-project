package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.model.map.NobilityTrack;
import it.polimi.ingsw.ps23.model.market.Market;
import it.polimi.ingsw.ps23.model.state.StateCache;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;
import it.polimi.ingsw.ps23.model.map.GameMap;
import it.polimi.ingsw.ps23.model.map.King;
import it.polimi.ingsw.ps23.model.map.KingTiles;
import it.polimi.ingsw.ps23.model.Initialization;

public class Game {

	private Deck politicDeck;
	private FreeCouncillors freeCouncillors;
	private GameMap gameMap;
	private King king;
	private KingTiles kingTiles;
	private NobilityTrack nobilityTrack;
	private GamePlayersSet gamePlayersSet;
	private Player currentPlayer;
	private Market currentMarket;

	public Game(List<String> playersName) throws NoCapitalException {
		Initialization init = new Initialization(playersName);
		politicDeck = init.getPoliticDeck();
		freeCouncillors = init.getFreeCouncillors();
		gameMap = init.getGameMap();
		king = init.getKing();
		kingTiles = init.getKingTiles();
		nobilityTrack = init.getNobilityTrack();
		gamePlayersSet = init.getGamePlayerSet();
		StateCache.loadCache();
	}
	
	public GameMap getGameMap() {
		return gameMap;
	}
	
	public GamePlayersSet getGamePlayersSet() {
		return gamePlayersSet;
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
	
	public FreeCouncillors getFreeCouncillors() {
		return freeCouncillors;
	}
	
	public King getKing() {
		return king;
	}

	public NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}

	public void createNewMarket() {
		currentMarket = new Market(gamePlayersSet);
	}
	
	public int getNumberOfPlayer() {
		return gamePlayersSet.numberOfPlayer();
	}
	
	public Market getMarket() {
		return currentMarket;
	}
}
