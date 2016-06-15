package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.server.commons.exceptions.ViewNotFoundException;

class Server implements ServerInterface {
	
	private static final int PORT = 12345;
	private static final int MINIMUM_PLAYERS_NUMBER = 2;
	private static final int LAUNCH_TIMEOUT = 10;
	private static final int CONNECTION_TIMEOUT = 120;
	private static final String SECONDS_PRINT =  " seconds...";
	

	private static final String POLICY_NAME = "COF Server";
	
	private ExecutorService executor;
	
	private ServerSocket serverSocket;

	private List<Connection> allConnections;
	private Map<String, Connection> waitingConnections;
	private Map<String, Connection> playingPlayers; //forse serve per ripristinare la sessione giocatore
	
	private boolean launchingGame;
	private boolean active;
	
	private PrintStream output;
	private Logger logger;

	private GameInstancesSet gameInstances;
	
	private Server() {
		output = new PrintStream(System.out);
		gameInstances = new GameInstancesSet();
		active = true;
		logger = Logger.getLogger(this.getClass().getName());
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			active = false;
			logger.log(Level.SEVERE, "Cannot initialize the server connection socket.", e);
			Thread.currentThread().interrupt();
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
		if(waitingConnections.size() == MINIMUM_PLAYERS_NUMBER) {
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
					active = false;
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
		gameInstances.newGame(waitingConnections, playingPlayers);
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
		} catch (IOException | NullPointerException e) {
			active = false;
			logger.log(Level.SEVERE, "Cannot create a new connection socket.", e);
		}
	}
	
	//TODO 2 giocatori minimo altrimenti deve terminare il game
	synchronized void deregisterConnection(Connection c) throws ViewNotFoundException {
		String disconnectedPlayer = gameInstances.disconnectPlayer(c);
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
		return active;
	}

	private void startRMI() {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(this, 0);
			registry.bind(POLICY_NAME, stub);
			
		} catch (RemoteException | AlreadyBoundException e) {
			logger.log(Level.SEVERE, "Cannot create a new registry.", e);
		}
	}

	@Override
	public void registerClient(String name, ClientInterface client) {
		//se ci sono due giocatori nella waiting list ==> lancia il timer
		try {
			client.notify("CIAOOOOOOOO");
		} catch (RemoteException e) {
		}
	}

	@Override
	public void notify(String message) {
		System.out.println(message);
	}

	public void startSocket() {
		while(isActive()) {
			newConnection();
		}
	}

	public static void main(String[] args) {
		Server server;
		server = new Server();
		server.startRMI();
		server.startSocket();
	}

}
