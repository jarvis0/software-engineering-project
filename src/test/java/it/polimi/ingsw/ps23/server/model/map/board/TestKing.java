package it.polimi.ingsw.ps23.server.model.map.board;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;

public class TestKing {

	@Test
	public void test() {
		GameColor gameColor = new GameColor("blue");
		City city = new CapitalCity("a", gameColor);
		Queue<Councillor> councillors = new LinkedList<>();
		Council council = new Council(councillors);
		King king = new King(city, council);
		assertTrue(king.getCouncil().equals(council));
		city = new CapitalCity("b", gameColor);
		king.setNewPosition(city);
		assertTrue(king.getPosition().equals(city));
	}

}
