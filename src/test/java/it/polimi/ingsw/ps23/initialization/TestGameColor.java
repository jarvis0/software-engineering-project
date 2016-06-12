package it.polimi.ingsw.ps23.initialization;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.model.map.GameColor;

public class TestGameColor {

	@Test
	public void test() {
		GameColor color = new GameColor("White", "0xfffff");
		assertTrue(color.toString().equals("White"));
	}

}
