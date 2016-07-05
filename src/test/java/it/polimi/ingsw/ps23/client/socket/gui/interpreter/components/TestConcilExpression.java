package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

public class TestConcilExpression {

	@Test
	public void test() {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		Game game = new Game(players);
		String message = addCouncils(game.getGameMap().getGroupRegionalCity(), game.getKing().getCouncil());
		Expression expression = new TerminalExpression("<councils>", "</councils>");
		CouncilsExpression councilsExpression = new CouncilsExpression(expression);
		councilsExpression.parse(message);
		for(Region region : game.getGameMap().getGroupRegionalCity()) {
			assertTrue(councilsExpression.getCouncilsName().contains(region.getName()));
		}
		assertTrue(councilsExpression.getCouncilsName().contains("kingdom"));
		for(Councillor councillor : game.getKing().getCouncil().getCouncillors()) {
			assertTrue(councilsExpression.getCouncilsColor().get(councilsExpression.getCouncilsColor().size() - 1).contains(councillor.toString()));
		}
		
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
		return "<councils>" + councilsSend + "</councils>";
	}
}
