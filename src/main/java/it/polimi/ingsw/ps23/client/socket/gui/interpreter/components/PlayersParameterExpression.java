package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;

class PlayersParameterExpression extends GUIParser {

	private Expression expression;
	
	private List<String> names;
	private List<String> coins;
	private List<String> assistants;
	private List<String> victoryPoints;
	private List<String> nobilityTrackPoints;
	private List<List<String>> builtEmporiums;
	
	private List<List<List<String>>> permitTilesCities;
	private List<List<List<String>>> permitTilesBonusesName;
	private List<List<List<String>>> permitTilesBonusesValue;
	private List<List<List<String>>> totalPermitTilesCities;
	private List<List<List<String>>> totalPermitTilesBonusesName;
	private List<List<List<String>>> totalPermitTilesBonusesValue;
	private Map<String, List<String>> politicCards;
	private List<String> areOnline;
	
	PlayersParameterExpression(Expression expression) {
		this.expression = expression;
	}

	List<String> getNames() {
		return names;
	}
	
	List<String> getCoins() {
		return coins;
	}
	
	List<String> getAssistants() {
		return assistants;
	}
	
	List<String> getVictoryPoints() {
		return victoryPoints;
	}
	
	List<String> getNobilityTrackPoints() {
		return nobilityTrackPoints;
	}
	
	Map<String, List<String>> getPoliticCards() {
		return politicCards;
	}
	
	List<List<List<String>>> getPermitTilesCities() {
		return permitTilesCities;
	}
	
	List<List<List<String>>> getPermitTilesBonusesName() {
		return permitTilesBonusesName;
	}
	
	List<List<List<String>>> getPermitTilesBonusesValue() {
		return permitTilesBonusesValue;
	}
	
	
	List<List<List<String>>> getTotalPermitTilesCities() {
		return totalPermitTilesCities;
	}
	
	List<List<List<String>>> getTotalPermitTilesBonusesName() {
		return totalPermitTilesBonusesName;
	}
	
	List<List<List<String>>> getTotalPermitTilesBonusesValue() {
		return totalPermitTilesBonusesValue;
	}
	
	List<String> getOnline() {
		return areOnline;
	}
	
	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			names = new ArrayList<>();
			coins = new ArrayList<>();
			assistants = new ArrayList<>();
			victoryPoints = new ArrayList<>();
			nobilityTrackPoints = new ArrayList<>();
			builtEmporiums = new ArrayList<>();
			permitTilesCities = new ArrayList<>();
			permitTilesBonusesName = new ArrayList<>();
			permitTilesBonusesValue = new ArrayList<>();
			totalPermitTilesCities = new ArrayList<>();
			totalPermitTilesBonusesName = new ArrayList<>();
			totalPermitTilesBonusesValue = new ArrayList<>();
			areOnline = new ArrayList<>();
			politicCards = new HashMap<>();
			String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			int playersNumber = Integer.parseInt(field);
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			for(int i = 0; i < playersNumber; i++) {
				parsingMessage = addField(names, parsingMessage);
				parsingMessage = addField(coins, parsingMessage);
				parsingMessage = addField(assistants, parsingMessage);
				parsingMessage = addField(victoryPoints, parsingMessage);
				parsingMessage = addField(nobilityTrackPoints, parsingMessage);

				field = parsingMessage.substring(0, parsingMessage.indexOf(','));
				int builtEmporiumsNumber = Integer.parseInt(field);
				parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
				List<String> playerBuiltEmporiums = new ArrayList<>();
				for(int j = 0; j < builtEmporiumsNumber; j++) {
					parsingMessage = addField(playerBuiltEmporiums, parsingMessage);
				}
				builtEmporiums.add(playerBuiltEmporiums);

				parsingMessage = addPermitTiles(parsingMessage, permitTilesCities, permitTilesBonusesName, permitTilesBonusesValue);
				
				parsingMessage = addPermitTiles(parsingMessage, totalPermitTilesCities, totalPermitTilesBonusesName, totalPermitTilesBonusesValue);
				
				field = parsingMessage.substring(0, parsingMessage.indexOf(','));
				int politicCardsNumber = Integer.parseInt(field);
				parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
				List<String> playerPoliticCards = new ArrayList<>();
				for(int j = 0; j < politicCardsNumber; j++) {
					parsingMessage = addField(playerPoliticCards, parsingMessage);
				}
				politicCards.put(names.get(i), playerPoliticCards);
				
				parsingMessage = addField(areOnline, parsingMessage);
			}
		}
	}

}
