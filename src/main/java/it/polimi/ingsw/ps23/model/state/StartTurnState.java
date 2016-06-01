package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class StartTurnState implements State {

	private Player currentPlayer;
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		currentPlayer = game.getCurrentPlayer();
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
