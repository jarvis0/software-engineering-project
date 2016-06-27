package it.polimi.ingsw.ps23.server;

import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

class LaunchingGameTask extends TimerTask {

	private Server server;
	
	private Timer timer;
	
	private PrintStream output;
	
	boolean isRMI;
	
	private int seconds;
	private int i;

	LaunchingGameTask(Server server, int seconds) {
		this.server = server;
		this.seconds = seconds;
		isRMI = false;
		output = new PrintStream(System.out, true);
		i = 1;
	}
	
	LaunchingGameTask(Timer timer, Server server, int seconds) {
		this.timer = timer;
		this.server = server;
		this.seconds = seconds;
		isRMI = true;
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
			if(isRMI) {
				server.setRMITimerEnd(timer);
			}
			else {
				server.setSocketTimerEnd();
			}
		}
	}
	
}
	
