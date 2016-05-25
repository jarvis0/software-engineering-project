package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.NobilityTrack;
import it.polimi.ingsw.ps23.model.bonus.NobilityTrackStep;
import it.polimi.ingsw.ps23.model.map.RewardToken;

public class NobilityTrackFactory {

	private List<NobilityTrackStep> nobilityTrackSteps;

	public NobilityTrackFactory() {
		nobilityTrackSteps = new ArrayList<>();
	}
	
	public NobilityTrack makeNobilityTrack(List<String[]> rawNobilityTrackSteps) {
		String[] fields = rawNobilityTrackSteps.remove(rawNobilityTrackSteps.size() - 1);
		for(String[] rawNobilityTrackStep : rawNobilityTrackSteps) {
			nobilityTrackSteps.add((NobilityTrackStep) new BonusesFactory().makeBonuses(fields, rawNobilityTrackStep, new NobilityTrackStep()));
		}
		return new NobilityTrack(nobilityTrackSteps);
	}
}
