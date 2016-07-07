package it.polimi.ingsw.ps23.server.model.state;

import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.GameMap;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
/**
 * Provides methods to show the status of the map in the current game.
 * @author Mirco Manzoni & Alessandro Erba
 *
 */
public abstract class MapUpdateState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6491577176320941015L;
	private Player currentPlayer;
	private PlayersSet gamePlayersSet;
	private GameMap gameMap;
	private Council kingCouncil;
	private NobilityTrack nobilityTrack;
	private String kingPosition;
	private Bonus currentKingTile;
	private FreeCouncillorsSet freeCouncillors;
	private boolean finalTurn;
	private String lastActionPerformed;
	
	public String getKingPosition() {
		return kingPosition;
	}
	
	public List<Councillor> getFreeCouncillors() {
		return freeCouncillors.getFreeCouncillorsList();
	}
	
	public List<Region> getGroupRegionalCity() {
		return gameMap.getGroupRegionalCity();
	}
	
	public GameMap getGameMap() {
		return gameMap;
	}
	
	public List<Region> getGroupColoredCity() {
		return gameMap.getGroupColoredCity();
	}
	
	public Bonus getCurrentKingTile() {
		return currentKingTile;		
	}
	
	public List<Player> getPlayersList() {
		return gamePlayersSet.getPlayers();
	}
	
	public NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Council getKingCouncil() {
		return kingCouncil;
	}
	
	public String getLastActionPerformed() {
		return lastActionPerformed;
	}
	
	void setParameters(Game game) {
		currentPlayer = game.getCurrentPlayer();
		gameMap = game.getGameMap();
		gamePlayersSet = game.getGamePlayersSet();
		kingCouncil = game.getKing().getCouncil();
		nobilityTrack = game.getNobilityTrack();
		kingPosition = game.getKing().getPosition().getName();
		currentKingTile = game.getKingTilesSet().getCurrentTile();
		freeCouncillors = game.getFreeCouncillors();
		finalTurn = false;
		for(Player player : gamePlayersSet.getPlayers()) {
			if(player.hasFinished()) {
				finalTurn = true;
			}
		}
		lastActionPerformed = game.getLastActionPerformed();
	}
	
	/**
	 * This method provides the print of the game status.
	 * @return the game status string
	 */
	
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
		print += "> KING'S POSITION: " + kingPosition;
		print += "\n> KING COUNCIL: " + kingCouncil + "\n> CURRENT KING BONUS TILE: " + currentKingTile + "\n> CITY COLORED BONUS TILES:" + gameMap.printColoredBonusTile() + "\n> NOBILITY TRACK: " + nobilityTrack;
		print += "\n\n\n\t\t\t\t\t++++++++PLAYERS++++++++\n\n";
		StringBuilder loopPrint = new StringBuilder();
		for(Player gamePlayer : gamePlayersSet.getPlayers()) {
			loopPrint.append("> " + gamePlayer + "\n");
		}
		print += loopPrint;
		print += "\n\n===============================================================================================================\n";
		print += "===============================================================================================================\n";
		if(finalTurn) {
			print += "This is the final round.\n";
		}
		print += lastActionPerformed + "\n";
		return print;
	}
}
