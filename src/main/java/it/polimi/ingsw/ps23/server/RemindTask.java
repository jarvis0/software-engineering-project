package it.polimi.ingsw.ps23.server;

import java.util.TimerTask;

class RemindTask extends TimerTask {

	private Server server;
	private int seconds;
	private int i;

	public RemindTask(Server server, int seconds) {
		this.server = server;
		this.seconds = seconds;
		i = 1;
	}
		
	@Override
	public void run() {
		if(i != seconds) {
			System.out.println("A new game is starting in " + (seconds - i) + " seconds...");
			i++;
		}
		else {
			server.setTimerEnd();
		}
	}
}
	

