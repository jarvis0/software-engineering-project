package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.initialization.GameColorsBuilder;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
/**
 * Tests the {@link GameColorBuilder} checking how the color is created.
 * @author Giuseppe Mascellaro
 *
 */
public class TestGameColorBuilder {

	@Test
	public void test() {
		GameColor color0 = GameColorsBuilder.makeColor("White");
		GameColor color1 = GameColorsBuilder.makeColor("White");
		GameColor color2 = GameColorsBuilder.makeColor("Black");
		GameColor color3 = GameColorsBuilder.makeColor("Black");		
		assertTrue(color0 == color1);
		assertTrue(color0 != color2);
		assertTrue(color2 == color3);
		assertTrue(color0 != color3);
	}

}
