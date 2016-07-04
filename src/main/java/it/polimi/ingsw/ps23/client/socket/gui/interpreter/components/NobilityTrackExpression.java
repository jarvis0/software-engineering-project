package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;

class NobilityTrackExpression extends GUIParser {

	private Expression expression;
	
	private List<List<String>> stepsName;
	private List<List<String>> stepsValue;
	
	NobilityTrackExpression(Expression expression) {
		this.expression = expression;
	}

	List<List<String>> getStepsName() {
		return stepsName;
	}

	List<List<String>> getStepsValue() {
		return stepsValue;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			stepsName = new ArrayList<>();
			stepsValue = new ArrayList<>();
			do {
				parsingMessage = addBonuses(stepsName, stepsValue, parsingMessage);
			} while(!parsingMessage.isEmpty());
		}
	}

}
