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

	void refreshDynamicContents(KingPositionExpression kingPosition,
			FreeCouncillorsExpression freeCouncillors, CouncilsExpression councils, PermitTilesUpExpression permitTilesUp,
			BonusTilesExpression bonusTiles, PlayersParameterExpression players) {
		refreshKingPosition(kingPosition.getKingPosition());
		refreshFreeCouncillors(freeCouncillors.getFreeCouncillors());
		refreshCouncils(councils.getCouncilsName(), councils.getCouncilsColor());
		this.refreshPermitTilesUp(permitTilesUp.getRegions(), permitTilesUp.getPermitTilesCities(), permitTilesUp.getPermitTilesBonusesName(), permitTilesUp.getPermitTilesBonusesValue());
		refreshBonusTiles(bonusTiles.getGroupsName(), bonusTiles.getGroupsBonusName(), bonusTiles.getGroupsBonusValue(), bonusTiles.getKingBonusName(), bonusTiles.getKingBonusValue());
		refreshPlayersTable(players.getNames(), players.getCoins(), players.getAssistants(), players.getNobilityTrackPoints(), players.getVictoryPoints());
		refreshPoliticCards(players.getPoliticCards());
		getFrame().repaint();
		getFrame().revalidate();
	}
}
