package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;

class BonusTilesExpression extends GUIParser {

	private Expression expression;
	
	private List<String> groupsName = new ArrayList<>();
	private List<String> groupsBonusName = new ArrayList<>();
	private List<String> groupsBonusValue = new ArrayList<>();
	private String kingBonusName;
	private String kingBonusValue;
	
	BonusTilesExpression(Expression expression) {
		this.expression = expression;
	}
	
	List<String> getGroupsName() {
		return groupsName;
	}
	
	List<String> getGroupsBonusName() {
		return groupsBonusName;
	}
	
	List<String> getGroupsBonusValue() {
		return groupsBonusValue;
	}
	
	String getKingBonusName() {
		return kingBonusName;
	}
	
	String getKingBonusValue() {
		return kingBonusValue;
	}
	
	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			int groupsNumber = Integer.parseInt(field);
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			groupsName = new ArrayList<>();
			groupsBonusName = new ArrayList<>();
			groupsBonusValue = new ArrayList<>();
			for(int i = 0; i < groupsNumber; i++) {
				parsingMessage = addField(groupsName, parsingMessage);
				parsingMessage = addField(groupsBonusName, parsingMessage);
				parsingMessage = addField(groupsBonusValue, parsingMessage);
			}
			kingBonusName = parsingMessage.substring(0, parsingMessage.indexOf(','));
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			kingBonusValue = parsingMessage.substring(0, parsingMessage.indexOf(','));
		}
	}

}
