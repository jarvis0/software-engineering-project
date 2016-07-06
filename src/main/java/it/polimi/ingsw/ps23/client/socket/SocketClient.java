package it.polimi.ingsw.ps23.client.socket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class has all methods to make the client socket connection work.
 * @author Giuseppe Mascellaro
 *
 */
public class SocketClient {

	private static final String CONSOLE_TAG = "<console>";
	private static final String GUI_TAG = "<gui>";
	private static final String END_OF_MESSAGE_TAG = "<eom>";

	private Scanner scanner;
	private PrintStream output;
	
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private RemoteView remoteView;
	
	/**
	 * Initializes a new socket connection with the server.
	 * @param portNumber - TCP port number
	 * @throws IOException if the remote server is unreachable.
	 */
	public SocketClient(int portNumber) throws IOException {
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out, true);
		socket = new Socket(InetAddress.getLocalHost().getHostName(), portNumber);
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter(END_OF_MESSAGE_TAG);
		textOut = new PrintStream(socket.getOutputStream());
	}

	/**
	 * Sends a string message to the server.
	 * @param message - to be sent to the server
	 */
	public void send(String message) {
 		textOut.print(message + END_OF_MESSAGE_TAG);
 	}
	
	/**
	 * Sleeps until the server sends a string message.
	 * @return the received string message.
	 */
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

	/**
	 * Starts a new RemoteView related to the specified
	 * type of view the user has chosen previously.
	 * @param clientInfos
	 */
	public void start(String clientInfos) {
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

	synchronized void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot close the connection.", e);
		}
	}
	
}
