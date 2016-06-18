package it.polimi.ingsw.ps23.server.commons.modelview;

import java.util.Timer;
import java.util.TimerTask;

class RMITimeoutTask extends TimerTask {

	private String playerName;
	private Timer timer;
	
	RMITimeoutTask(String playerName, Timer timer) {
		this.playerName = playerName;
		this.timer = timer;
	}
	
	@Override
	public void run() {
		
		timer.cancel();
	}

}
