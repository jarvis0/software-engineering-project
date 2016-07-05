package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;

class PlayersEmporiumsExpression extends GUIParser {

	private Expression expression;
	
	private List<List<String>> playersEmporiums;
	
	PlayersEmporiumsExpression(Expression expression) {
		this.expression = expression;
		playersEmporiums = new ArrayList<>();
	}
	
	List<List<String>> getPlayersEmporiums() {
		return playersEmporiums;
	}
	
	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			int citiesNumber = Integer.parseInt(field);
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			for(int i = 0; i < citiesNumber; i++) {
				List<String> playerEmporium = new ArrayList<>();
				field = parsingMessage.substring(0, parsingMessage.indexOf(','));
				int emporiumsNumber = Integer.parseInt(field);
				parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
				for(int j = 0; j < emporiumsNumber; j++) {
					parsingMessage = addField(playerEmporium, parsingMessage);
				}
				playersEmporiums.add(playerEmporium);
			}
		}
	}

}
