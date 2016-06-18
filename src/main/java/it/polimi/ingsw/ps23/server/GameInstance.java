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
import it.polimi.ingsw.ps23.server.view.View;

public class GameInstance {

	private Model model;
	private List<SocketView> socketViews;
	private ServerController controller;
	
	GameInstance() {
		model = new Model();
		socketViews = new ArrayList<>();
		controller = new ServerController(model);
	}

	List<SocketView> getSocketViews() {
		return socketViews;
	}
	
	void newGame(Map<String, Connection> socketWaitingConnections, Map<String, ClientInterface> rmiWaitingConnections) {
		List<String> socketPlayersName = new ArrayList<>(socketWaitingConnections.keySet());
		for(int i = 0; i < socketPlayersName.size(); i++) {
			String socketPlayerName = socketPlayersName.get(i);
			Connection connection = socketWaitingConnections.get(socketPlayerName);
			//TODO if(GUI ==> guiview else Console)
			socketViews.add(new SocketConsoleView(socketPlayerName, connection));
			connection.setSocketView(socketViews.get(i));
			model.attach(socketViews.get(i));
			socketViews.get(i).attach(controller);
		}
		List<String> rmiPlayersName = new ArrayList<>(rmiWaitingConnections.keySet());
		if(!rmiPlayersName.isEmpty()) {
			ServerControllerInterface serverControllerStub = controller.setStub();
			for(int i = 0; i < rmiPlayersName.size(); i++) {
				String rmiPlayerName = rmiPlayersName.get(i);
				try {
					ClientInterface remoteClient = rmiWaitingConnections.get(rmiPlayerName);
					remoteClient.setController(serverControllerStub);
					model.attachStub(rmiWaitingConnections.get(rmiPlayerName));
				} catch (RemoteException e) {
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach remote RMI client.", e);
				}
			}
		}
		List<String> playersName = new ArrayList<>();
		playersName.addAll(socketPlayersName);
		playersName.addAll(rmiPlayersName);
		//Collections.shuffle(playersName); TODO remove the comment slashes
		model.setUpModel(playersName, new PlayerResumeHandler(socketViews));
		for(Connection connection : socketWaitingConnections.values()) {
			connection.startGame();
		}
	}
	
	boolean existsSocketPlayerView(Connection c) {
		for(SocketView view : socketViews) {
			if(view.getConnection() == c) {
				return true;
			}
		}
		return false;
	}

	void detach(View view) {
		model.detach(view);
	}

	void sendRMIMessage(String message) {
		model.sendRMIInfoMessage(message);
	}

}
