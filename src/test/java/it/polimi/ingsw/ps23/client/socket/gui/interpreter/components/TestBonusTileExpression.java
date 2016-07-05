package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Region;

public class TestBonusTileExpression {

	@Test
	public void test() {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		Game game = new Game(players);
		String message = addBonusTiles(game.getGameMap().getGroupRegionalCity(), game.getGameMap().getGroupRegionalCity(), game.getKingTilesSet().getCurrentTile());
		Expression expression = new TerminalExpression("<bonus_tiles>", "</bonus_tiles>");
		BonusTilesExpression bonusTilesExpression = new BonusTilesExpression(expression);
		bonusTilesExpression.parse(message);
		for(Region region : game.getGameMap().getGroupRegionalCity()) {
			assertTrue(bonusTilesExpression.getGroupsName().contains(region.getName()));
			assertTrue(bonusTilesExpression.getGroupsBonusName().contains(region.getBonusTile().getName()));
			assertTrue(bonusTilesExpression.getGroupsBonusValue().contains(String.valueOf(region.getBonusTile().getValue())));
		}
		assertTrue(bonusTilesExpression.getKingBonusName().equals(game.getKingTilesSet().getCurrentTile().getName()));
		assertTrue(bonusTilesExpression.getKingBonusValue().equals(String.valueOf(game.getKingTilesSet().getCurrentTile().getValue())));
		
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
		return "<bonus_tiles>" + bonusTilesSend + "</bonus_tiles>";
	}

}
