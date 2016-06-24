package it.polimi.ingsw.ps23.server.model;

import java.util.List;

import it.polimi.ingsw.ps23.server.view.SocketView;

public class PlayersResumeHandler {

	private List<SocketView> views;
	
	public PlayersResumeHandler(List<SocketView> views) {
		this.views = views;
	}
	
	synchronized void resume() {
		for(SocketView view : views) {
			view.threadWakeUp();
		}
	}
	
}
