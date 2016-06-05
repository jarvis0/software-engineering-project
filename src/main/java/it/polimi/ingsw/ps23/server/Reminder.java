package it.polimi.ingsw.ps23.server;

import java.util.Timer;
import java.util.TimerTask;

public class Reminder implements Runnable {
	
	private Server server;
	private int seconds;
	private int i;
	
	public Reminder(Server server, int seconds) {
		this.server = server;
		this.seconds = seconds;
		i = 1;
	}

	class RemindTask extends TimerTask {
		@Override
		public void run() {
			if(i != seconds) {
				System.out.print("\rA new game is starting in " + (seconds - i) + " seconds...");
				i++;
				countInstant();
			}
			else {
				server.startGame();
			}
		}
	}
	
	private void countInstant() {
		Timer timer = new Timer();
		timer.schedule(new RemindTask(), 1000L);
	}
	
	@Override
	public void run() {
		countInstant();
	}

}
