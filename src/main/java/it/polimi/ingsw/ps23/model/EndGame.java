package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

public class EndGame {

	private static final int NOBILITY_TRACK_POINTS_FIRST_PLACE = 5;
	private static final int NOBILITY_TRACK_POINTS_SECOND_PLACE = 2;
	private static final int PERMISSION_CARD_POINTS = 3;
	
	private Game game;
	private TurnHandler turnHandler;
	
	public boolean isGameEnded(Game game, TurnHandler turnHandler) {
		for (Player player : game.getGamePlayersSet().getPlayers()) {
			if (player.hasFinished()) {
				this.game = game;
				this.turnHandler = turnHandler;
				applyFinalBonus();
				return true;
			}
		}
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
		List<Player> firstPlace = new ArrayList<>();
		List<Player> secondPlace = new ArrayList<>();
		for (Player player : game.getGamePlayersSet().getPlayers()) {
			if (!firstPlace.isEmpty()) {
				if(player.getNobilityTrackPoints() >= firstPlace.get(0).getNobilityTrackPoints()) {
					if(player.getNobilityTrackPoints() > firstPlace.get(0).getNobilityTrackPoints()) {
						secondPlace.clear();
						secondPlace.addAll(firstPlace);
						firstPlace.clear();
					}
					firstPlace.add(player);
				}
				else {
					if(!secondPlace.isEmpty()) {
						if(player.getNobilityTrackPoints() >= secondPlace.get(0).getNobilityTrackPoints()) {
							if(player.getNobilityTrackPoints() > secondPlace.get(0).getNobilityTrackPoints()) {
								secondPlace.clear();
							}
							secondPlace.add(player);
						}
					}
					else {
						secondPlace.add(player);
					}
				}
			}
			else {
				firstPlace.add(player);
			}
		}
		if(firstPlace.size() > 1) {
			for(Player player : firstPlace) {
				player.updateVictoryPoints(NOBILITY_TRACK_POINTS_FIRST_PLACE);
			}
		}
		else {
			firstPlace.get(0).updateVictoryPoints(NOBILITY_TRACK_POINTS_FIRST_PLACE);
			for(Player player : secondPlace) {
				player.updateVictoryPoints(NOBILITY_TRACK_POINTS_SECOND_PLACE);
			}
		}
	}

	private void getVictoryPointsForPermissionHandDeck() {
		List<Player> firstPlace = new ArrayList<>();
		for(Player player : game.getGamePlayersSet().getPlayers()) {
			if(!firstPlace.isEmpty()) {
				if(player.getNobilityTrackPoints() > firstPlace.get(0).getNobilityTrackPoints()) {
					firstPlace.clear();
					firstPlace.add(player);
				}
				if(player.getNobilityTrackPoints() == firstPlace.get(0).getNobilityTrackPoints()) {
					firstPlace.add(player);
				}				
			}
			else {
				firstPlace.add(player);
			}
		}
		for(Player player : firstPlace) {
			player.updateVictoryPoints(PERMISSION_CARD_POINTS);
		}
	}
}
