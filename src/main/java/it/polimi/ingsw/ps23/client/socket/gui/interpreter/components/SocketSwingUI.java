package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import java.util.List;

import it.polimi.ingsw.ps23.client.GUIView;
import it.polimi.ingsw.ps23.client.SwingUI;

/**
 * Creates a bridge to SwingUI and refreshes it starting from strings received via socket.
 * @author Giuseppe Mascellaro
 *
 */
public class SocketSwingUI extends SwingUI {
	
	/**
	 * Constructs the swing UI
	 * @param guiView - socket view useful for receiving and sending updates by the swing UI
	 * @param mapType - map type of the current game
	 * @param playerName - client game player name
	 */
	public SocketSwingUI(GUIView guiView, String mapType, String playerName) {
		super(guiView, mapType, playerName);
	}

	void loadStaticContents(List<String> citiesName, List<List<String>> rewardTokensName, List<List<String>> rewardTokensValue, List<List<String>> stepsName, List<List<String>> stepsValue) {
		addRewardTokens(citiesName, rewardTokensName, rewardTokensValue);
		addNobilityTrackBonuses(stepsName, stepsValue);
	}

	void refreshDynamicContents(KingPositionExpression kingPosition,
			FreeCouncillorsExpression freeCouncillors, CouncilsExpression councils, PermitTilesUpExpression permitTilesUp,
			BonusTilesExpression bonusTiles, PlayersEmporiumsExpression arePlayersEmporiums, PlayersParameterExpression players) {
		refreshKingPosition(kingPosition.getKingPosition());
		refreshFreeCouncillors(freeCouncillors.getFreeCouncillors());
		refreshCouncils(councils.getCouncilsName(), councils.getCouncilsColor());
		refreshBonusTiles(bonusTiles.getGroupsName(), bonusTiles.getGroupsBonusName(), bonusTiles.getGroupsBonusValue(), bonusTiles.getKingBonusName(), bonusTiles.getKingBonusValue());
		refreshPlayersTable(players.getNames(), players.getCoins(), players.getAssistants(), players.getNobilityTrackPoints(), players.getVictoryPoints(), players.getOnline());
		refreshPermitTilesUp(permitTilesUp.getRegions(), permitTilesUp.getPermitTilesCities(), permitTilesUp.getPermitTilesBonusesName(), permitTilesUp.getPermitTilesBonusesValue());
		refreshCitiesToolTip(arePlayersEmporiums.getCitiesName(), arePlayersEmporiums.getPlayersEmporiums());
		refreshGamePlayersPermitTiles(players.getNames(), players.getPermitTilesCities(), players.getPermitTilesBonusesName(), players.getPermitTilesBonusesValue());
		refreshAllPermitTiles(players.getNames(), players.getTotalPermitTilesCities(), players.getTotalPermitTilesBonusesName(), players.getTotalPermitTilesBonusesValue());
		refreshPoliticCards(players.getPoliticCards());
		getFrame().repaint();
		getFrame().revalidate();
	}
	
}
