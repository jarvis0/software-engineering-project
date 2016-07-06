package it.polimi.ingsw.ps23.server.model;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests the {@link TurnHandler} and all methods to manage the action of the current player.
 * @author Mirco Manzoni
 *
 */
public class TestTurnHandler {

	@Test
	public void test() {
		TurnHandler turnHandler = new TurnHandler();
		assertTrue(turnHandler.isAvailableMainAction() && turnHandler.isAvailableQuickAction());
		turnHandler.useMainAction();
		assertTrue(!turnHandler.isAvailableMainAction());
		turnHandler.useQuickAction();
		assertTrue(!turnHandler.isAvailableQuickAction());
		turnHandler.addMainAction();
		assertTrue(turnHandler.isAvailableMainAction());
	}

}
