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
			if(seconds - i > 1) {
				System.out.println("A new game is starting in " + (seconds - i) + " seconds...");
			}
			else {
				System.out.println("A new game is starting in " + (seconds - i) + " second...");
			}
			i++;
		}
		else {
			server.setTimerEnd();
		}
	}
}
	

