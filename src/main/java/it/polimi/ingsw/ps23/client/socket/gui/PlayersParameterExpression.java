package it.polimi.ingsw.ps23.client.socket.gui;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;

class PlayersParameterExpression extends GUIComponentsParser {

	private Expression expression;
	
	private List<String> players;
	private List<String> coins;
	private List<String> assistants;
	private List<String> victoryPoints;
	private List<String> nobilityTrackPoints;
	private List<List<String>> builtEmporiums;
	
	private List<List<List<String>>> permitTilesCities;
	private List<List<List<String>>> permitTilesBonusesName;
	private List<List<List<String>>> permitTilesBonusesValue;
	private List<List<List<String>>> usedPermitTilesCities;
	private List<List<List<String>>> usedPermitTilesBonusesName;
	private List<List<List<String>>> usedPermitTilesBonusesValue;
	private List<List<String>> politicCards;
	private List<String> isOnline;
	
	PlayersParameterExpression(Expression expression) {
		this.expression = expression;
	}

	private String addPermitTile(String message, List<List<String>> playerPermitTilesCity, List<List<String>> playerPermitTilesBonusesName, List<List<String>> playerPermitTilesBonusesValue) {
		String parsingMessage = message;
		String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
		int permitTilesCityNumber = Integer.parseInt(field);
		parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
		List<String> playerPermitTileCity = new ArrayList<>();
		for(int k = 0; k < permitTilesCityNumber; k++) {
			field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			playerPermitTileCity.add(field);
		}
		playerPermitTilesCity.add(playerPermitTileCity);
		addBonuses(playerPermitTilesBonusesName, playerPermitTilesBonusesValue, parsingMessage);
		return parsingMessage;
	}
	
	private String addPermitTiles(String message, List<List<List<String>>> permitTilesCities, List<List<List<String>>> permitTilesBonusesName, List<List<List<String>>> permitTilesBonusesValue) {
		String parsingMessage = message;
		String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
		int permitTilesNumber = Integer.parseInt(field);
		parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
		List<List<String>> playerPermitTilesCity = new ArrayList<>();
		List<List<String>> playerPermitTilesBonusesName = new ArrayList<>();
		List<List<String>> playerPermitTilesBonusesValue = new ArrayList<>();
		for(int j = 0; j < permitTilesNumber; j++) {
			parsingMessage = addPermitTile(parsingMessage, playerPermitTilesCity, playerPermitTilesBonusesName, playerPermitTilesBonusesValue);
		}
		permitTilesCities.add(playerPermitTilesCity);
		permitTilesBonusesName.add(playerPermitTilesBonusesName);
		permitTilesBonusesValue.add(playerPermitTilesBonusesValue);
		return parsingMessage;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			players = new ArrayList<>();
			coins = new ArrayList<>();
			assistants = new ArrayList<>();
			victoryPoints = new ArrayList<>();
			nobilityTrackPoints = new ArrayList<>();
			builtEmporiums = new ArrayList<>();
			permitTilesCities = new ArrayList<>();
			permitTilesBonusesName = new ArrayList<>();
			permitTilesBonusesValue = new ArrayList<>();
			usedPermitTilesCities = new ArrayList<>();
			usedPermitTilesBonusesName = new ArrayList<>();
			usedPermitTilesBonusesValue = new ArrayList<>();
			isOnline = new ArrayList<>();
			politicCards = new ArrayList<>();
			String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			int playersNumber = Integer.parseInt(field);
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			for(int i = 0; i < playersNumber; i++) {
				parsingMessage = addField(players, parsingMessage);
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
				
				parsingMessage = addPermitTiles(parsingMessage, usedPermitTilesCities, usedPermitTilesBonusesName, usedPermitTilesBonusesValue);
				
				field = parsingMessage.substring(0, parsingMessage.indexOf(','));
				int politicCardsNumber = Integer.parseInt(field);
				parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
				List<String> playerPoliticCards = new ArrayList<>();
				for(int j = 0; j < politicCardsNumber; j++) {
					parsingMessage = addField(playerPoliticCards, parsingMessage);
				}
				politicCards.add(playerPoliticCards);
				
				parsingMessage = addField(isOnline, parsingMessage);
			}
		}
	}

}
