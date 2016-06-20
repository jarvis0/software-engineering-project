package it.polimi.ingsw.ps23.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.server.controller.ServerController;
import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.model.Model;
import it.polimi.ingsw.ps23.server.model.player.PlayerResumeHandler;
import it.polimi.ingsw.ps23.server.view.SocketConsoleView;
import it.polimi.ingsw.ps23.server.view.SocketView;

public class GameInstance {

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
	
	private List<String> newSocketGame(Map<String, Connection> socketWaitingConnections) {
		List<String> socketPlayersName = new ArrayList<>(socketWaitingConnections.keySet());
		for(int i = 0; i < socketPlayersName.size(); i++) {
			String socketPlayerName = socketPlayersName.get(i);
			Connection connection = socketWaitingConnections.get(socketPlayerName);
			//TODO se GUI ==> guiview se no Console
			createSocketGame(new SocketConsoleView(socketPlayerName, connection), connection);
		}
		return socketPlayersName;
	}
	
	private List<String> newRMIGame(Map<String, ClientInterface> rmiWaitingConnections) {
		List<String> rmiPlayersName = new ArrayList<>(rmiWaitingConnections.keySet());
		if(!rmiPlayersName.isEmpty()) {
			model.setUpRMI(this, rmiTimeout);
			ServerControllerInterface serverControllerStub = controller.setStub();
			for(int i = 0; i < rmiPlayersName.size(); i++) {
				String rmiPlayerName = rmiPlayersName.get(i);
				try {
					ClientInterface remoteClient = rmiWaitingConnections.get(rmiPlayerName);
					remoteClient.setController(serverControllerStub);
					model.attachRMIClient(rmiPlayerName, rmiWaitingConnections.get(rmiPlayerName));
				} catch (RemoteException e) {
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach remote RMI client.", e);
				}
			}
		}
		return rmiPlayersName;
	}

	void newGame(Map<String, Connection> socketWaitingConnections, Map<String, ClientInterface> rmiWaitingConnections) {
		List<String> socketPlayersName = newSocketGame(socketWaitingConnections);
		List<String> rmiPlayersName = newRMIGame(rmiWaitingConnections);
		playersName.addAll(socketPlayersName);
		playersName.addAll(rmiPlayersName);
		//Collections.shuffle(playersName); TODO
		model.setUpModel(playersName, new PlayerResumeHandler(socketViews));//TODO in un altro thread?
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
			gameSocketView.sendNoInput(message);
		}
	}
	
	void disconnectSocketClient(SocketView socketView) {
		String message = "The player " + model.getCurrentPlayer() + " has been disconnected due to connection timeout.";
		socketViews.remove(socketView);
		sendSocketInfoMessage(message);
		model.sendRMIInfoMessage(message);
		model.detach(socketView);
		model.setCurrentPLayerOffline();
	}
	
	public void disconnectRMIClient() {
		String message = "Player " +  model.getCurrentPlayer() + " has been disconnected from the game due to connection timeout.";
		sendSocketInfoMessage(message);
		model.sendRMIInfoMessage(message);
		model.detachRMIClient();
		model.setCurrentPLayerOffline();
	}

	boolean isFormerPlayer(String name) {
		return playersName.contains(name);
	}

	void reconnectPlayer(String name, Connection connection) {
		createSocketGame(new SocketConsoleView(name, connection), connection);
		model.setOnlinePlayer(name);
	}

}
