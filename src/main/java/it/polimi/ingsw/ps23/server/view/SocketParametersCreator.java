package it.polimi.ingsw.ps23.server.view;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.Set;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrackStep;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.BusinessPermitTile;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.player.Player;
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
	private static final String PLAYERS_PARAMETERS_TAG_OPEN = "<players_parameters>";
	private static final String PLAYERS_PARAMETERS_TAG_CLOSE = "</players_parameters>";
	private static final String PERMIT_TILES_UP_TAG_OPEN = "<permit_tiles_up>";
	private static final String PERMIT_TILES_UP_TAG_CLOSE = "</permit_tiles_up>";
	private static final String TURN_PARAMETERS_TAG_OPEN = "<turn_parameters>";
	private static final String TURN_PARAMETERS_TAG_CLOSE = "</turn_parameters>";
	
	private static final String ACTION_TAG_OPEN = "<action>";
	private static final String ACTION_TAG_CLOSE = "</action>";
	private static final String ELECT_COUNCILLOR_TAG = "<elect_councillor>";
	private static final String ENGAGE_AN_ASSISTANT_TAG = "<engage_an_assistant>";
	private static final String ACQUIRE_BUSINESS_PERMIT_TILE_TAG = "<acquire_business_permit_tile>";
	private static final String CHANGE_PERMIT_TILES_TAG = "<change_permit_tiles>";
	private static final String ASSISTANT_TO_ELECT_COUNCILLOR_TAG = "<assistant_to_elect_councillor>";
	private static final String ADDITIONAL_MAIN_ACTION_TAG = "<additional_main_action>";
	private static final String BUILD_EMPORIUM_KING_TAG = "<build_emporium_king>";
	private static final String BUILD_EMPORIUM_PERMIT_TILE_TAG = "<build_emporium_permit_tile>";
	private static final String MARKET_OFFER_PHASE_TAG = "<market_offer_phase>";
	private static final String MARKET_BUY_PHASE_TAG = "<market_buy_phase>";
	private static final String SUPER_BONUS_TAG = "<super_bonus>";
	private static final String END_GAME_TAG = "<end_game>";
	
	private String addKingPosition(String kingPosition) {
		return KING_POSITION_TAG_OPEN + kingPosition + KING_POSITION_TAG_CLOSE;
	}
	
	private void addBonuses(StringBuilder bonusesSend, List<Bonus> bonuses) {
		int bonusesNumber = bonuses.size();
		bonusesSend.append(bonusesNumber);
		for(Bonus bonus : bonuses) {
			bonusesSend.append("," + bonus.getName());
			bonusesSend.append("," + bonus.getValue());
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
		return REWARD_TOKENS_TAG_OPEN + citiesSend + REWARD_TOKENS_TAG_CLOSE;
	}

	private String addNobilityTrackSteps(List<NobilityTrackStep> steps) {
		StringBuilder nobilityTrackSend = new StringBuilder();
		for(NobilityTrackStep step : steps) {
			addBonuses(nobilityTrackSend, step.getBonuses());
			nobilityTrackSend.append(",");
		}
		return NOBILITY_TRACK_TAG_OPEN + nobilityTrackSend + NOBILITY_TRACK_TAG_CLOSE;
	}

	String createUIStaticContents(Map<String, City> cities, NobilityTrack nobilityTrack) {
		return STATIC_CONTENT_TAG_OPEN + addRewardTokens(cities) + addNobilityTrackSteps(nobilityTrack.getSteps()) + STATIC_CONTENT_TAG_CLOSE;
	}

	private String addFreeCouncillors(List<Councillor> freeCouncillors) {
		StringBuilder freeCouncillorsSend = new StringBuilder();
		for(Councillor councillor : freeCouncillors) {
			freeCouncillorsSend.append(councillor.getColor() + ",");
		}
		return FREE_COUNCILLORS_TAG_OPEN + freeCouncillorsSend + FREE_COUNCILLORS_TAG_CLOSE;
	}

	private String addCouncils(List<Region> regions, Council kingCouncil) {
		StringBuilder councilsSend = new StringBuilder();
		int regionsNumber = regions.size();
		councilsSend.append(regionsNumber);
		for(int i = 0; i < regionsNumber; i++) {
			GroupRegionalCity region = (GroupRegionalCity) regions.get(i);
			councilsSend.append("," + region.getName());
			Queue<Councillor> councillors = region.getCouncil().getCouncillors();
			councilsSend.append("," + councillors.size());
			for(Councillor councillor : councillors) {
				councilsSend.append("," + councillor.getColor().toString());
			}
		}
		Queue<Councillor> councillors = kingCouncil.getCouncillors();
		councilsSend.append("," + councillors.size());
		for(Councillor councillor : councillors) {
			councilsSend.append("," + councillor.getColor().toString());
		}
		councilsSend.append(",");
		return COUNCILS_TAG_OPEN + councilsSend + COUNCILS_TAG_CLOSE;
	}

	private void addBonusTiles(StringBuilder bonusTilesSend, List<Region> regions) {
		for(Region region : regions) {
			bonusTilesSend.append("," + region.getName());
			Bonus bonus = region.getBonusTile();
			bonusTilesSend.append("," + bonus.getName());
			bonusTilesSend.append("," + bonus.getValue());
		}
	}
	
	private String addBonusTiles(List<Region> groupRegionalCity, List<Region> groupColoredCity, Bonus currentKingTile) {
		StringBuilder bonusTilesSend = new StringBuilder();
		int groupsNumber = groupRegionalCity.size() + groupColoredCity.size();
		bonusTilesSend.append(groupsNumber);//TODO already aquired
		addBonusTiles(bonusTilesSend, groupRegionalCity);
		addBonusTiles(bonusTilesSend, groupColoredCity);
		bonusTilesSend.append("," + currentKingTile.getName());
		bonusTilesSend.append("," + currentKingTile.getValue());
		bonusTilesSend.append(",");
		return BONUS_TILES_TAG_OPEN + bonusTilesSend + BONUS_TILES_TAG_CLOSE;
	}

	private void addPermitHandDeck(StringBuilder playersParameterSend, List<Card> cards) {
		playersParameterSend.append("," + cards.size());
		for(Card card : cards) {
			BusinessPermitTile permitTile = (BusinessPermitTile) card;
			playersParameterSend.append("," + permitTile.getCities().size());
			for(City city : permitTile.getCities()) {
				playersParameterSend.append("," + city.getName().charAt(0));
			}
			playersParameterSend.append(",");
			addBonuses(playersParameterSend, permitTile.getBonuses());
		}
	}

	private void addPoliticHandDeck(StringBuilder playersParameterSend, List<Card> cards) {
		playersParameterSend.append("," + cards.size());
		for(Card politicCard : cards) {
			playersParameterSend.append("," + ((PoliticCard) politicCard).getColor().toString());
		}
	}

	private String addPlayerParameters(List<Player> playersList) {
		StringBuilder playersParameterSend = new StringBuilder();
		playersParameterSend.append(playersList.size());
		for(Player player : playersList) {
			playersParameterSend.append("," + player.getName());
			playersParameterSend.append("," + player.getCoins());
			playersParameterSend.append("," + player.getAssistants());
			playersParameterSend.append("," + player.getVictoryPoints());
			playersParameterSend.append("," + player.getNobilityTrackPoints());
			List<City> builtEmporiums = player.getEmporiums().getBuiltEmporiumsSet();
			int builtEmporiumsNumber = builtEmporiums.size();
			playersParameterSend.append("," + builtEmporiumsNumber);
			for(int j = 0; j < builtEmporiumsNumber; j++) {
				playersParameterSend.append("," + builtEmporiums.get(j).getName());
			}
			addPermitHandDeck(playersParameterSend, player.getPermitHandDeck().getCards());
			addPermitHandDeck(playersParameterSend, player.getPermitUsedHandDeck().getCards());
			addPoliticHandDeck(playersParameterSend, player.getPoliticHandDeck().getCards());
			playersParameterSend.append("," + player.isOnline());
		}//verificare se ha i permit tile ecc.. con il debug F5 TODO
		playersParameterSend.append(",");
		return PLAYERS_PARAMETERS_TAG_OPEN + playersParameterSend + PLAYERS_PARAMETERS_TAG_CLOSE;
	}

	private String addPermitTilesUp(List<Region> groupRegionalCity) {
		StringBuilder permitTilesUpSend = new StringBuilder();
		permitTilesUpSend.append(groupRegionalCity.size());
		for(Region region : groupRegionalCity) {
			permitTilesUpSend.append("," + region.getName());
			addPermitHandDeck(permitTilesUpSend, ((GroupRegionalCity) region).getPermitTilesUp().getCards());
		}
		permitTilesUpSend.append(",");
		return PERMIT_TILES_UP_TAG_OPEN + permitTilesUpSend + PERMIT_TILES_UP_TAG_CLOSE;
	}

	private String addTurnParameters(Player currentPlayer, boolean availableMainAction, boolean availableQuickAction) {
		StringBuilder turnParametersSend = new StringBuilder();
		turnParametersSend.append(currentPlayer.getName());
		turnParametersSend.append("," + availableMainAction);
		turnParametersSend.append("," + availableQuickAction);
		turnParametersSend.append(",");
		return TURN_PARAMETERS_TAG_OPEN + turnParametersSend + TURN_PARAMETERS_TAG_CLOSE;
	}

	String createUIDynamicContents(StartTurnState currentState) {
		String message = addKingPosition(currentState.getKingPosition());
		message += addFreeCouncillors(currentState.getFreeCouncillors());
		message += addCouncils(currentState.getGroupRegionalCity(), currentState.getKingCouncil());
		message += addBonusTiles(currentState.getGroupRegionalCity(), currentState.getGroupColoredCity(), currentState.getCurrentKingTile());
		message += addPlayerParameters(currentState.getPlayersList());
		message += addPermitTilesUp(currentState.getGroupRegionalCity());
		message += addTurnParameters(currentState.getCurrentPlayer(), currentState.isAvailableMainAction(), currentState.isAvailableQuickAction());
		return DYNAMIC_CONTENT_TAG_OPEN + message + DYNAMIC_CONTENT_TAG_CLOSE;
	}
	
	String createElectCouncillor() {
		return ACTION_TAG_OPEN + ELECT_COUNCILLOR_TAG + ACTION_TAG_CLOSE;
	}
	
	String createEngageAnAssistant() {
		return ACTION_TAG_OPEN + ENGAGE_AN_ASSISTANT_TAG + ACTION_TAG_CLOSE;
	}
	
	String createAcquireBusinessPermitTile() {
		return ACTION_TAG_OPEN + ACQUIRE_BUSINESS_PERMIT_TILE_TAG + ACTION_TAG_CLOSE;
	}
	
	String createChangePermitTiles() {
		return ACTION_TAG_OPEN + CHANGE_PERMIT_TILES_TAG + ACTION_TAG_CLOSE;
	}
	
	String createAssistantToElectCouncillor() {
		return ACTION_TAG_OPEN + ASSISTANT_TO_ELECT_COUNCILLOR_TAG + ACTION_TAG_CLOSE;
	}
	
	String createAdditionalMainAction() {
		return ACTION_TAG_OPEN + ADDITIONAL_MAIN_ACTION_TAG + ACTION_TAG_CLOSE;
	}
	
	String createBuildKingEmpoium() {
		return ACTION_TAG_OPEN + BUILD_EMPORIUM_KING_TAG + ACTION_TAG_CLOSE;
	}
	
	String createBuildPermitTile() {
		return ACTION_TAG_OPEN + BUILD_EMPORIUM_PERMIT_TILE_TAG + ACTION_TAG_CLOSE;
	}
	
	String createMarketOfferPhase() {
		return ACTION_TAG_OPEN + MARKET_OFFER_PHASE_TAG + ACTION_TAG_CLOSE;
	}
	
	String createMarketBuyPhase() {
		return ACTION_TAG_OPEN + MARKET_BUY_PHASE_TAG + ACTION_TAG_CLOSE;
	}
	
	String createSuperBonus() {
		return ACTION_TAG_OPEN + SUPER_BONUS_TAG + ACTION_TAG_CLOSE;
	}

	public String createEndGame() {
		return ACTION_TAG_OPEN + END_GAME_TAG + ACTION_TAG_CLOSE;
	}

}
