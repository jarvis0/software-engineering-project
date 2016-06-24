package it.polimi.ingsw.ps23.server.model.state;

import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.WinnerComparator;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class EndGameState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8226148165092467541L;
	private List<Player> players;

	public String getWinner() {
		Collections.sort(players, new WinnerComparator());
		return "The winner is: " + players.get(0) + "\nClassification: " + players.toString();
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		players = game.getGamePlayersSet().getPlayers();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
}
