package it.polimi.ingsw.ps23.model;

import java.util.Comparator;

public class PermissionTileComparator implements Comparator<Player> {

	@Override
	public int compare(Player o1, Player o2) {
		return o1.getNumberOfPermissionCard() - o2.getNumberOfPermissionCard();
	}	
}
