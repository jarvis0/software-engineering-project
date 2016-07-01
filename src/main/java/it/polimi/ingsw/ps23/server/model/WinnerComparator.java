package it.polimi.ingsw.ps23.server.model;

import java.util.Comparator;

import it.polimi.ingsw.ps23.server.model.player.Player;

class WinnerComparator implements Comparator<Player> {

	@Override
	public int compare(Player o1, Player o2) {
		if(o1.getVictoryPoints() - o2.getVictoryPoints() == 0) {
			return o1.getAssistants() + o1.getNumberOfPoliticCard() - o2.getAssistants() - o2.getNumberOfPoliticCard();
		}
		else
		{
			return o1.getVictoryPoints() - o2.getVictoryPoints();
		}
	}	
}
