package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrackStep;

class NobilityTrackFactory {

	NobilityTrack makeNobilityTrack(List<String[]> rawNobilityTrackSteps, BonusCache bonusCache) {
		List<NobilityTrackStep> nobilityTrackSteps = new ArrayList<>();
		String[] fields = rawNobilityTrackSteps.remove(rawNobilityTrackSteps.size() - 1);
		for(String[] rawNobilityTrackStep : rawNobilityTrackSteps) {
			nobilityTrackSteps.add((NobilityTrackStep) new BonusesFactory(bonusCache).makeBonuses(fields, rawNobilityTrackStep, new NobilityTrackStep()));
		}
		return new NobilityTrack(nobilityTrackSteps);
	}
}
