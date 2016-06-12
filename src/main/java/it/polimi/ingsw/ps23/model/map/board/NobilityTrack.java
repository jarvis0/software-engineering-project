package it.polimi.ingsw.ps23.model.map.board;

import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class NobilityTrack {
	
	List<NobilityTrackStep> steps;

	public NobilityTrack(List<NobilityTrackStep> steps) {
		this.steps = steps;
	}
	
	public void walkOnNobilityTrack(int initialNobilityTrackPoints, int finalNobilityTrackPoints, Game game, TurnHandler turnHandler) {
		for(int position = initialNobilityTrackPoints+1; position <= finalNobilityTrackPoints; position++) {
			steps.get(position).useBonus(game, turnHandler);
		}	
	}
	
	@Override
	public String toString() {
		StringBuilder loopPrint = new StringBuilder();
		for(NobilityTrackStep step : steps) {
			loopPrint.append("\n\t- " + step.toString());
		}
		String print = new String();
		print += loopPrint;
		return print;
	}

}
