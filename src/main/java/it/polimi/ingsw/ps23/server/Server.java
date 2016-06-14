package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
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

class Server {
	
	private static final int PORT = 12345;
	private static final int LAUNCH_TIMEOUT = 7;
	private static final int CONNECTION_TIMEOUT = 4;
	private static final String SECONDS_PRINT =  " seconds...";
	
	private ExecutorService executor;
	
	private ServerSocket serverSocket;

	private List<Connection> allConnections;
	private Map<String, Connection> waitingConnections;
	private Map<String, Connection> playingPlayers; //forse serve per ripristinare la sessione giocatore
	
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
			Thread.currentThread().interrupt();
			//eccezione fatale, come si fa?
		}
		executor = Executors.newCachedThreadPool();
		allConnections = new ArrayList<>();
		waitingConnections = new HashMap<>();
		playingPlayers = new HashMap<>();
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
		//TODO contorllare che questo player vuole rientrare in una partita precedentemente abbandonata
		startCountdown();
	}
	
	synchronized void initializeGame() {
		model = new Model();
		controller = new Controller(model);
		List<String> playersName = new ArrayList<>(waitingConnections.keySet());
		Collections.shuffle(playersName);
		views = new ArrayList<>();
		for(int i = 0; i < playersName.size(); i++) {
			Connection connection = waitingConnections.get(playersName.get(i));
			views.add(new View(playersName.get(i), connection));
			connection.setView(views.get(i));
			model.attach(views.get(i));
			views.get(i).attach(controller);
			playingPlayers.put(playersName.get(i), connection);
		}
		model.setUpModel(playersName, new PlayerResumeHandler(views));
		for(Connection connection : waitingConnections.values()) {
			connection.startGame();
		}
		launchingGame = false;
		waitingConnections.clear();
		output.println("A new game has started.");
	}
	
	private void newConnection() {
		try {
			output.println("Waiting for connections...");
			Socket newSocket = serverSocket.accept();
			output.println("I've received a new Client connection.");
			Connection connection = new Connection(this, newSocket, CONNECTION_TIMEOUT);
			allConnections.add(connection);
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
	
	//TODO 2 giocatori minimo altrimenti deve terminare il game
	synchronized void deregisterConnection(Connection c) {
		//TODO il player X passa offline
		String disconnectedPlayer = new String();
		Iterator<View> loop = views.iterator();
		while(loop.hasNext()) { //TODO se rimuovo potrei avere problemi a ciclare
			View view = loop.next();
			if(view.getConnection() == c) { //TODO rimuovere questa view da observer di model
				disconnectedPlayer = view.getClientName();
				view.setPlayerOffline();
				view.getConnection().endThread();
				loop.remove();
			}
		}
		for(View view : views) {
			view.sendNoInput("The player " + disconnectedPlayer + " has disconnected.");
		}
		allConnections.remove(c);
		Iterator<String> iterator = playingPlayers.keySet().iterator();
		while(iterator.hasNext()) {
			if(playingPlayers.get(iterator.next()) == c) {
				iterator.remove();
			}
		}
		iterator = waitingConnections.keySet().iterator();
		while(iterator.hasNext()){
			if(waitingConnections.get(iterator.next()) == c){
				iterator.remove();
			}
		}
		output.println("The player " + disconnectedPlayer + " has disconnected.");
	}
	
	private boolean isActive() {
		return true;
	}

	public void run() {
		while(isActive()) {
			newConnection();
		}
	}

	public static void main(String[] args) {
		Server server;
		server = new Server();
		server.run();
	}

}
