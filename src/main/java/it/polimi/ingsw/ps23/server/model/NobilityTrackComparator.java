package it.polimi.ingsw.ps23.server.model;

import java.util.Comparator;

import it.polimi.ingsw.ps23.server.model.player.Player;

class NobilityTrackComparator implements Comparator<Player> {

	@Override
	public int compare(Player o1, Player o2) {
		return o2.getAssistants() - o1.getAssistants();
	}

}
