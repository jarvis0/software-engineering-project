package it.polimi.ingsw.ps23.initialization;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.initialization.GameColorFactory;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;

public class TestCouncillor {

	@Test
	public void test() {
		GameColor color = GameColorFactory.makeColor("White", "0xffffff");
		Councillor councillor = new Councillor(color);
		assertTrue(councillor.toString().equals("White"));
		assertTrue(councillor.getColor().equals(color));
	}

}
