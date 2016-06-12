package it.polimi.ingsw.ps23.server;

import java.io.PrintStream;
import java.util.TimerTask;

class RemindTask extends TimerTask {

	private Server server;
	
	private PrintStream output;
	
	private int seconds;
	private int i;

	public RemindTask(Server server, int seconds) {
		this.server = server;
		this.seconds = seconds;
		output = new PrintStream(System.out);
		i = 1;
	}
		
	@Override
	public void run() {
		if(i != seconds) {
			if(seconds - i > 1) {
				output.println("A new game is starting in " + (seconds - i) + " seconds...");
			}
			else {
				output.println("A new game is starting in " + (seconds - i) + " second...");
			}
			i++;
		}
		else {
			server.setTimerEnd();
		}
	}
}
	

