package it.polimi.ingsw.ps23.server.model.player;

import java.util.List;

import it.polimi.ingsw.ps23.server.view.View;

public class PlayerResumeHandler {

	private List<View> views;
	
	public PlayerResumeHandler(List<View> views) {
		this.views = views;
	}
	
	public synchronized void resume() {
		for(View view : views) {
			view.threadWakeUp();
		}
	}
	
}
