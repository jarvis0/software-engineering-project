package it.polimi.ingsw.ps23.client.socket.gui;

import java.util.List;

import it.polimi.ingsw.ps23.client.SwingUI;

public class SocketSwingUI extends SwingUI {

	public SocketSwingUI(String mapType, String playerName) {
		super(mapType, playerName);
	}

	void loadStaticContents(List<String> citiesName, List<List<String>> rewardTokensName, List<List<String>> rewardTokensValue, List<List<String>> stepsName, List<List<String>> stepsValue) {
		addRewardTokens(citiesName, rewardTokensName, rewardTokensValue);
		addNobilityTrackBonuses(stepsName, stepsValue);
	}
	
	void refreshDynamicContents(String kingPosition, List<String> freeCouncillors, List<String> councilsName, List<List<String>> councilsColor, List<String> groupsName,
			List<String> groupsBonusName, List<String> groupsBonusValue, String kingBonusName, String kingBonusValue) {
		refreshKingPosition(kingPosition);
		refreshFreeCouncillors(freeCouncillors);
		refreshCouncils(councilsName, councilsColor);
		refreshBonusTiles(groupsName, groupsBonusName, groupsBonusValue, kingBonusName, kingBonusValue);
	}

}
