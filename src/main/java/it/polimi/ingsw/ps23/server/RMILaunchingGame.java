package it.polimi.ingsw.ps23.server;

import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

class RMILaunchingGame extends TimerTask {

	private Server server;
	
	private Timer timer;
	
	private PrintStream output;
	
	private int seconds;
	private int i;

	RMILaunchingGame(Timer timer, Server server, int seconds) {
		this.timer = timer;
		this.server = server;
		this.seconds = seconds;
		output = new PrintStream(System.out, true);
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
			server.setRMITimerEnd(timer);
		}
	}
	
}
