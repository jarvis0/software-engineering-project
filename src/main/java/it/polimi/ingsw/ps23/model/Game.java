package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.NobilityTrack;
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
	private GamePlayers gamePlayers;

	
	public Game(List<String> playersID) throws NoCapitalException {
		Initialization init = new Initialization();
		politicDeck = init.loadPoliticDeck();
		freeCouncillors = init.loadCouncillors();
		gameMap = init.loadMap(freeCouncillors);
		king = init.createKing(gameMap.getCitiesList(), freeCouncillors);
		kingTiles = init.loadKingTiles();
		nobilityTrack = init.loadNobilityTrack();
		gamePlayers = init.loadPlayer(playersID, politicDeck);
		System.out.println(freeCouncillors);
	}
	
}
