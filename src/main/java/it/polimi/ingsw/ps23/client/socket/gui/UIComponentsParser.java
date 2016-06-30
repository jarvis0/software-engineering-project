package it.polimi.ingsw.ps23.client.socket.gui;

import java.util.ArrayList;
import java.util.List;

abstract class UIComponentsParser {

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

}
