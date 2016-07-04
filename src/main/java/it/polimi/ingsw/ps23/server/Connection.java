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

/**
 * Synchronizes the server side socket connection with the client one
 * and others connections in order to start a new game.
 * <p>
 * Provides send and receive methods to exchange string messages.
 * <p>
 * Provides close socket connection method both for client connection timeout
 * and game end.
 * @author Giuseppe Mascellaro
 *
 */
public class Connection implements Runnable {
	
	private static final String NO_INPUT_TAG_OPEN = "<no_input>";
	private static final String NO_INPUT_TAG_CLOSE = "</no_input>";
	private static final String YES_INPUT_TAG_OPEN = "<yes_input>";
	private static final String YES_INPUT_TAG_CLOSE = "</yes_input>";
	private static final String END_OF_MESSAGE_TAG = "<eom>";
	
	private Server server;
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private int timeout;

	private boolean started;
	private boolean reconnected;

	private SocketView socketView;
	private boolean isConsole;
	
	Connection(Server server, Socket socket, int timeout) throws IOException {
		super();
		this.server = server;
		this.socket = socket;
		this.timeout = timeout;
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter(END_OF_MESSAGE_TAG);
		textOut = new PrintStream(socket.getOutputStream(), true);
		started = false;
		reconnected = false;
	}

	void setConsole(boolean isConsole) {
		this.isConsole = isConsole;
	}
	
	boolean isConsole() {
		return isConsole;
	}
	
	Server getServer() {
		return server;
	}
	/**
	 * Send a socket message to the active socket connection.
	 * The string argument must not contain the communication protocol string:
	 * "EOM" (End of Message) or the sent message will be corrupted. 
	 * <p>
	 * This method always return immediately, whether or not the 
	 * socket client is online.
	 * 
	 * @param message to be sent to the socket client
	 */
	public void send(String message) {
 		textOut.print(message + END_OF_MESSAGE_TAG);
 	}
 	
	public void sendNoInput(String message) {
		send(NO_INPUT_TAG_OPEN + message + NO_INPUT_TAG_CLOSE);
	}
	
	public void sendYesInput(String message) {
		send(YES_INPUT_TAG_OPEN + message + YES_INPUT_TAG_CLOSE);
	}
	
	/**
	 * This method is a listener for socket client connection. It receives 
	 * a string message.
	 * <p>
	 * When called, it will block the program flow waiting for a message
	 * to be received.
	 * It starts an implicit timer which for socket connection timeout
	 * trace. The timeout is set by default by the server application.
	 * 
	 * @return received socket message.
	 */
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

	void setReconnected() {
		reconnected = true;
	}
	
	synchronized void startGame() {
		notifyAll();
	}

	private synchronized void initialization() {
		if(!started) {
			try {
				wait();
			} catch (InterruptedException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot put a socket connection on hold.", e);
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
		server.joinToSocketWaitingList(receive(), this);
		if(!reconnected) {
			initialization();
		}
		else {
			socketView.setReconnected(reconnected);
		}
		socketView.run();
		close();
	}

}
