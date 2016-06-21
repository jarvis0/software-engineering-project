package it.polimi.ingsw.ps23.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.server.commons.exceptions.ViewNotFoundException;
import it.polimi.ingsw.ps23.server.view.SocketView;

class GameInstancesSet {

	private List<GameInstance> gameInstances;
	private GameInstance foundGameInstance;
	
	private int timeout;
	
	GameInstancesSet(int timeout) {
		gameInstances = new ArrayList<>();
		foundGameInstance = null;
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
	
	void disconnectSocketPlayer(Connection connection) throws ViewNotFoundException {
		for(GameInstance gameInstance : gameInstances) {
			SocketView socketView = gameInstance.findSocketView(connection);
			if(socketView != null) {
				gameInstance.disconnectSocketClient(socketView);
				return;
			}
		}
		throw new ViewNotFoundException();
	}
	
	boolean checkIfFormerPlayer(String name) {
		for(GameInstance gameInstance : gameInstances) {
			if(gameInstance.isFormerPlayer(name)) {
				foundGameInstance = gameInstance;
				return true;
			}
		}
		return false;
	}
	
	void reconnectPlayer(String name, Connection connection) {
		if(foundGameInstance != null) {
			foundGameInstance.reconnectPlayer(name, connection);
			foundGameInstance = null;
		}
	}

}
