package it.polimi.ingsw.ps23.model.map;

import java.util.List;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class NobilityTrack {
	
	List<NobilityTrackStep> steps;

	public NobilityTrack(List<NobilityTrackStep> steps) {
		this.steps = steps;
	}
	
	public void takeBonus(int index, Player player, TurnHandler turnHandler) {
		steps.get(index).useBonus(player, turnHandler);
	}
	
	@Override
	public String toString() {
		return steps.toString();
	}

}
