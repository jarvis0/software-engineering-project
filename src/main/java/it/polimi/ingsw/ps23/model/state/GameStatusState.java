package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.GamePlayersSet;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.GameMap;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class GameStatusState implements State {

	private GameMap gameMap;
	private GamePlayersSet gamePlayersSet;
	private Council kingCouncil;
	private boolean finalTurn;
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		this.gameMap = game.getGameMap();
		this.gamePlayersSet = game.getGamePlayersSet();
		this.kingCouncil = game.getKing().getCouncil();
		finalTurn = false;
		for (Player player : gamePlayersSet.getPlayers()) {
			if (player.hasFinished()) {
				finalTurn = true;
				return;
			}
		}
	}
		
	public String getStatus() {
		String returnString = "Map: " + gameMap + "\nPlayers: " + gamePlayersSet + "\n King Council: " + kingCouncil;
		if(finalTurn) {
			returnString += "\nThis is the final round";
		}
		return returnString;
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
