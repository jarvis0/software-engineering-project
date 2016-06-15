package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.ViewNotFoundException;
import it.polimi.ingsw.ps23.server.view.View;

public class Connection implements Runnable {
	
	private Server server;
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private int timeout;

	private boolean started;

	private View view;
	
	private Logger logger;
	
	Connection(Server server, Socket socket, int timeout) throws IOException {
		super();
		this.server = server;
		this.socket = socket;
		this.timeout = timeout;
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter("EOM");
		textOut = new PrintStream(socket.getOutputStream());
		logger = Logger.getLogger(this.getClass().getName());
		started = false;
	}
	
	Server getServer() {
		return server;
	}
	
	public void send(String message) {
 		textOut.print(message + "EOM");
 		textOut.flush();
 	}
 	
 	public String receive() {
		Timer timer = new Timer();
		timer.schedule(new TimeoutTask(this, timer), timeout * 1000L);
		String message = textIn.next();
 		timer.cancel();
 		return message;
 	}

	synchronized void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Cannot close the connection.", e);
		}
	}
	
	void close() {
		closeConnection();
		try {
			server.deregisterConnection(this);
		} catch (ViewNotFoundException e) {
			logger.log(Level.SEVERE, "Cannot find disconnecting player view.", e);
		}
	}

	synchronized void setStarted() {
		started = true;
	}
	
	synchronized void startGame() {
		notifyAll();
	}
	
	private synchronized void initialization() {
		boolean loop = true;
		if(!started) {
			while(loop) {
				try {
					wait();
					loop = false;
				} catch (InterruptedException e) {
					logger.log(Level.SEVERE, "Cannot put connection " + this + " on hold.", e);
					Thread.currentThread().interrupt();
				}
			}
		}
		else {
			server.initializeGame();
		}
	}

	void setView(View view) {
		this.view = view;
	}
	
	void endThread() {
		Thread.currentThread().interrupt();
	}
	
	@Override
	public void run() {
		server.joinToWaitingList(this, receive());
		initialization();
		view.run();
		close();
	}

}
