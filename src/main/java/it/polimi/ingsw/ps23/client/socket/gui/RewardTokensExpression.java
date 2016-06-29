package it.polimi.ingsw.ps23.client.socket.gui;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;

class RewardTokensExpression implements Parser {
	
	private Expression expression;

	private List<String> citiesName;
	private List<List<String>> rewardTokensName;
	private List<List<String>> rewardTokensValue;
	
	RewardTokensExpression(Expression expression) {
		this.expression = expression;
	}
	
	List<String> getCitiesName() {
		return citiesName;
	}

	List<List<String>> getRewardTokensName() {
		return rewardTokensName;
	}

	List<List<String>> getRewardTokensValue() {
		return rewardTokensValue;
	}
	
	@Override
	public String parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.removeTag(message);
			String field;
			citiesName = new ArrayList<>();
			rewardTokensName = new ArrayList<>();
			rewardTokensValue = new ArrayList<>();
			do {
				field = parsingMessage.substring(0, parsingMessage.indexOf(','));
				citiesName.add(field);
				parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
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
				rewardTokensName.add(bonusesName);
				rewardTokensValue.add(bonusesValue);
			} while(!parsingMessage.isEmpty());
			return expression.removeBlock(message);
		}
		return message;
	}

}
