package it.polimi.ingsw.ps23.server.model;

import java.util.Comparator;

import it.polimi.ingsw.ps23.server.model.player.Player;

public class PermissionTileComparator implements Comparator<Player> {

	@Override
	public int compare(Player o1, Player o2) {
		return o1.getNumberOfPermissionCard() - o2.getNumberOfPermissionCard();
	}	
}
