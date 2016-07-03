package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;

class CouncilsExpression extends GUIParser {

	private static final String KINGDOM = "kingdom";
	
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
	
	private String addCouncil(List<List<String>> councilsColor, String message) {
		String parsingMessage = message;
		String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
		int councillorsNumber = Integer.parseInt(field);
		parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
		List<String> council = new ArrayList<>();
		for(int j = 0; j < councillorsNumber; j++) {
			parsingMessage = addField(council, parsingMessage);
		}
		councilsColor.add(council);
		return parsingMessage;
	}
	
	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			councilsName = new ArrayList<>();
			councilsColor = new ArrayList<>();
			String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			int regionalCouncilsNumber = Integer.parseInt(field);
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			for(int i = 0; i < regionalCouncilsNumber; i++) {
				parsingMessage = addField(councilsName, parsingMessage);
				parsingMessage = addCouncil(councilsColor, parsingMessage);
			}
			councilsName.add(KINGDOM);
			addCouncil(councilsColor, parsingMessage);
		}
	}

}
