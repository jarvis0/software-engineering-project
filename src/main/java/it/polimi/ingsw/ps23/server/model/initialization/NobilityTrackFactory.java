package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrackStep;

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
