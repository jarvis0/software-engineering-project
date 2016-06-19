package it.polimi.ingsw.ps23.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.server.commons.exceptions.ViewNotFoundException;
import it.polimi.ingsw.ps23.server.view.SocketView;

class GameInstancesSet {

	private List<GameInstance> gameInstances;
	
	private int timeout;
	
	GameInstancesSet(int timeout) {
		gameInstances = new ArrayList<>();
		this.timeout = timeout;
	}

	void newGame(Map<String, Connection> socketWaitingConnections, Map<String, ClientInterface> rmiWaitingConnections) {
		GameInstance gameInstance = new GameInstance();
		if(!rmiWaitingConnections.isEmpty()) {
			gameInstance.setRMITimeout(timeout);
		}
		gameInstance.newGame(socketWaitingConnections, rmiWaitingConnections);
		gameInstances.add(gameInstance);
	}

	private GameInstance findSocketPlayerGame(Connection c) throws ViewNotFoundException {
		for(GameInstance gameInstance : gameInstances) {
			if(gameInstance.existsSocketPlayerView(c)) {
				return gameInstance;
			}
		}
		throw new ViewNotFoundException();
	}
	
	private void notifyDisconnectionToRMI(GameInstance gameInstance, String message) {
		gameInstance.sendRMIMessage(message);
	}
	
	String disconnectSocketPlayer(Connection c) throws ViewNotFoundException {
		String disconnectedPlayer = new String();
		GameInstance gameInstance = findSocketPlayerGame(c);
		List<SocketView> views = gameInstance.getSocketViews();
		Iterator<SocketView> loop = views.iterator();
		boolean found = false;
		while(!found && loop.hasNext()) {
			SocketView view = loop.next();
			if(view.getConnection() == c) {
				disconnectedPlayer = view.getClientName();
				view.setPlayerOffline();//TODO almeno 2 giocatori
				gameInstance.socketDetach(view);
				loop.remove();
				found = true;
			}
		}
		String message = "The player " + disconnectedPlayer + " has been disconnected due to connection timeout.";
		for(SocketView view : views) {
			view.sendNoInput(message);
		}
		notifyDisconnectionToRMI(gameInstance, message);
		return disconnectedPlayer;
	}

}
