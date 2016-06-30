package it.polimi.ingsw.ps23.client.socket;

import java.util.List;

import it.polimi.ingsw.ps23.client.SwingUI;

public class SocketSwingUI extends SwingUI {

	public SocketSwingUI(String mapType, String playerName) {
		super(mapType, playerName);
	}

	public void loadStaticContent(List<String> citiesName, List<List<String>> rewardTokensName, List<List<String>> rewardTokensValue, List<List<String>> stepsName, List<List<String>> stepsValue) {
		addRewardTokens(citiesName, rewardTokensName, rewardTokensValue);
		addNobilityTrackBonuses(stepsName, stepsValue);
	}
	
	public void refreshDynamicContent(String kingPosition, List<String> freeCouncillors) {
		refreshKingPosition(kingPosition);
		refreshFreeCouncillors(freeCouncillors);
	}

}
