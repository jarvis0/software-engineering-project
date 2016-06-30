package it.polimi.ingsw.ps23.server.view;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.Set;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrackStep;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;

class SocketParametersCreator {

	private static final String STATIC_CONTENT_TAG_OPEN = "<static_content>";
	private static final String STATIC_CONTENT_TAG_CLOSE = "</static_content>";
	private static final String REWARD_TOKENS_TAG_OPEN = "<reward_tokens>";
	private static final String REWARD_TOKENS_TAG_CLOSE = "</reward_tokens>";
	private static final String NOBILITY_TRACK_TAG_OPEN = "<nobility_track>";
	private static final String NOBILITY_TRACK_TAG_CLOSE = "</nobility_track>";
	private static final String DYNAMIC_CONTENT_TAG_OPEN = "<dynamic_content>";
	private static final String DYNAMIC_CONTENT_TAG_CLOSE = "</dynamic_content>";
	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	private static final String FREE_COUNCILLORS_TAG_OPEN = "<free_councillors>";
	private static final String FREE_COUNCILLORS_TAG_CLOSE = "</free_councillors>";
	private static final String COUNCILS_TAG_OPEN = "<councils>";
	private static final String COUNCILS_TAG_CLOSE = "</councils>";
	private static final String BONUS_TILES_TAG_OPEN = "<bonus_tiles>";
	private static final String BONUS_TILES_TAG_CLOSE = "</bonus_tiles>";
	private static final String KINGDOM = "kingdom";
	
	private String addKingPosition(String kingPosition) {
		return KING_POSITION_TAG_OPEN + kingPosition + KING_POSITION_TAG_CLOSE;
	}
	
	private String addRewardTokens(Map<String, City> cities) {
		Set<Entry<String, City>> citiesEntry = cities.entrySet();
		StringBuilder citiesSend = new StringBuilder();
		for(Entry<String, City> cityEntry : citiesEntry) {
			City city = cityEntry.getValue();
			if(!city.isCapital()) {
				citiesSend.append(city.getName());
				int rewardTokensNumber = ((NormalCity) city).getRewardToken().getBonuses().size();
				citiesSend.append("," + rewardTokensNumber);
				StringBuilder rewardTokens = new StringBuilder();
				for(int i = 0; i < rewardTokensNumber; i++) {
					Bonus bonus = ((NormalCity) city).getRewardToken().getBonuses().get(i);
					rewardTokens.append("," + bonus.getName());
					rewardTokens.append("," + bonus.getValue());
				}
				citiesSend.append(rewardTokens + ",");
			}
		}
		return REWARD_TOKENS_TAG_OPEN + citiesSend + REWARD_TOKENS_TAG_CLOSE;
	}

	private String addNobilityTrackSteps(List<NobilityTrackStep> steps) {
		StringBuilder nobilityTrackSend = new StringBuilder();
		for(NobilityTrackStep step : steps) {
			List<Bonus> bonuses = step.getBonuses();
			int bonusesNumber = bonuses.size();
			nobilityTrackSend.append(bonusesNumber);
			for(int i = 0; i < bonusesNumber; i++) {
				Bonus bonus = bonuses.get(i);
				nobilityTrackSend.append("," + bonus.getName());
				nobilityTrackSend.append("," + bonus.getValue());
			}
			nobilityTrackSend.append(",");
		}
		return NOBILITY_TRACK_TAG_OPEN + nobilityTrackSend + NOBILITY_TRACK_TAG_CLOSE;
	}

	String createUIStaticContents(Map<String, City> cities, NobilityTrack nobilityTrack) {
		return STATIC_CONTENT_TAG_OPEN + addRewardTokens(cities) + addNobilityTrackSteps(nobilityTrack.getSteps()) + STATIC_CONTENT_TAG_CLOSE;
	}

	private String addFreeCouncillors(List<Councillor> freeCouncillors) {
		StringBuilder freeCouncillorsSend = new StringBuilder();
		for(int i = 0; i < freeCouncillors.size(); i++) {
			freeCouncillorsSend.append(freeCouncillors.get(i).getColor() + ",");
		}
		return FREE_COUNCILLORS_TAG_OPEN + freeCouncillorsSend + FREE_COUNCILLORS_TAG_CLOSE;
	}

	private String addCouncils(List<Region> regions, Council kingCouncil) {
		StringBuilder councilsSend = new StringBuilder();
		for(int i = 0; i < regions.size(); i++) {
			GroupRegionalCity region = (GroupRegionalCity) regions.get(i);
			councilsSend.append(region.getName());
			Queue<Councillor> councillors = region.getCouncil().getCouncillors();
			councilsSend.append("," + councillors.size());
			for(Councillor councillor : councillors) {
				councilsSend.append("," + councillor.getColor().toString());
			}
			councilsSend.append(",");
		}
		councilsSend.append(KINGDOM);//evitabile
		Queue<Councillor> councillors = kingCouncil.getCouncillors();
		councilsSend.append("," + councillors.size());
		for(Councillor councillor : councillors) {
			councilsSend.append("," + councillor.getColor().toString());
		}
		councilsSend.append(",");
		return COUNCILS_TAG_OPEN + councilsSend + COUNCILS_TAG_CLOSE;
	}

	private String addBonusTiles(List<Region> groupRegionalCity, List<Region> groupColoredCity, Bonus currentKingTile) {
		StringBuilder bonusTilesSend = new StringBuilder();
		int groupsNumber = groupRegionalCity.size() + groupColoredCity.size();
		bonusTilesSend.append(groupsNumber);//TODO already aquired
		for(int i = 0; i < groupRegionalCity.size(); i++) {
			bonusTilesSend.append("," + groupRegionalCity.get(i).getName());
			Bonus bonus = groupRegionalCity.get(i).getBonusTile();
			bonusTilesSend.append("," + bonus.getName());
			bonusTilesSend.append("," + bonus.getValue());
		}
		for(int i = 0; i < groupColoredCity.size(); i++) {
			bonusTilesSend.append("," + groupColoredCity.get(i).getName());
			Bonus bonus = groupColoredCity.get(i).getBonusTile();
			bonusTilesSend.append("," + bonus.getName());
			bonusTilesSend.append("," + bonus.getValue());
		}
		bonusTilesSend.append("," + currentKingTile.getName());
		bonusTilesSend.append("," + currentKingTile.getValue());
		bonusTilesSend.append(",");
		return BONUS_TILES_TAG_OPEN + bonusTilesSend + BONUS_TILES_TAG_CLOSE;
	}

	String createUIDynamicContents(StartTurnState currentState) {
		String message = addKingPosition(currentState.getKingPosition());
		message += addFreeCouncillors(currentState.getFreeCouncillors());
		message += addCouncils(currentState.getGroupRegionalCity(), currentState.getKingCouncil());
		message += addBonusTiles(currentState.getGroupRegionalCity(), currentState.getGroupColoredCity(), currentState.getCurrentKingTile());
		return DYNAMIC_CONTENT_TAG_OPEN + message + DYNAMIC_CONTENT_TAG_CLOSE;
	}

}
