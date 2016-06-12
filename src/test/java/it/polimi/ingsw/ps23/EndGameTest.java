package it.polimi.ingsw.ps23;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Model;
import it.polimi.ingsw.ps23.model.NoCapitalException;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.City;

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
