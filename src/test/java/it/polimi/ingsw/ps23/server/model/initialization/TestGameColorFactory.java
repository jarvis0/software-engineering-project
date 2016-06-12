package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.initialization.GameColorFactory;
import it.polimi.ingsw.ps23.server.model.map.GameColor;

public class TestGameColorFactory {

	@Test
	public void test() {
		GameColor color0 = GameColorFactory.makeColor("White", "0xfffff");
		GameColor color1 = GameColorFactory.makeColor("White", "0xfffff");
		GameColor color2 = GameColorFactory.makeColor("Black", "0xfffff");
		GameColor color3 = GameColorFactory.makeColor("Black", "0x0ffff");
		
		assertTrue(color0 == color1);
		assertTrue(color0 != color2);
		assertTrue(color2 != color3);
		assertTrue(color0 != color3);
	}

}