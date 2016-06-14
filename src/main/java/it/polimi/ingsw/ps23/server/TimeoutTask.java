package it.polimi.ingsw.ps23.server;

import java.util.TimerTask;

class TimeoutTask extends TimerTask {

	private Connection connection;
	
	TimeoutTask(Connection connection) {
		this.connection = connection;
	}
		
	@Override
	public void run() {
		connection.close();
	}
	
}
	

