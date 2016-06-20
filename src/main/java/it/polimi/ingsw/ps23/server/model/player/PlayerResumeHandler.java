package it.polimi.ingsw.ps23.server.model.player;

import java.util.List;

import it.polimi.ingsw.ps23.server.view.SocketView;

public class PlayerResumeHandler {

	private List<SocketView> views;
	
	public PlayerResumeHandler(List<SocketView> views) {
		this.views = views;
	}
	
	public synchronized void resume() {
		for(SocketView view : views) {
			view.threadWakeUp();
		}
	}
	
}
