package it.polimi.ingsw.ps23.server;

import java.util.Timer;
import java.util.TimerTask;

class TimeoutTask extends TimerTask {

	private Connection connection;
	private Timer timer;
	
	TimeoutTask(Connection connection, Timer timer) {
		this.connection = connection;
		this.timer = timer;
	}
		
	@Override
	public void run() {
		connection.close();
		timer.cancel();
	}
	
}
	

