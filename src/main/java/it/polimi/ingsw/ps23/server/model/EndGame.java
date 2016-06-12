package it.polimi.ingsw.ps23.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps23.model.player.Player;

public class EndGame {

	private static final int NOBILITY_TRACK_POINTS_FIRST_PLACE = 5;
	private static final int NOBILITY_TRACK_POINTS_SECOND_PLACE = 2;
	private static final int PERMISSION_CARD_POINTS = 3;
	
	private Game game;
	private TurnHandler turnHandler;
	
	public boolean isGameEnded(Game game, TurnHandler turnHandler) {
		//for(Player player : game.getGamePlayersSet().getPlayers()) {
			if(game.getCurrentPlayer().hasFinished()) {
				this.game = game;
				this.turnHandler = turnHandler;
				applyFinalBonus();
				return true;
			}
		//}
		return false;
	}
	
	private void applyFinalBonus() {
		getTilePoints();
		getVictoryPointsForNobilityTrack();
		getVictoryPointsForPermissionHandDeck();
	}
	
	private void getTilePoints() {
		for (Player player : game.getGamePlayersSet().getPlayers()) {
			game.setCurrentPlayer(player);
			player.getAllTilePoints(game, turnHandler);
		}
	}
	
	private void getVictoryPointsForNobilityTrack() {
		List<Player> players = new ArrayList<>();
		players.addAll(game.getGamePlayersSet().getPlayers());
		Collections.sort(players, new NobilityTrackComparator());
		players = takeFirstPlace(players);
		takeSecondPlace(players);
	}
	
	private List<Player> takeFirstPlace(List<Player> players) {
		int max = players.get(0).getAssistants();
		players.remove(players.size() - 1).updateVictoryPoints(NOBILITY_TRACK_POINTS_FIRST_PLACE);
		for(int i = players.size(); i >= 0; i--) {
			if(players.get(i).getAssistants() == max) {
				players.remove(i).updateVictoryPoints(NOBILITY_TRACK_POINTS_FIRST_PLACE);
			}
			else {
				return players;
			}
		}
		return players;
	}
	
	private void takeSecondPlace(List<Player> players) {
		int max = players.get(0).getAssistants();
		players.remove(players.size() - 1).updateVictoryPoints(NOBILITY_TRACK_POINTS_SECOND_PLACE);
		for(int i = players.size(); i >= 0; i--) {
			if(players.get(i).getAssistants() == max) {
				players.remove(i).updateVictoryPoints(NOBILITY_TRACK_POINTS_SECOND_PLACE);
			}
			else {
				return;
			}
		}
	}

	private void getVictoryPointsForPermissionHandDeck() {
		List<Player> players = new ArrayList<>();
		Collections.sort(players, new PermissionTileComparator());
		players.get(0).updateVictoryPoints(PERMISSION_CARD_POINTS);
		int max = players.remove(0).getNumberOfPermissionCard();
		for(Player player : game.getGamePlayersSet().getPlayers()) {
			if(player.getNumberOfPermissionCard() < max) {
				return;
			}
			player.updateVictoryPoints(PERMISSION_CARD_POINTS);
		}
	}
}
