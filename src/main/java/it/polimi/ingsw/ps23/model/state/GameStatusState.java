package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.GamePlayersSet;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.GameMap;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class GameStatusState implements State {

	private GameMap gameMap;
	private GamePlayersSet gamePlayersSet;
	private Council kingCouncil;
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		this.gameMap = game.getGameMap();
		this.gamePlayersSet = game.getGamePlayersSet();
		this.kingCouncil = game.getKing().getCouncil();
	}
		
	public String getStatus() {
		return "Map: " + gameMap + "\nPlayers: " + gamePlayersSet + "\n King Council: " + kingCouncil;
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
