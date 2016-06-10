package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.PlayersSet;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.GameMap;
import it.polimi.ingsw.ps23.model.map.NobilityTrack;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public class GameStatusState implements State {

	private GameMap gameMap;
	private PlayersSet gamePlayersSet;
	private Council kingCouncil;
	private NobilityTrack nobilityTrack;
	private boolean finalTurn;
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		this.gameMap = game.getGameMap();
		this.gamePlayersSet = game.getGamePlayersSet();
		this.kingCouncil = game.getKing().getCouncil();
		this.nobilityTrack = game.getNobilityTrack();
		finalTurn = false;
		for (Player player : gamePlayersSet.getPlayers()) {
			if (player.hasFinished()) {
				finalTurn = true;
				return;
			}
		}
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
		print += "> KING COUNCIL: " + kingCouncil + "\n> NOBILITY TRACK: " + nobilityTrack;
		print += "\n\n\n\t\t\t\t\t++++++++PLAYERS++++++++\n\n";
		for(Player gamePlayer : gamePlayersSet.getPlayers()) {
			print += "> " + gamePlayer + "\n";
		}
		print += "\n\n===============================================================================================================\n";
		print += "===============================================================================================================\n\n";
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
