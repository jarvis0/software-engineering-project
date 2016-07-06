package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.map.GameColor;
/**
 * Tests the construction of the {@link GameColor}.
 * @author Mirco Manzoni
 *
 */
public class TestGameColor {

	@Test
	public void test() {
		GameColor color = new GameColor("White");
		assertTrue(color.toString().equals("White"));
	}

}
