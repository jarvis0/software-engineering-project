package it.polimi.ingsw.ps23.model;

import java.util.Comparator;

public class NobilityTrackComparator implements Comparator<Player>{

	@Override
	public int compare(Player o1, Player o2) {
		return o2.getAssistants() - o1.getAssistants();
	}

}
