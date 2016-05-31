package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.commons.modelview.ModelObservable;
import it.polimi.ingsw.ps23.model.state.Context;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;

public class Model extends ModelObservable implements Cloneable {
	
	private List<String> playersName;
	private int currentPlayerIndex;
	private Game game;
	private Context context;

	public Game getGame() {
		return game;
	}
	
	private void newGame() {
		try {
			game = new Game(playersName);
		} catch (NoCapitalException e) {
			e.printStackTrace();
		}
	}

	public void setUpModel(List<String> playersName) {
		this.playersName = playersName;
		currentPlayerIndex = 0;
		newGame();
		context = new Context();
		GameStatusState gameStatusState = new GameStatusState();
		gameStatusState.changeState(context, game);
		setState(context);
	}
	
	private Player setCurrentPlayer() {
		Player currentPlayer = game.getGamePlayersSet().getPlayer(currentPlayerIndex);
		currentPlayer.pickCard(game.getPoliticDeck(), 1);
		if(currentPlayerIndex < playersName.size() - 1) 
			currentPlayerIndex++;
		else
			currentPlayerIndex = 0;
		return currentPlayer;
	}
	
	public void setPlayerTurn() {
		//nel visitor: setState(setCurrentPlayer());
		context = new Context();
		StartTurnState startTurnState = new StartTurnState();
		game.setCurrentPlayer(setCurrentPlayer());
		startTurnState.changeState(context, game);
		setState(context);
	}
	
}