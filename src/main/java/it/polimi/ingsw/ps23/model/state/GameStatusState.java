package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.GamePlayersSet;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.GameMap;
import it.polimi.ingsw.ps23.model.map.NobilityTrack;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public class GameStatusState implements State {

	private GameMap gameMap;
	private GamePlayersSet gamePlayersSet;
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
		print += "\n\n\n\t\t\t\t\t++++++++GAME BOARD++++++++\n\n";
		print += "KING COUNCIL: " + kingCouncil + "\n\n\n>NOBILITY TRACK: " + nobilityTrack + "\n\n\nPLAYERS: " + gamePlayersSet + "\n\n\n";
		if(finalTurn) {
			print += "\nThis is the final round.";
		}
		return print;
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
