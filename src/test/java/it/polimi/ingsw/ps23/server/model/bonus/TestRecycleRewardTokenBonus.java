package it.polimi.ingsw.ps23.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;

public class TestRecycleRewardTokenBonus {

	@Test
	public void test() throws InvalidCityException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		TurnHandler turnHandler = new TurnHandler();
		RecycleRewardTokenBonus bonus = new RecycleRewardTokenBonus("Recycle Reward Token");
		bonus.updateBonus(game, turnHandler);
		assertTrue(turnHandler.getSuperBonuses().contains(bonus));
		String checkBonus = bonus.checkBonus(game.getCurrentPlayer());
		Iterator<City> iterator = game.getGameMap().getCities().values().iterator();
		boolean found = false;
		City city = null;
		while(!found && iterator.hasNext()) {
			city = iterator.next();
			if(!city.isCapital()) {
				for(Bonus bonus2 : ((NormalCity)city).getRewardToken().getBonuses()) {
					if(!(bonus2 instanceof NobilityTrackStepBonus)) {
						found = true;
					}
				}
			}
		}
		game.getCurrentPlayer().getEmporiums().getBuiltEmporiumsSet().add(city);
		List<String> input = new ArrayList<>();
		input.add(city.getName());
		bonus.acquireSuperBonus(input, game, turnHandler);
		assertFalse(checkBonus.equals(bonus.checkBonus(game.getCurrentPlayer())));
	}

}
