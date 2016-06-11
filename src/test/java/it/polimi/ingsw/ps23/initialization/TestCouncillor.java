package it.polimi.ingsw.ps23.initialization;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.map.Councillor;

public class TestCouncillor {

	@Test
	public void test() {
		GameColor color = new GameColor("White", "0xffffff");
		Councillor councillor = new Councillor(color);
		assertTrue(councillor.toString().equals("White"));
	}

}
