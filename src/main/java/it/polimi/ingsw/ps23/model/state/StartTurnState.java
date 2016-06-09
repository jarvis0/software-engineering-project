package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public class StartTurnState implements State {

	private Player currentPlayer;
	private TurnHandler turnHandler;
	
	public StartTurnState(TurnHandler turnHandler) {
		this.turnHandler = turnHandler;
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		currentPlayer = game.getCurrentPlayer();
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

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
