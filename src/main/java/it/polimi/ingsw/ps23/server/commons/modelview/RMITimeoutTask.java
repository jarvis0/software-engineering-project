package it.polimi.ingsw.ps23.server.commons.modelview;

import java.util.Timer;
import java.util.TimerTask;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.server.GameInstance;

class RMITimeoutTask extends TimerTask {

	private GameInstance gameInstance;
	private ClientInterface client;
	private Timer timer;
	
	RMITimeoutTask(GameInstance gameInstance, ClientInterface client, Timer timer) {
		this.gameInstance = gameInstance;
		this.client = client;
		this.timer = timer;
	}
	
	@Override
	public void run() {
		gameInstance.disconnectRMIClient(client);
		timer.cancel();
	}

}
