package it.polimi.ingsw.ps23.model.bonus;

import java.util.List;

public class NobilityTrack {
	
	List<NobilityTrackStep> steps;

	public NobilityTrack(List<NobilityTrackStep> steps) {
		this.steps = steps;
	}
	
	@Override
	public String toString() {
		return steps.toString();
	}

}
