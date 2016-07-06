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
	
	String disconnectSocketPlayer(Connection connection) throws ViewNotFoundException {
		for(GameInstance gameInstance : gameInstances) {
			SocketView socketView = gameInstance.findSocketView(connection);
			if(socketView != null) {
				return gameInstance.disconnectSocketClient(socketView);
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
	
	boolean checkIfAlreadyInGame(String name) {
		for(GameInstance gameInstance : gameInstances) {
			if(gameInstance.isInGame(name)) {
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
	
	void reconnectPlayer(String name, ClientInterface client) {
		if(foundGameInstance != null) {
			foundGameInstance.reconnectPlayer(name, client);
			foundGameInstance = null;
		}
	}

	/**
	 * Checks if a game can end and can be removed from the list of game instances.
	 * @return true if a game has ended.
	 */
	public boolean checkIfEndGame() {
		boolean gameEnded = false;
		for(GameInstance gameInstance : gameInstances) {
			if(gameInstance.checkIfEndGame()) {
				gameInstances.remove(gameInstance);
				gameEnded = true;
			}
		}
		return gameEnded;
	}

}
