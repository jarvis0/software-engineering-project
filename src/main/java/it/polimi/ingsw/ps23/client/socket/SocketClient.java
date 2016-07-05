package it.polimi.ingsw.ps23.client.socket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient {

	private static final int SOCKET_PORT_NUMBER = 12345;
	private static final String CONSOLE_TAG = "<console>";
	private static final String GUI_TAG = "<gui>";
	private static final String END_OF_MESSAGE_TAG = "<eom>";

	private Scanner scanner;
	private PrintStream output;
	
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private RemoteView remoteView;

	private SocketClient(int portNumber) throws IOException {
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out, true);
		socket = new Socket(InetAddress.getLocalHost().getHostName(), portNumber);
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter(END_OF_MESSAGE_TAG);
		textOut = new PrintStream(socket.getOutputStream());
	}

	public void send(String message) {
 		textOut.print(message + END_OF_MESSAGE_TAG);
 	}
	
	public String receive() {
		String message = new String();
		try {
			message = textIn.next();
		} catch (NoSuchElementException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Connection reset.", e);
			output.println("Connection reset. Reconnect to resume the game.");
			remoteView.setConnectionTimedOut();
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
	
	void start(String clientInfos) {
		send(clientInfos);
		if(clientInfos.contains(CONSOLE_TAG)) {
			remoteView = new RemoteConsoleView(this, scanner, output);
			remoteView.run();
		}
		else {
			if(clientInfos.contains(GUI_TAG)) {
				remoteView = new RemoteGUIView(this, output);
				remoteView.run();
			}
		}
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		PrintStream output = new PrintStream(System.out, true);
		output.print("Welcome, what's your name (only letters or previous in game name)? ");
		//String clientInfos = CONSOLE_TAG + "AleGiuMir";
		//String clientInfos = GUI_TAG + "AleGiuMir";
		String clientInfos = CONSOLE_TAG + scanner.next();
		try {
			SocketClient client = new SocketClient(SOCKET_PORT_NUMBER);
			client.start(clientInfos);
		} catch(IOException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Cannot connect to server.", e);
		}
	}

}
