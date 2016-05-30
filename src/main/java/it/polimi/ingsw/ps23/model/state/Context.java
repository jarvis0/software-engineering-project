package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;

public class Context {
	
	private State state;
	private Player currentPlayer;
	private Game game;
	
	public Context(Game game) {
		state = null;
		this.game = game;
	}
	
	public void setCurrentPlayer(int index) {
		currentPlayer = game.getGamePlayersSet().getPlayer(index);
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
}
