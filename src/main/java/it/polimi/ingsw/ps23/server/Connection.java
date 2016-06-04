package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import it.polimi.ingsw.ps23.commons.remote.RemoteObservable;

public class Connection extends RemoteObservable implements Runnable {
	
	private Scanner scanner;
	private PrintStream output;
	
	private Server server;
	private Socket socket;
	private Scanner socketIn;
	private PrintStream socketOut;
	
	private boolean online;
	
	public Connection(Server server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		try {
			socketIn = new Scanner(socket.getInputStream());
			socketIn.useDelimiter("EOM");
			socketOut = new PrintStream(socket.getOutputStream());
		} catch(IOException e) {
			output.println("Error while initializating connection.");
		}
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out);
		online = false;
	}
	
	private synchronized boolean isOnline() {
		return online;
	}
	
	public void setOnline() {
		online = true;
	}
	
	public void send(String message) {
		socketOut.print(message + "EOM");
		socketOut.flush();
	}
	
	public String receive() {
		return socketIn.next();
	}
	
	public void asyncSend(final String message){
		new Thread(new Runnable() {			
			@Override
			public void run() {
				send(message);
			}
		}).start();
	}
	
	public synchronized void closeConnection() {		
		send("Connection ended.");
		try {
			socket.close();
		} catch(IOException e) {
			output.println("Unable to close the connection.");
		}
		online = false;
	}
	
	private void close() {
		closeConnection();		
		output.println("Deregistro il client!");
		server.deregisterConnection(this);
	}
	
	@Override
	public void run(){
		send("Connection established at " + new Date().toString());
		send("Welcome, what's your name?");
		server.rendezvous(this, receive());
		while(!isOnline()); //mettere in wait this thread fintanto che non viene creata una vera partita
		while(isOnline()) {
			remoteWakeUp(); //mi fa andare sulla remote console view
		}
		close();
	}
	
}
