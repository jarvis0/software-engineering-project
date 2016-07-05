package it.polimi.ingsw.ps23.server.view;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrackStep;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.state.Context;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;

public class TestSocketParametersCreator {

	@Test
	public void test() {
		SocketParametersCreator creator = new SocketParametersCreator();
		TurnHandler turnHandler = new TurnHandler();
		StartTurnState state = new StartTurnState(turnHandler);
		List<String> playerNames = new ArrayList<>();
		playerNames.add("a");
		playerNames.add("b");
		Game game = new Game(playerNames);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayer(0));
		state.changeState(new Context(), game);
		String content = creator.createUIDynamicContents(state);
		assertTrue(content.contains("<dynamic_content>") && content.contains("</dynamic_content>"));
		assertTrue(content.substring(content.indexOf("<king_position>"), content.indexOf("</king_position>")).contains(game.getKing().getPosition().getName()));
		for(Councillor councillor : game.getFreeCouncillors().getFreeCouncillorsList()) {
			assertTrue(content.substring(content.indexOf("<free_councillors>"), content.indexOf("</free_councillors>")).contains(councillor.toString()));
		}
		int input = Character.getNumericValue(content.substring(content.indexOf("<councils>"), content.indexOf("</councils>")).charAt(0 + "<councils>".length()));
		assertTrue(input == game.getGameMap().getGroupRegionalCity().size());
		for(Region region : game.getGameMap().getGroupRegionalCity()) {
			for(Councillor councillor : ((GroupRegionalCity)region).getCouncil().getCouncillors()) {
				assertTrue(content.substring(content.indexOf("<councils>"), content.indexOf("</councils>")).contains(councillor.toString()));
			}
		}
		assertTrue(content.substring(content.indexOf("<players_parameters>"), content.indexOf("</players_parameters>")).contains(playerNames.get(0)));
		assertTrue(content.substring(content.indexOf("<players_parameters>"), content.indexOf("</players_parameters>")).contains(playerNames.get(1)));
		for(Card card : game.getGamePlayersSet().getPlayer(0).getPoliticHandDeck().getCards()) {
			assertTrue(content.substring(content.indexOf("<players_parameters>"), content.indexOf("</players_parameters>")).contains(((PoliticCard)card).toString()));
		}
		content = creator.createUIStaticContents(game.getGameMap().getCities(), game.getNobilityTrack());
		System.out.println(content);
		assertTrue(content.contains("<static_content>") && content.contains("</static_content>"));
		Iterator<City> iterator = game.getGameMap().getCities().values().iterator();
		while(iterator.hasNext()) {
			City city = iterator.next();
			if(!(city instanceof CapitalCity)) {
				for(Bonus bonus : ((NormalCity)city).getRewardToken().getBonuses()) {
					assertTrue(content.substring(content.indexOf("<reward_tokens>"), content.indexOf("</reward_tokens>")).contains(bonus.getName() + "," + bonus.getValue()));
				}
			}
		}
		for(NobilityTrackStep step : game.getNobilityTrack().getSteps()) {
			for(Bonus bonus : step.getBonuses()) {
				assertTrue(content.substring(content.indexOf("<nobility_track>"), content.indexOf("</nobility_track>")).contains(bonus.getName() + "," + bonus.getValue()));
			}
		}
		assertTrue(creator.createElectCouncillor().contains("elect_councillor"));
		assertTrue(creator.createEngageAnAssistant().contains("engage_an_assistant"));
		assertTrue(creator.createAcquireBusinessPermitTile().contains("acquire_business_permit_tile"));
		assertTrue(creator.createChangePermitTiles().contains("change_permit_tiles"));
		assertTrue(creator.createAssistantToElectCouncillor().contains("assistant_to_elect_councillor"));
		assertTrue(creator.createAdditionalMainAction().contains("additional_main_action"));
		assertTrue(creator.createBuildKingEmpoium().contains("build_emporium_king"));
		assertTrue(creator.createBuildPermitTile().contains("build_emporium_permit_tile"));
		assertTrue(creator.createMarketOfferPhase().contains("market_offer_phase"));
		assertTrue(creator.createMarketBuyPhase().contains("market_buy_phase"));
		assertTrue(creator.createSuperBonus().contains("super_bonus"));
		assertTrue(creator.createEndGame().contains("end_game"));
		
}

}
