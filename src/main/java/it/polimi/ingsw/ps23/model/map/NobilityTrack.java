package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class NobilityTrack {
	
	List<NobilityTrackStep> steps;

	public NobilityTrack(List<NobilityTrackStep> steps) {
		this.steps = steps;
	}
	
	public void walkOnNobilityTrack(int initialNobilityTrackPoints, int finalNobilityTrackPoints, Player player, TurnHandler turnHandler) {
		
		for(int position = initialNobilityTrackPoints+1; position <= finalNobilityTrackPoints; position++) {
			steps.get(position).useBonus(player, turnHandler);
		}
	}
	
	@Override
	public String toString() {
		return steps.toString();
	}

}
