package it.polimi.ingsw.ps23.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.server.controller.ServerController;
import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.model.Model;
import it.polimi.ingsw.ps23.server.model.PlayersResumeHandler;
import it.polimi.ingsw.ps23.server.view.SocketConsoleView;
import it.polimi.ingsw.ps23.server.view.SocketGUIView;
import it.polimi.ingsw.ps23.server.view.SocketView;

/**
 * Provides a compact way to handle both socket and RMI game
 * instance.
 * <p>
 * Constructs the game and the MVC objects to make
 * the game work.
 * @author Giuseppe Mascellaro
 *
 */
public class GameInstance {
	
	private static final String MAP_TYPE_TAG_OPEN = "<map_type>";
	private static final String MAP_TYPE_TAG_CLOSE = "</map_type>";
	
	private static final String PLAYER_PRINT = "Player ";
	
	private Model model;
	private List<SocketView> socketViews;
	private ServerController controller;
	private List<String> playersName;
	
	private int rmiTimeout;
	
	GameInstance() {
		model = new Model();
		socketViews = new ArrayList<>();
		controller = new ServerController(model);
		playersName = new ArrayList<>();
	}

	void setRMITimeout(int rmiTimeout) {
		this.rmiTimeout = rmiTimeout;
	}
	
	List<SocketView> getSocketViews() {
		return socketViews;
	}
	
	private void createSocketGame(SocketView socketView, Connection connection) {
		socketViews.add(socketView);
		connection.setSocketView(socketView);
		model.attach(socketView);
		socketView.attach(controller);
	}
	
	private void createSocketView(Connection connection, String socketPlayerName) {
		if(connection.isConsole()) {
			createSocketGame(new SocketConsoleView(socketPlayerName, connection), connection);
		}
		else {
			createSocketGame(new SocketGUIView(socketPlayerName, connection), connection);
		}
	}
	
	private List<String> newSocketGame(Map<String, Connection> socketWaitingConnections) {
		List<String> socketPlayersName = new ArrayList<>(socketWaitingConnections.keySet());
		for(int i = 0; i < socketPlayersName.size(); i++) {
			String socketPlayerName = socketPlayersName.get(i);
			Connection connection = socketWaitingConnections.get(socketPlayerName);
			createSocketView(connection, socketPlayerName);
		}
		return socketPlayersName;
	}

	private void createRMIGame(String rmiPlayerName, ClientInterface client, ServerControllerInterface serverControllerStub) {
		try {
			ClientInterface remoteClient = client;
			remoteClient.setController(serverControllerStub);
			model.attachRMIClient(rmiPlayerName, client);
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach remote RMI client.", e);
		}
	}

	private List<String> newRMIGame(Map<String, ClientInterface> rmiWaitingConnections) {
		List<String> rmiPlayersName = new ArrayList<>(rmiWaitingConnections.keySet());
		if(!rmiPlayersName.isEmpty()) {
			model.setUpRMI(this, rmiTimeout);
			ServerControllerInterface serverControllerStub = controller.setStub();
			for(int i = 0; i < rmiPlayersName.size(); i++) {
				String rmiPlayerName = rmiPlayersName.get(i);
				createRMIGame(rmiPlayerName, rmiWaitingConnections.get(rmiPlayerName), serverControllerStub);
			}
		}
		return rmiPlayersName;
	}

	void newGame(Map<String, Connection> socketWaitingConnections, Map<String, ClientInterface> rmiWaitingConnections) {
		List<String> socketPlayersName = newSocketGame(socketWaitingConnections);
		List<String> rmiPlayersName = newRMIGame(rmiWaitingConnections);
		playersName.addAll(socketPlayersName);
		playersName.addAll(rmiPlayersName);
		Collections.shuffle(playersName);
		model.setUpModel(playersName, new PlayersResumeHandler(socketViews));
		String mapType = MAP_TYPE_TAG_OPEN + model.getMapType() + MAP_TYPE_TAG_CLOSE;
		model.sendRMIInfoMessage(mapType);
		sendSocketInfoMessage(mapType);
		model.startGame();
		for(Connection connection : socketWaitingConnections.values()) {
			connection.startGame();
		}
	}

	SocketView findSocketView(Connection connection) {
		for(SocketView socketView : socketViews) {
			if(socketView.getConnection() == connection) {
				return socketView;
			}
		}
		return null;
	}

	private void sendSocketInfoMessage(String message) {
		for(SocketView gameSocketView : socketViews) {
			gameSocketView.getConnection().sendNoInput(message);
		}
	}
	
	String disconnectSocketClient(SocketView socketView) {
		String currentPlayer = model.getCurrentPlayer();
		String message = "The player " + currentPlayer + " has been disconnected due to connection timeout.";
		socketViews.remove(socketView);
		sendSocketInfoMessage(message);
		model.sendRMIInfoMessage(message);
		model.detach(socketView);
		model.setCurrentPlayerOffline();
		return currentPlayer;
	}
	
	/**
	 * Provides all procedures to kick-out a RMI client from the game
	 * due to connection timeout.
	 * <p>
	 * Notifies to the other game socket and RMI clients that the
	 * specified client has been kicked-out.
	 * <p>
	 * Sets the specified player offline to make him reconnect and
	 * resume the same game.
	 * @param client
	 */
	public void disconnectRMIClient(ClientInterface client) {
		String message = PLAYER_PRINT +  model.getCurrentPlayer() + " has been disconnected from the game due to connection timeout.";
		System.out.println(message);
		sendSocketInfoMessage(message);
		try {
			client.infoMessage("You have been disconnected from the game due to connection timeout.");
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach the RMI remote client.", e);
		}
		model.detachRMIClient();
		model.sendRMIInfoMessage(message);
		model.setCurrentPlayerOffline();
	}

	boolean isInGame(String name) {
		return playersName.contains(name) && model.isOnline(name);
	}

	boolean isFormerPlayer(String name) {
		return playersName.contains(name) && !model.isOnline(name);
	}

	void reconnectPlayer(String name, Connection connection) {
		String message = PLAYER_PRINT + name + " has been reconnected to the game.";
		for(SocketView gameSocketView : socketViews) {
			gameSocketView.getConnection().sendNoInput(message);
		}
		model.sendRMIInfoMessage(message);
		createSocketView(connection, name);
		String mapType = MAP_TYPE_TAG_OPEN + model.getMapType() + MAP_TYPE_TAG_CLOSE;//TODO anche per RMI
		connection.sendNoInput(mapType);
		model.setOnlinePlayer(name);
		connection.setReconnected();
	}
	
	void reconnectPlayer(String name, ClientInterface client) {
		model.sendRMIInfoMessage(PLAYER_PRINT + name + " has been reconnected to the game.");
		createRMIGame(name, client, controller);
		model.setOnlinePlayer(name);
	}

	boolean checkIfEndGame() {
		return model.getEndGame();
	}

}
