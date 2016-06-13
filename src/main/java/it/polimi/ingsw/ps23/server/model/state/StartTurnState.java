package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.GameMap;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class StartTurnState implements State {

	private Player currentPlayer;
	private TurnHandler turnHandler;
	private GameMap gameMap;
	private PlayersSet gamePlayersSet;
	private Council kingCouncil;
	private NobilityTrack nobilityTrack;
	private boolean finalTurn;
	
	public StartTurnState(TurnHandler turnHandler) {
		this.turnHandler = turnHandler;
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		currentPlayer = game.getCurrentPlayer();
		this.gameMap = game.getGameMap();
		this.gamePlayersSet = game.getGamePlayersSet();
		this.kingCouncil = game.getKing().getCouncil();
		this.nobilityTrack = game.getNobilityTrack();
		finalTurn = false;
		for(Player player : gamePlayersSet.getPlayers()) {
			if(player.hasFinished()) {
				finalTurn = true;
				return;
			}
		}
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
		print += "> KING COUNCIL: " + kingCouncil + "\n> CITY COLORED BONUS TILE:" + gameMap.getColoredBonusTileString() + "\n> NOBILITY TRACK: " + nobilityTrack;
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
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
