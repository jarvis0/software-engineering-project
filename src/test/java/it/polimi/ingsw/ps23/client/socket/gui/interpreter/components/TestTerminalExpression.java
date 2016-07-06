package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
/**
 * Tests if {@link TerminalExpression} can interpret message inside defined tags.
 * @author Mirco Manzoni
 *
 */
public class TestTerminalExpression {

	private static final String START = "<a>";
	private static final String FINISH = "</a>";
	
	@Test
	public void test() {
		Expression expression = new TerminalExpression(START, FINISH);
		String message = "hello world!";
		assertTrue(expression.interpret(START + message + FINISH));
		assertTrue(expression.selectBlock(START + message + FINISH).equals(message));
		assertTrue(expression.removeTags(START + message + FINISH).equals(message));
		assertTrue(expression.removeBlock(START + message + FINISH).equals(""));		
	}

}
