package it.polimi.ingsw.ps23.server.view;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;

class SocketParametersCreator {

	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	private static final String REWARD_TOKENS_TAG_OPEN = "<reward_tokens>";
	private static final String REWARD_TOKENS_TAG_CLOSE = "</reward_tokens>";
	
	private String addKingPosition(String kingPosition) {
		return KING_POSITION_TAG_OPEN + kingPosition + KING_POSITION_TAG_CLOSE;
	}
	
	private String addRewardTokens(Map<String, City> cities) {
		Set<Entry<String, City>> citiesEntry = cities.entrySet();
		StringBuilder citiesSend = new StringBuilder();
		for(Entry<String, City> cityEntry : citiesEntry) {
			City city = cityEntry.getValue();
			citiesSend.append(city.getName());
			if(!city.isCapital()) {
				int rewardTokensNumber = ((NormalCity) city).getRewardToken().getBonuses().size();
				citiesSend.append("," + rewardTokensNumber);
				StringBuilder rewardTokens = new StringBuilder();
				for(int i = 0; i < rewardTokensNumber; i++) {
					Bonus bonus = ((NormalCity) city).getRewardToken().getBonuses().get(i);
					rewardTokens.append("," + bonus.getName());
					rewardTokens.append("," + bonus.getValue());
				}
				citiesSend.append(rewardTokens + "#");
			}
		}
		return REWARD_TOKENS_TAG_OPEN + citiesSend + REWARD_TOKENS_TAG_CLOSE;
	}
	
	String createUIStatus(StartTurnState currentState) {
		String message = addKingPosition(currentState.getKing().getPosition().getName());
		return message;
	}

	String createUIStaticContent(Map<String, City> cities, NobilityTrack nobilityTrack) {
		String message = addRewardTokens(cities);
		return message;
	}
	
}
