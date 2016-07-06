package it.polimi.ingsw.ps23.client.socket.gui.interpreter;

import java.util.ArrayList;
import java.util.List;
/**
 * Provides an abstract class for parsing string sent via socket.
 * @author Giuseppe Mascellaro
 *
 */
public abstract class GUIParser {

	protected abstract void parse(String message);
	
	protected String addField(List<String> list, String parsingMessage) {
		list.add(parsingMessage.substring(0, parsingMessage.indexOf(',')));
		return parsingMessage.substring(parsingMessage.indexOf(',') + 1);
	}

	protected String addBonuses(List<List<String>> namesLists, List<List<String>> valuesLists, String message) {
		String field = message.substring(0, message.indexOf(','));
		int bonusesNumber = Integer.parseInt(field);
		String parsingMessage = message.substring(message.indexOf(',') + 1);
		List<String> bonusesName = new ArrayList<>();
		List<String> bonusesValue = new ArrayList<>();
		for(int i = 0; i < bonusesNumber; i++) {
			parsingMessage = addField(bonusesName, parsingMessage);
			parsingMessage = addField(bonusesValue, parsingMessage);
		}
		namesLists.add(bonusesName);
		valuesLists.add(bonusesValue);
		return parsingMessage;
	}
	
	protected String addPermitTile(String message, List<List<String>> playerPermitTilesCity, List<List<String>> playerPermitTilesBonusesName, List<List<String>> playerPermitTilesBonusesValue) {
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
		parsingMessage = addBonuses(playerPermitTilesBonusesName, playerPermitTilesBonusesValue, parsingMessage);
		return parsingMessage;
	}
	
	protected String addPermitTiles(String message, List<List<List<String>>> permitTilesCities, List<List<List<String>>> permitTilesBonusesName, List<List<List<String>>> permitTilesBonusesValue) {
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

}
