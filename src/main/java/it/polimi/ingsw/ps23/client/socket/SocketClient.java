package it.polimi.ingsw.ps23.client.socket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class SocketClient {

	private static final int SOCKET_PORT_NUMBER = 12345;
	
	private Scanner scanner;
	private PrintStream output;
	
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private RemoteConsoleView remoteConsoleView;

	private boolean connectionTimedOut;
	
	private SocketClient(int portNumber) throws IOException {
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out, true);
		socket = new Socket(InetAddress.getLocalHost().getHostName(), portNumber);
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter("EOM");
		textOut = new PrintStream(socket.getOutputStream());
	}

	void send(String message) {
 		textOut.print(message + "EOM");
 	}
	
	String receive() {
		String message = new String();
		try {
			message = textIn.next();
		} catch (NoSuchElementException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Connection timed out.", e);
			output.println("Connection timed out. Reconnect to resume the game.");
			if(remoteConsoleView != null) {
				remoteConsoleView.setConnectionTimedOut();
			}
			else {
				connectionTimedOut = true;
			}
			closeConnection();
 		}
		return message;
 	}

	synchronized void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot close the connection.", e);
		}
	}
	
	void start() {
		String message = receive();
		output.print(message);
		send(scanner.nextLine());
		output.println("Waiting others players to connect...\n");
		output.println(receive());
		if(!connectionTimedOut) {
			remoteConsoleView = new RemoteConsoleView(this, scanner, output);
			remoteConsoleView.run();
		}
	}
	
	public static void main(String[] args) {
		SocketClient client;
		try {
			client = new SocketClient(SOCKET_PORT_NUMBER);
			client.start();
		} catch(IOException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Cannot connect to server.", e);
		}
	}

}
