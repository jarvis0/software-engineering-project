package it.polimi.ingsw.ps23.server.model.map;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.server.model.map.board.GroupColoredCity;
import it.polimi.ingsw.ps23.server.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.City;

public class TestProtectedMethodRegion {

	@Test
	public void test() {
		VictoryPointBonus bonus = new VictoryPointBonus("victoryPoint");
		Region region = new GroupColoredCity("a", bonus);
		assertTrue(region.getBonusTile().equals(bonus));
		City city = new CapitalCity("b", new GameColor("pink"));
		region.addCity(city);
		assertTrue(region.getCities().entrySet().iterator().next().getValue().equals(city));
	}

}
