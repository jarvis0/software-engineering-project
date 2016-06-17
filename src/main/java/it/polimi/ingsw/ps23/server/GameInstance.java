package it.polimi.ingsw.ps23.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.client.rmi.RMIView;
import it.polimi.ingsw.ps23.server.controller.Controller;
import it.polimi.ingsw.ps23.server.model.Model;
import it.polimi.ingsw.ps23.server.view.SocketConsoleView;
import it.polimi.ingsw.ps23.server.view.SocketView;
import it.polimi.ingsw.ps23.server.view.View;

public class GameInstance {

	private Model model;
	private List<SocketView> socketViews;
	private List<RMIView> rmiViews;
	private Controller controller;
	
	GameInstance() {
		model = new Model();
		socketViews = new ArrayList<>();
		
		controller = new Controller(model);
	}

	List<SocketView> getSocketViews() {
		return socketViews;
	}
	
	void newGame(Map<String, Connection> socketWaitingConnections, Map<String, ClientInterface> rmiWaitingConnections) {
		List<String> socketPlayersName = new ArrayList<>(socketWaitingConnections.keySet());
		// TODO Collections.shuffle(playersName);
		for(int i = 0; i < socketPlayersName.size(); i++) {
			String socketPlayerName = socketPlayersName.get(i);
			Connection connection = socketWaitingConnections.get(socketPlayerName);
			//TODO if(GUI ==> guiview else Console)
			socketViews.add(new SocketConsoleView(socketPlayerName, connection));
			connection.setView(socketViews.get(i));
			model.attach(socketViews.get(i));
			socketViews.get(i).attach(controller);
		}
		List<String> rmiPlayersName = new ArrayList<>(rmiWaitingConnections.keySet());
		for(int i = 0; i < rmiPlayersName.size(); i++) {
			String rmiPlayerName = rmiPlayersName.get(i);
			
		}
		//model.setUpModel(socketPlayersName, new PlayerResumeHandler(socketViews));
		for(Connection connection : socketWaitingConnections.values()) {
			connection.startGame();
		}
		//TODO sendinfomessage ai rmiclients per avvisarli che il game è partito
	}
	
	boolean existsPlayerView(Connection c) {
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

}
