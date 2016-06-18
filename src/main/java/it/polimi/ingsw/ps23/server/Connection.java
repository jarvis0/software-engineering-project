package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.ViewNotFoundException;
import it.polimi.ingsw.ps23.server.view.SocketView;

public class Connection implements Runnable {
	
	private Server server;
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private int timeout;

	private boolean started;

	private SocketView socketView;
	
	Connection(Server server, Socket socket, int timeout) throws IOException {
		super();
		this.server = server;
		this.socket = socket;
		this.timeout = timeout;
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter("EOM");
		textOut = new PrintStream(socket.getOutputStream(), true);
		started = false;
	}
	
	Server getServer() {
		return server;
	}
	
	public void send(String message) {
 		textOut.print(message + "EOM");
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
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot close the connection.", e);
		}
	}
	
	void close() {
		closeConnection();
		try {
			server.deregisterSocketConnection(this);
		} catch (ViewNotFoundException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot find disconnecting player view.", e);
		}
	}

	synchronized void setStarted() {
		started = true;
	}
	
	synchronized void startGame() {
		notifyAll();
	}
	
	private synchronized void initialization() {
		if(!started) {
			try {
				wait();
			} catch (InterruptedException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot put connection " + this + " on hold.", e);
				Thread.currentThread().interrupt();
			}
		}
		else {
			server.initializeGame();
		}
	}

	void setSocketView(SocketView socketView) {
		this.socketView = socketView;
	}

	@Override
	public void run() {
		server.joinToWaitingList(this, receive());
		initialization();
		socketView.run();
		close();
	}

}
