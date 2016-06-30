package it.polimi.ingsw.ps23.client.socket.gui;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;

class RewardTokensExpression extends UIComponentsParser {
	
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
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			citiesName = new ArrayList<>();
			rewardTokensName = new ArrayList<>();
			rewardTokensValue = new ArrayList<>();
			do {
				parsingMessage = addField(citiesName, parsingMessage);
				parsingMessage = addBonuses(rewardTokensName, rewardTokensValue, parsingMessage);
			} while(!parsingMessage.isEmpty());
		}
	}

}
