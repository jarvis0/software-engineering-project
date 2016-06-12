package it.polimi.ingsw.ps23.server.model.player;

import java.util.List;

import it.polimi.ingsw.ps23.server.view.View;

public class PlayerResumeHandler {

	private List<View> consoleViews;
	
	public PlayerResumeHandler(List<View> consoleViews) {
		this.consoleViews = consoleViews;
	}
	
	public synchronized void resume() {
		for(View consoleView : consoleViews) {
				consoleView.threadWakeUp();
		}
	}
	
}
