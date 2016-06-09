package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import it.polimi.ingsw.ps23.view.ConsoleView;

public class Connection implements Runnable {
	
	private PrintStream output;
	
	private Server server;
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private boolean started;

	private ConsoleView consoleView;
	
	public Connection(Server server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		try {
			textIn = new Scanner(socket.getInputStream());
			textIn.useDelimiter("EOM");
			textOut = new PrintStream(socket.getOutputStream());
			
		} catch(IOException e) {
			output.println("Error while initializating connection.");
		}
		output = new PrintStream(System.out);
		started = false;
	}
	
	public Server getServer() {
		return server;
	}
	
	public void send(String message) {
 		textOut.print(message + "EOM");
 		textOut.flush();
 	}
 	
 	public String receive() {
 		return textIn.next();
 	}

	public synchronized void closeConnection() {		
		send("Connection ended.");
		try {
			socket.close();
		} catch(IOException e) {
			output.println("Unable to close the connection.");
		}
	}
	
	private void close() {
		closeConnection();		
		output.println("Deregistro il client!");
		server.deregisterConnection(this);
	}
	
	public synchronized void setStarted() {
		started = true;
	}
	
	public synchronized void startGame() {
		notifyAll();
	}
	
	private synchronized void initialization() {
		if(!started) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			server.initializeGame();
			output.println("A new game has been started.");
		}
	}

	public void setConsoleView(ConsoleView consoleView) {
		this.consoleView = consoleView;
	}
	
	@Override
	public void run() {
		send("Connection established at " + new Date().toString());
		send("Welcome, what's your name? ");
		server.joinToWaitingList(this, receive());
		initialization();
		//remoteWakeUp();
		consoleView.run();
		close();
	}

}
