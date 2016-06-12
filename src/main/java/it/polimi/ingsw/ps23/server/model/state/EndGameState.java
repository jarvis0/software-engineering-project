package it.polimi.ingsw.ps23.server.model.state;

import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.WinnerComparator;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class EndGameState implements State {

	private List<Player> players;
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		players = game.getGamePlayersSet().getPlayers();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
	public String getWinner() {
		Collections.sort(players, new WinnerComparator());
		return "The winner is: " + players.get(0) + "\nClassification: " + players.toString();
	}

}
