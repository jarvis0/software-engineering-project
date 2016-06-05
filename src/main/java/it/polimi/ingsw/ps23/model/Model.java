package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.commons.modelview.ModelObservable;
import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.state.Context;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;
import it.polimi.ingsw.ps23.model.state.State;

public class Model extends ModelObservable implements Cloneable {
	
	private List<String> playersName;
	private int currentPlayerIndex;
	private Game game;
	private Context context;
	private TurnHandler turnHandler;

	public Game getGame() {
		return game;
	}
	
	private void newGame() {
		try {
			game = new Game(playersName);
		} catch (NoCapitalException e) {
			e.printStackTrace();
		}
		changePlayer();
	}

	public void setUpModel(List<String> playersName) {
		this.playersName = playersName;
		currentPlayerIndex = 0;
		newGame();
		setGameStatusState();
	}
	
	private Player setCurrentPlayer() {
		Player currentPlayer = game.getGamePlayersSet().getPlayer(currentPlayerIndex);
		currentPlayer.pickCard(game.getPoliticDeck(), 1);
		currentPlayerIndex = (currentPlayerIndex + 1) % playersName.size();
		return currentPlayer;
	}
	
	public void setPlayerTurn() {
		if(!(turnHandler.isAvailableMainAction() || (turnHandler.isAvailableQuickAction() && !(context.getState() instanceof StartTurnState)))) {
			changePlayer();
		}
		context = new Context();
		StartTurnState startTurnState = new StartTurnState(turnHandler);		
		startTurnState.changeState(context, game);
		//clonare startTurnState
		wakeUp(startTurnState);
	}
	
	public void setActionState(State state) {
		context = new Context();
		state.changeState(context, game);
		wakeUp(state);
	}
	
	public void doAction(Action action) {
		action.doAction(game, turnHandler);
		setGameStatusState();
	}
	
	private void setGameStatusState() {
		context = new Context();
		GameStatusState gameStatusState = new GameStatusState();
		gameStatusState.changeState(context, game);
		wakeUp(gameStatusState);
	}
	
	private void changePlayer() {
		turnHandler = new TurnHandler();
		game.setCurrentPlayer(setCurrentPlayer());
	}
}