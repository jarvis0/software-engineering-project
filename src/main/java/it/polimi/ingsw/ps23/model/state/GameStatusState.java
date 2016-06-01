package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.GamePlayersSet;
import it.polimi.ingsw.ps23.model.map.GameMap;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class GameStatusState implements State {

	private GameMap gameMap;
	private GamePlayersSet gamePlayersSet;
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		this.gameMap = game.getGameMap();
		this.gamePlayersSet = game.getGamePlayersSet();
	}
	
	public GameMap getGameMap() {
		return gameMap;
	}
	
	public GamePlayersSet getGamePlayersSet() {
		return gamePlayersSet;
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
