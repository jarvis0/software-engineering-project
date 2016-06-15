package it.polimi.ingsw.ps23.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.commons.exceptions.ViewNotFoundException;
import it.polimi.ingsw.ps23.server.view.SocketView;

public class GameInstancesSet {

	private List<GameInstance> gameInstances;
	
	GameInstancesSet() {
		gameInstances = new ArrayList<>();
	}

	public void newGame(Map<String, Connection> waitingConnections, Map<String, Connection> playingPlayers) {
		GameInstance gameInstance = new GameInstance();
		gameInstance.newGame(waitingConnections, playingPlayers);
		gameInstances.add(gameInstance);
	}

	private GameInstance findPlayerGameInstance(Connection c) throws ViewNotFoundException {
		for(GameInstance gameInstance : gameInstances) {
			if(gameInstance.existsPlayerView(c)) {
				return gameInstance;
			}
		}
		throw new ViewNotFoundException();
	}
	
	String disconnectPlayer(Connection c) throws ViewNotFoundException {
		String disconnectedPlayer = new String();
		GameInstance gameInstance = findPlayerGameInstance(c);
		List<SocketView> views = gameInstance.getViews();
		Iterator<SocketView> loop = views.iterator();
		boolean found = false;
		while(!found && loop.hasNext()) { //TODO se rimuovo potrei avere problemi a ciclare
			SocketView view = loop.next();
			if(view.getConnection() == c) { //TODO rimuovere questa view da observer di model
				disconnectedPlayer = view.getClientName();
				if(views.size() == 1) {
					view.getConnection().endThread();
				}
				else {
					view.setPlayerOffline();
					view.getConnection().endThread();
				}
				gameInstance.detach(view);
				loop.remove();
				found = true;
			}
		}
		for(SocketView view : views) {
			view.sendNoInput("The player " + disconnectedPlayer + " has disconnected.");
		}
		return disconnectedPlayer;
	}

}
