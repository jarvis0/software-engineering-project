package it.polimi.ingsw.ps23.server.commons.modelview;

import java.util.Timer;
import java.util.TimerTask;

import it.polimi.ingsw.ps23.server.GameInstance;

class RMITimeoutTask extends TimerTask {

	private GameInstance gameInstance;
	private Timer timer;
	
	RMITimeoutTask(GameInstance gameInstance, Timer timer) {
		this.gameInstance = gameInstance;
		this.timer = timer;
	}
	
	@Override
	public void run() {
		gameInstance.disconnectRMIClient();
		timer.cancel();
	}

}
