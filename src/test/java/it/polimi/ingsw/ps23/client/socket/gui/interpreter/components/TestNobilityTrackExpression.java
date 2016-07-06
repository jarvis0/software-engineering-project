package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.RealBonus;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrackStep;
/**
 * Tests if the correct info are obtained from a message after {@link NobilityTrackExpression} parsing.
 * @author Mirco Manzoni
 *
 */
public class TestNobilityTrackExpression {

	@Test
	public void test() {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		Game game = new Game(players);
		String message = addNobilityTrackSteps(game.getNobilityTrack().getSteps());
		NobilityTrackExpression nobilityTrackExpression = new NobilityTrackExpression(new TerminalExpression("<nobility_track>","</nobility_track>"));
		nobilityTrackExpression.parse(message);
		for(NobilityTrackStep step : game.getNobilityTrack().getSteps()) {
			for(Bonus bonus : step.getBonuses()) {
				assertTrue(nobilityTrackExpression.getStepsName().toString().contains(bonus.getName()));
				if(!bonus.isNull())
				assertTrue(nobilityTrackExpression.getStepsValue().toString().contains(String.valueOf(((RealBonus)bonus).getValue())));
			}
		}
	}

	private String addNobilityTrackSteps(List<NobilityTrackStep> steps) {
		StringBuilder nobilityTrackSend = new StringBuilder();
		for(NobilityTrackStep step : steps) {
			addBonuses(nobilityTrackSend, step.getBonuses());
			nobilityTrackSend.append(",");
		}
		return "<nobility_track>" + nobilityTrackSend + "</nobility_track>";
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
