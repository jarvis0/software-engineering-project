package it.polimi.ingsw.ps23.client.socket.gui;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;

class NobilityTrackExpression implements Parser {

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
	public String parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.removeTag(message);
			String field;
			stepsName = new ArrayList<>();
			stepsValue = new ArrayList<>();
			do {
				List<String> bonusesName = new ArrayList<>();
				List<String> bonusesValue = new ArrayList<>();
				field = parsingMessage.substring(0, parsingMessage.indexOf(','));
				parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
				int bonusesNumber = Integer.parseInt(field);
				for(int i = 0; i < bonusesNumber; i++) {
					field = parsingMessage.substring(0, parsingMessage.indexOf(','));
					bonusesName.add(field);
					parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
					field = parsingMessage.substring(0, parsingMessage.indexOf(','));
					bonusesValue.add(field);
					parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
				}
				stepsName.add(bonusesName);
				stepsValue.add(bonusesValue);
			} while(!parsingMessage.isEmpty());
			return expression.removeBlock(message);
		}
		return message;
	}

}
