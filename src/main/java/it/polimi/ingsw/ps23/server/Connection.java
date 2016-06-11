package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.view.View;

public class Connection implements Runnable {
	
	private PrintStream output;
	
	private Server server;
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private boolean started;

	private View consoleView;
	
	private Logger logger;
	
	Connection(Server server, Socket socket) throws IOException {
		super();
		this.server = server;
		this.socket = socket;
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter("EOM");
		textOut = new PrintStream(socket.getOutputStream());
		output = new PrintStream(System.out);
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
 		return textIn.next();
 	}

	synchronized void closeConnection() {		
		send("Connection ended.");
		try {
			socket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Cannot close the connection.", e);
		}
	}
	
	private void close() {
		closeConnection();		
		output.println("A client logged out.");
		server.deregisterConnection(this);
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
			output.println("A new game has been started.");
		}
	}

	void setConsoleView(View consoleView) {
		this.consoleView = consoleView;
	}
	
	@Override
	public void run() {
		server.joinToWaitingList(this, receive());
		initialization();
		consoleView.run();
		close();
	}

}
