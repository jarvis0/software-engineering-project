package it.polimi.ingsw.ps23;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.Model;
import it.polimi.ingsw.ps23.server.model.NoCapitalException;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.City;

public class EndGameTest {

	@Test
	public void test() throws NoCapitalException {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		players.add("Player 2");
		players.add("Player 3");
		Model model = new Model();
		Game game = new Game(players);
		TurnHandler turnHandler = new TurnHandler();
		Player winner = game.getGamePlayersSet().getPlayer(0);
		
		//for(City city : game.getGameMap().ge)
	}

}
