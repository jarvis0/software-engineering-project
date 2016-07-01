package it.polimi.ingsw.ps23.client.socket.gui;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;

class PermitTilesUpExpression extends GUIComponentsParser {

	private Expression expression;
	
	private List<String> regions;
	private List<List<List<String>>> permitTilesCities;
	private List<List<List<String>>> permitTilesBonusesName;
	private List<List<List<String>>> permitTilesBonusesValue;
	
	PermitTilesUpExpression(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			regions = new ArrayList<>();
			permitTilesCities = new ArrayList<>();
			permitTilesBonusesName = new ArrayList<>();
			permitTilesBonusesValue = new ArrayList<>();
			String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			int regionsNumber = Integer.parseInt(field);
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			for(int i = 0; i < regionsNumber; i++) {
				parsingMessage = addField(regions, parsingMessage);
				parsingMessage = addPermitTiles(parsingMessage, permitTilesCities, permitTilesBonusesName, permitTilesBonusesValue);
			}
		}
	}

}
