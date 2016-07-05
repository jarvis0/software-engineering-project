package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;

public class TestTerminalExpression {

	@Test
	public void test() {
		String startTag = "<a>";
		String finishTag = "</a>";
		Expression expression = new TerminalExpression(startTag, finishTag);
		String message = "hello world!";
		assertTrue(expression.interpret(startTag + message + finishTag));
		assertTrue(expression.selectBlock(startTag + message + finishTag).equals(message));
		assertTrue(expression.removeTags(startTag + message + finishTag).equals(message));
		assertTrue(expression.removeBlock(startTag + message + finishTag).equals(""));		
	}

}
