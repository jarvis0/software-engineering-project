package it.polimi.ingsw.ps23.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.player.Player;

class EndGame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8751948646819316802L;
	private static final int NOBILITY_TRACK_POINTS_FIRST_PLACE = 5;
	private static final int NOBILITY_TRACK_POINTS_SECOND_PLACE = 2;
	private static final int PERMISSION_CARD_POINTS = 3;
	
	private Game game;
	private TurnHandler turnHandler;
	private boolean finishedNobilityPoints;
	
	EndGame(Game game, TurnHandler turnHandler) {
		this.game = game;
		this.turnHandler = turnHandler;
		finishedNobilityPoints = false;
	}
	
	boolean isGameEnded() {
		//for(Player player : game.getGamePlayersSet().getPlayers()) {TODO
			if(game.getCurrentPlayer().hasFinished()) {
				applyFinalBonus();
				return true;
			}
		//}
		return false;
	}
	
	void applyFinalBonus() {
		getTilePoints();
		getVictoryPointsForNobilityTrack();
		getVictoryPointsForPermissionHandDeck();
	}
	
	private void getTilePoints() {
		for (Player player : game.getGamePlayersSet().getPlayers()) {
			game.setCurrentPlayer(player);
			player.getAllTilesPoints(game, turnHandler);
		}
	}
	
	private void getVictoryPointsForNobilityTrack() {
		List<Player> players = new ArrayList<>();
		players.addAll(game.getGamePlayersSet().getPlayers());
		Collections.sort(players, new NobilityTrackComparator());
		players = takeFirstPlace(players);
		if(!finishedNobilityPoints) {
			takeSecondPlace(players);
		}
	}
	
	private List<Player> takeFirstPlace(List<Player> players) {
		int max = players.get(0).getNobilityTrackPoints();
		players.remove(players.size() - 1).updateVictoryPoints(NOBILITY_TRACK_POINTS_FIRST_PLACE);
		for(int i = players.size() - 1; i >= 0; i--) {
			if(players.get(i).getNobilityTrackPoints() == max) {
				players.remove(i).updateVictoryPoints(NOBILITY_TRACK_POINTS_FIRST_PLACE);
				finishedNobilityPoints = true;
			}
			else {
				return players;
			}
		}
		return players;
	}
	
	private void takeSecondPlace(List<Player> players) {
		int max = players.get(0).getNobilityTrackPoints();
		players.remove(players.size() - 1).updateVictoryPoints(NOBILITY_TRACK_POINTS_SECOND_PLACE);
		for(int i = players.size() - 1; i >= 0; i--) {
			if(players.get(i).getNobilityTrackPoints() == max) {
				players.remove(i).updateVictoryPoints(NOBILITY_TRACK_POINTS_SECOND_PLACE);
			}
			else {
				return;
			}
		}
	}

	private void getVictoryPointsForPermissionHandDeck() {
		List<Player> players = new ArrayList<>();
		players.addAll(game.getGamePlayersSet().getPlayers());
		Collections.sort(players, new PermissionTileComparator());
		players.get(0).updateVictoryPoints(PERMISSION_CARD_POINTS);
		int max = players.remove(0).getNumberOfPermitCards();
		for(Player player : players) {
			if(player.getNumberOfPermitCards() < max) {
				return;
			}
			player.updateVictoryPoints(PERMISSION_CARD_POINTS);
		}
	}
	
}
