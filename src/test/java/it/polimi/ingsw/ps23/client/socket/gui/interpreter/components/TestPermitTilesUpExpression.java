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
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.BusinessPermitTile;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
/**
 * Tests if the correct info are obtained from a message after {@link PermitTileUpExpression} parsing.
 * @author Mirco Manzoni
 *
 */
public class TestPermitTilesUpExpression {

	@Test
	public void test() {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		Game game = new Game(players);
		String message = addPermitTilesUp(game.getGameMap().getGroupRegionalCity());
		PermitTilesUpExpression permitTilesUpExpression = new PermitTilesUpExpression(new TerminalExpression("<permit_tiles_up>", "</permit_tiles_up>"));
		permitTilesUpExpression.parse(message);
		for(Region region : game.getGameMap().getGroupRegionalCity()) {
			assertTrue(permitTilesUpExpression.getRegions().contains(region.getName()));
			for(Card card : ((GroupRegionalCity)region).getPermitTilesUp().getCards()) {
				for(City city : ((BusinessPermitTile)card).getCities()) {
					assertTrue(permitTilesUpExpression.getPermitTilesCities().toString().contains(String.valueOf(city.getName().charAt(0))));
				}				
				for(Bonus bonus : ((BusinessPermitTile)card).getBonuses()) {
					assertTrue(permitTilesUpExpression.getPermitTilesBonusesName().toString().contains(bonus.getName()));
					assertTrue(permitTilesUpExpression.getPermitTilesBonusesValue().toString().contains(String.valueOf(((RealBonus)bonus).getValue())));
				}
			}
		}
		
	}
	
	private String addPermitTilesUp(List<Region> groupRegionalCity) {
		StringBuilder permitTilesUpSend = new StringBuilder();
		permitTilesUpSend.append(groupRegionalCity.size());
		for(Region region : groupRegionalCity) {
			permitTilesUpSend.append("," + region.getName());
			addPermitHandDeck(permitTilesUpSend, ((GroupRegionalCity) region).getPermitTilesUp().getCards());
		}
		permitTilesUpSend.append(",");
		return "<permit_tiles_up>" + permitTilesUpSend + "</permit_tiles_up>";
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
			bonusesSend.append("," + ((RealBonus)bonus).getValue());
		}
	}

}
