package it.polimi.ingsw.ps23.server.model.state;

import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.WinnerComparator;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;
/**
 * Provides methods to show the result of the current game 
 * @author Mirco Manzoni
 *
 */
public class EndGameState extends MapUpdateState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8226148165092467541L;
	private List<Player> players;

	/**
	 * Performs a comparison among all players in game in order to
	 * choose the winner of the game according to the Council Of Four game ending.
	 * @return the winner of the game.
	 */
	public String getWinner() {
		Collections.sort(players, new WinnerComparator());
		StringBuilder stringBuilder = new StringBuilder();
		int i = 1;
		for(Player player : players) {
			stringBuilder.append("\n" + i + ": " + player.getName() + ", Victory Points:" + player.getVictoryPoints());
			i++;
		}
		return "\nThe winner is: " + players.get(0).getName() + "\nClassification: " + stringBuilder;
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		setParameters(game);
		players = game.getGamePlayersSet().getPlayers();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
}
