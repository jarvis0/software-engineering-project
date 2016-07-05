package it.polimi.ingsw.ps23.server.model.map.board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.RealBonus;

public class TestNobilityTrack {

	@Test
	public void test() {
		List<String> playerNames = new ArrayList<>();
		playerNames.add("a");
		playerNames.add("b");
		Game game = new Game(playerNames);
		TurnHandler turnHandler = new TurnHandler();
		turnHandler.useMainAction();
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayer(game.getPlayersNumber() - 1));
		List<NobilityTrackStep> nobilityTrackSteps = game.getNobilityTrack().getSteps();
		game.getNobilityTrack().walkOnNobilityTrack(0, nobilityTrackSteps.size() - 1, game, turnHandler);
		TurnHandler turnHandler2 = new TurnHandler();
		turnHandler2.useMainAction();
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayer(game.getPlayersNumber() - 2));
		for(NobilityTrackStep step : nobilityTrackSteps) {
			for(Bonus bonus : step.getBonuses()) {
				if(!bonus.isNull()) {
					((RealBonus)bonus).updateBonus(game, turnHandler2);
				}				
			}
		}
		assertAll(game, turnHandler, turnHandler2);
		game.getNobilityTrack().walkOnNobilityTrack(nobilityTrackSteps.size() - 1, nobilityTrackSteps.size() + 5, game, turnHandler);
		assertAll(game, turnHandler, turnHandler2);		
	}
	
	private void assertAll(Game game, TurnHandler turnHandler, TurnHandler turnHandler2) {
		assertTrue(turnHandler.isAvailableMainAction() == turnHandler2.isAvailableMainAction());
		assertTrue(turnHandler.getSuperBonuses().equals(turnHandler2.getSuperBonuses()));
		assertTrue(game.getGamePlayersSet().getPlayer(0).getCoins() == game.getGamePlayersSet().getPlayer(1).getCoins() - 1);
		assertTrue(game.getGamePlayersSet().getPlayer(0).getAssistants() == game.getGamePlayersSet().getPlayer(1).getAssistants() - 1);
		assertTrue(game.getGamePlayersSet().getPlayer(0).getVictoryPoints() == game.getGamePlayersSet().getPlayer(1).getVictoryPoints());
		assertTrue(game.getGamePlayersSet().getPlayer(0).getNumberOfPoliticCards() == game.getGamePlayersSet().getPlayer(1).getNumberOfPoliticCards());
	}

}
