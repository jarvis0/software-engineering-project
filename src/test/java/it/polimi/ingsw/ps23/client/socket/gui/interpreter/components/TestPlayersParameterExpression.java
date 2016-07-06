package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.RealBonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.BusinessPermitTile;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.player.Player;
/**
  * Tests if the correct info are obtained from a message after {@link PlayerParameterExpression} parsing.
 * @author Mirco Manzoni
 *
 */
public class TestPlayersParameterExpression {

	@Test
	public void test() {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		Game game = new Game(players);
		BusinessPermitTile permitCard = (BusinessPermitTile) ((GroupRegionalCity)game.getGameMap().getGroupRegionalCity().get(0)).getPermitTilesUp().getCards().get(0);
		List<Card> cards = new ArrayList<>();
		cards.add(permitCard);
		game.getGamePlayersSet().getPlayer(0).buyPermitCards(cards);
		String message = addPlayerParameters(game.getGamePlayersSet().getPlayers());
		PlayersParameterExpression playersParameterExpression = new PlayersParameterExpression(new TerminalExpression("<players_parameters>", "</players_parameters>"));
		playersParameterExpression.parse(message);
		assertTrue(playersParameterExpression.getNames().contains(players.get(0)));
		assertTrue(playersParameterExpression.getCoins().contains(String.valueOf(game.getGamePlayersSet().getPlayer(0).getCoins())));
		assertTrue(playersParameterExpression.getAssistants().contains(String.valueOf(game.getGamePlayersSet().getPlayer(0).getAssistants())));
		assertTrue(playersParameterExpression.getVictoryPoints().contains(String.valueOf(game.getGamePlayersSet().getPlayer(0).getVictoryPoints())));
		assertTrue(playersParameterExpression.getNobilityTrackPoints().contains(String.valueOf(game.getGamePlayersSet().getPlayer(0).getNobilityTrackPoints())));
		for(Card card : game.getGamePlayersSet().getPlayer(0).getPoliticHandDeck().getCards()) {
			assertTrue(playersParameterExpression.getPoliticCards().toString().contains(((PoliticCard)card).toString()));
		}
		assertTrue(playersParameterExpression.getOnline().get(0).equals(String.valueOf(game.getGamePlayersSet().getPlayer(0).isOnline())));
		for(City city : permitCard.getCities()) {
			assertTrue(playersParameterExpression.getTotalPermitTilesCities().toString().contains(String.valueOf(city.toString().charAt(0))));
		}
		for(Bonus bonus : permitCard.getBonuses()) {
			assertTrue(playersParameterExpression.getTotalPermitTilesBonusesName().toString().contains(String.valueOf(bonus.getName())));
			assertTrue(playersParameterExpression.getTotalPermitTilesBonusesValue().toString().contains(String.valueOf(((RealBonus)bonus).getValue())));
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
			addPermitHandDeck(playersParameterSend, player.getAllPermitHandDeck().getCards());
			addPoliticHandDeck(playersParameterSend, player.getPoliticHandDeck().getCards());
			playersParameterSend.append("," + player.isOnline());
		}
		playersParameterSend.append(",");
		return "<players_parameters>" + playersParameterSend + "</players_parameters>";
	}

	private void addPoliticHandDeck(StringBuilder playersParameterSend, List<Card> cards) {
		playersParameterSend.append("," + cards.size());
		for(Card politicCard : cards) {
			playersParameterSend.append("," + ((PoliticCard) politicCard).getColor().toString());
		}
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
	
	private void addBonuses(StringBuilder bonusesSend, List<Bonus> bonuses) {
		int bonusesNumber = bonuses.size();
		bonusesSend.append(bonusesNumber);
		for(Bonus bonus : bonuses) {
			bonusesSend.append("," + bonus.getName());
			if(!bonus.isNull()) {
				bonusesSend.append("," + ((RealBonus)bonus).getValue());
			} else {
				bonusesSend.append("," + 0);
			}
		}
	}
}
