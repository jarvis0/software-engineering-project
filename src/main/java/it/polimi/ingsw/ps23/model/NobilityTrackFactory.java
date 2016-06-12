package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.NobilityTrack;
import it.polimi.ingsw.ps23.model.map.NobilityTrackStep;

public class NobilityTrackFactory {

	public NobilityTrack makeNobilityTrack(List<String[]> rawNobilityTrackSteps) {
		List<NobilityTrackStep> nobilityTrackSteps = new ArrayList<>();
		String[] fields = rawNobilityTrackSteps.remove(rawNobilityTrackSteps.size() - 1);
		for(String[] rawNobilityTrackStep : rawNobilityTrackSteps) {
			nobilityTrackSteps.add((NobilityTrackStep) new BonusesFactory().makeBonuses(fields, rawNobilityTrackStep, new NobilityTrackStep()));
		}
		return new NobilityTrack(nobilityTrackSteps);
	}
}
