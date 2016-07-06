package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.initialization.GameColorsBuilder;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
/**
 * Tests the construction of the {@link Councillor}.
 * @author mirma
 *
 */
public class TestCouncillor {

	@Test
	public void test() {
		GameColor color = GameColorsBuilder.makeColor("White");
		Councillor councillor = new Councillor(color);
		assertTrue(councillor.toString().equals("White"));
		assertTrue(councillor.getColor().equals(color));
	}

}
