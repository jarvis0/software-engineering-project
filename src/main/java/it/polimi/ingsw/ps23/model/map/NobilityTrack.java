package it.polimi.ingsw.ps23.model.map;

import java.util.List;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;

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
		return steps.toString();
	}

}
