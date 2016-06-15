package it.polimi.ingsw.ps23.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.controller.Controller;
import it.polimi.ingsw.ps23.server.model.Model;
import it.polimi.ingsw.ps23.server.model.player.PlayerResumeHandler;
import it.polimi.ingsw.ps23.server.view.View;

public class GameInstance {

	private Model model;
	private List<View> views;
	private Controller controller;
	
	GameInstance() {
		model = new Model();
		views = new ArrayList<>();
		controller = new Controller(model);
	}

	List<View> getViews() {
		return views;
	}
	
	void newGame(Map<String, Connection> waitingConnections, Map<String, Connection> playingPlayers) {
		List<String> playersName = new ArrayList<>(waitingConnections.keySet());
		// TODO Collections.shuffle(playersName);
		for(int i = 0; i < playersName.size(); i++) {
			Connection connection = waitingConnections.get(playersName.get(i));
			views.add(new View(playersName.get(i), connection));
			connection.setView(views.get(i));
			model.attach(views.get(i));
			views.get(i).attach(controller);
			playingPlayers.put(playersName.get(i), connection);
		}
		model.setUpModel(playersName, new PlayerResumeHandler(views));
		for(Connection connection : waitingConnections.values()) {
			connection.startGame();
		}
	}
	
	boolean existsPlayerView(Connection c) {
		for(View view : views) {
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
