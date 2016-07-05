package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.RealBonus;
import it.polimi.ingsw.ps23.server.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;

public class TestRewardTokensExpression {

	@Test
	public void test() {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		Game game = new Game(players);
		String message = addRewardTokens(game.getGameMap().getCities());
		RewardTokensExpression rewardTokensExpression = new RewardTokensExpression(new TerminalExpression("<reward_tokens>", "</reward_tokens>"));
		rewardTokensExpression.parse(message);
		Iterator<City> iterator = game.getGameMap().getCities().values().iterator();
		while(iterator.hasNext()) {
			City city = iterator.next();
			if(!(city instanceof CapitalCity)) {
				assertTrue(rewardTokensExpression.getCitiesName().toString().contains(city.getName()));
				for(Bonus bonus : ((NormalCity)city).getRewardToken().getBonuses()) {
					assertTrue(rewardTokensExpression.getRewardTokensName().toString().contains(bonus.getName()));
					assertTrue(rewardTokensExpression.getRewardTokensValue().toString().contains(String.valueOf(((RealBonus)bonus).getValue())));
				}
			}
		}
	}
	
	private String addRewardTokens(Map<String, City> cities) {
		Set<Entry<String, City>> citiesEntry = cities.entrySet();
		StringBuilder citiesSend = new StringBuilder();
		for(Entry<String, City> cityEntry : citiesEntry) {
			City city = cityEntry.getValue();
			if(!city.isCapital()) {
				citiesSend.append(city.getName());
				citiesSend.append(",");
				addBonuses(citiesSend, ((NormalCity) city).getRewardToken().getBonuses());
				citiesSend.append(",");
			}
		}
		return "<reward_tokens>" + citiesSend + "</reward_tokens>";
	}
	
	private void addBonuses(StringBuilder bonusesSend, List<Bonus> bonuses) {
		int bonusesNumber = bonuses.size();
		bonusesSend.append(bonusesNumber);
		for(Bonus bonus : bonuses) {
			bonusesSend.append("," + bonus.getName());
			bonusesSend.append("," + ((RealBonus)bonus).getValue());
		}
	}

}
