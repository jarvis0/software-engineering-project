package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.controller.Controller;
import it.polimi.ingsw.ps23.server.model.Model;
import it.polimi.ingsw.ps23.server.model.player.PlayerResumeHandler;
import it.polimi.ingsw.ps23.server.view.View;

class Server implements Runnable {
	
	private static final int PORT = 12345;
	private static final int LAUNCH_TIMEOUT = 10;
	private static final int CONNECTION_TIMEOUT = 50;
	private static final String SECONDS_PRINT =  " seconds...";
	
	private ExecutorService executor;
	
	private ServerSocket serverSocket;

	private Map<String, Connection> waitingConnections;
	private Map<String, Connection> playingConnections; //forse serve per ripristinare la sessione giocatore
	
	private boolean launchingGame;
	
	private PrintStream output;
	private Logger logger;

	private Model model;
	private List<View> views;
	private Controller controller;
	
	private Server() {
		output = new PrintStream(System.out);
		logger = Logger.getLogger(this.getClass().getName());
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Cannot initialize the server connection socket.", e);
		}
		executor = Executors.newCachedThreadPool();
		waitingConnections = new HashMap<>();
		playingConnections = new HashMap<>();
		launchingGame = false;
	}

	synchronized void setTimerEnd() {
		notifyAll();
	}

	private synchronized void startCountdown() {
		if(waitingConnections.size() == 2) {
			launchingGame = true;
			output.println("A new game is starting in " + LAUNCH_TIMEOUT + SECONDS_PRINT);
			for(Connection connection : waitingConnections.values()) {
				connection.send("A new game is starting in " + LAUNCH_TIMEOUT + SECONDS_PRINT + "\n");
			}
			Timer timer = new Timer();
			timer.schedule(new RemindTask(this, LAUNCH_TIMEOUT), LAUNCH_TIMEOUT, 1000L);
			boolean loop = true;
			while(loop) {
				try {
					wait();
					loop = false;
				} catch (InterruptedException e) {
					logger.log(Level.SEVERE, "Cannot wait new game countdown.", e);
					Thread.currentThread().interrupt();
				}
			}
			timer.cancel();
			for(Connection connection : waitingConnections.values()) {
				connection.setStarted();
			}
		}
	}
	
	void joinToWaitingList(Connection c, String name) {
		output.println("Player " + name + " has been added to the waiting list.");
		waitingConnections.put(name, c);
		startCountdown();
	}
	
	synchronized void initializeGame() {
		model = new Model();
		controller = new Controller(model);
		List<String> playersName = new ArrayList<>(waitingConnections.keySet());
		views = new ArrayList<>();
		for(int i = 0; i < playersName.size(); i++) {
			Connection connection = waitingConnections.get(playersName.get(i));
			views.add(new View(playersName.get(i), connection));
			connection.setView(views.get(i));
			model.attach(views.get(i));
			views.get(i).attach(controller);
		}
		model.setUpModel(playersName, new PlayerResumeHandler(views));
		for(Connection connection : waitingConnections.values()) {
			connection.startGame();
		}
		launchingGame = false;
		waitingConnections.clear();
		output.println("A new game has been started.");
	}
	
	private void newConnection() {
		try {
			output.println("Waiting for connections...");
			Socket newSocket = serverSocket.accept();
			output.println("I've received a new Client connection.");
			Connection connection = new Connection(this, newSocket, CONNECTION_TIMEOUT);
			String message = "Connection established at " + new Date().toString();
			if(launchingGame) {
				message += "\nA new game is starting in less than " + LAUNCH_TIMEOUT + SECONDS_PRINT;
			}
			connection.send(message + "\nWelcome, what's your name? ");
			if(launchingGame) {
				connection.send("The new game is starting in a few seconds...\n");
			}
			executor.submit(connection);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Cannot create a new connection socket.", e);
		}
	}
	
	synchronized void deregisterConnection(Connection c) {
		Connection connection = playingConnections.get(c);
		if(connection != null)
			connection.closeConnection();
		playingConnections.remove(c);
		playingConnections.remove(connection);
		Iterator<String> iterator = waitingConnections.keySet().iterator();
		while(iterator.hasNext()) {
			if(waitingConnections.get(iterator.next()) == c) {
				iterator.remove();
			}
		}
		output.println("A client has logged out.");
	}
	
	@Override
	public void run() {
		while(true) {
			newConnection();
		}
	}
	
	public static void main(String[] args) {
		Server server;
		server = new Server();
		server.run();
	}

}
