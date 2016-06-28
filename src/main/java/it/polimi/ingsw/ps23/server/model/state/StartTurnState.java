package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.GameMap;
import it.polimi.ingsw.ps23.server.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.server.model.map.board.King;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class StartTurnState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6709781155533826821L;
	private Player currentPlayer;
	private TurnHandler turnHandler;
	private GameMap gameMap;
	private PlayersSet gamePlayersSet;
	private Council kingCouncil;
	private NobilityTrack nobilityTrack;
	private King king;
	private Bonus currentKingTile;
	private FreeCouncillorsSet freeCouncillors;
	
	private StateCache stateCache;
	private boolean finalTurn;
	
	public StartTurnState(TurnHandler turnHandler) {
		this.turnHandler = turnHandler;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public String getAvaiableAction() {
		String avaiableAction = new String();
		if(turnHandler.isAvailableMainAction()) {
			avaiableAction += "\n--Main Action--\nElect Councillor\nAcquire Business Permit Tile\nBuild Emporium Permit Tile\nBuild Emporium King";
		}
		if(turnHandler.isAvailableQuickAction()) {
			avaiableAction += "\n--Quick Action--\nEngage Assistant\nChange Permit Tile\nAssistant To Elect Councillor\nAdditional Main Action";
		}
		return avaiableAction;
	}

	public GameMap getGameMap() {
		return gameMap;
	}
	
	public PlayersSet getPlayersSet() {
		return gamePlayersSet;
	}

	public NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}
	
	public King getKing() {
		return king;
	}	
	
	public Bonus getCurrentKingTile() {
		return currentKingTile;		
	}
	
	public FreeCouncillorsSet getFreeCouncillors() {
		return freeCouncillors;
	}

	public StateCache getStateCache() {
		return stateCache;
	}
	
	public String getStatus() {
		String print = "\n===============================================================================================================\n";
		print += "===============================================================================================================\n\n";
		print += "\t\t\t\t\t+++++++++++++++++++++\n";
		print += "\t\t\t\t\t+                   +\n";
		print += "\t\t\t\t\t+    GAME STATUS    +\n";
		print += "\t\t\t\t\t+                   +\n";
		print += "\t\t\t\t\t+++++++++++++++++++++\n\n\n";
		print += gameMap;
		print += "\n\n\t\t\t\t\t+++++++GAME BOARD+++++++\n\n";
		print += "> KING COUNCIL: " + kingCouncil + "\n> CURRENT KING BONUS TILE: " + currentKingTile + "\n> CITY COLORED BONUS TILE:" + gameMap.printColoredBonusTile() + "\n> NOBILITY TRACK: " + nobilityTrack;
		print += "\n\n\n\t\t\t\t\t++++++++PLAYERS++++++++\n\n";
		StringBuilder loopPrint = new StringBuilder();
		for(Player gamePlayer : gamePlayersSet.getPlayers()) {
			loopPrint.append("> " + gamePlayer + "\n");
		}
		print += loopPrint;
		print += "\n\n===============================================================================================================\n";
		print += "===============================================================================================================\n";
		if(finalTurn) {
			print += "This is the final round.";
		}
		return print;
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		currentPlayer = game.getCurrentPlayer();
		gameMap = game.getGameMap();
		gamePlayersSet = game.getGamePlayersSet();
		kingCouncil = game.getKing().getCouncil();
		nobilityTrack = game.getNobilityTrack();
		king = game.getKing();
		currentKingTile = game.getKingTilesSet().getCurrentTile();
		stateCache = game.getStateCache();
		freeCouncillors = game.getFreeCouncillors();
		finalTurn = false;
		for(Player player : gamePlayersSet.getPlayers()) {
			if(player.hasFinished()) {
				finalTurn = true;
				return;
			}
		}
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
