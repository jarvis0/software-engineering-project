package it.polimi.ingsw.ps23.client.socket.gui;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;

class CouncilsExpression extends UIComponentsParser {

	private Expression expression;

	private List<String> councilsName;
	private List<List<String>> councilsColor;
	
	CouncilsExpression(Expression expression) {
		this.expression = expression;
	}
	
	List<String> getCouncilsName() {
		return councilsName;
	}
	
	List<List<String>> getCouncilsColor() {
		return councilsColor;
	}
	
	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			councilsName = new ArrayList<>();
			councilsColor = new ArrayList<>();
			do {
				parsingMessage = addField(councilsName, parsingMessage);
				String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
				int councillorsNumber = Integer.parseInt(field);
				parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
				List<String> council = new ArrayList<>();
				for(int i = 0; i < councillorsNumber; i++) {
					parsingMessage = addField(council, parsingMessage);
				}
				councilsColor.add(council);
			} while(!parsingMessage.isEmpty());
		}
	}

}
