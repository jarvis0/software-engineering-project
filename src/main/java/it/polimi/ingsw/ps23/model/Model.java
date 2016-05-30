package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.commons.modelview.ModelObservable;
import it.polimi.ingsw.ps23.model.state.Context;
import it.polimi.ingsw.ps23.model.state.StartRoundState;

public class Model extends ModelObservable implements Cloneable {
	
	private List<String> playersName;
	private Game game;
	private Context context;

	public Game getGame() {
		return game;
	}
	
	private Game newGame() {
		try {
			game = new Game(playersName);
		} catch (NoCapitalException e) {
			e.printStackTrace();
		}
		return game;
	}
	
	public void setModel(List<String> playersName) {
		this.playersName = playersName;
		game = newGame();
		setState(game);
	}
	
	public void newRound() {
		context = new Context(game);
		StartRoundState startRoundState = new StartRoundState();
		startRoundState.changeState(context);
	}
	
}