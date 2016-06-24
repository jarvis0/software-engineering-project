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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.server.commons.exceptions.ViewNotFoundException;

class Server implements ServerInterface {
	
	private static final int SOCKET_PORT_NUMBER = 12345;
	private static final int RMI_PORT_NUMBER = 1099;
	private static final String POLICY_NAME = "cofRegistry";
	private static final int MINIMUM_PLAYERS_NUMBER = 1;
	private static final int LAUNCH_TIMEOUT = 1;
	private static final String LAUNCH_PRINT = "A new game is starting in ";
	private static final int CONNECTION_TIMEOUT = 9000;
	private static final String SECONDS_PRINT =  " seconds...";
	private static final String NO_INPUT = "NOINPUTNEEDED";
	private static final int RANDOM_NUMBERS_POOL = 20;
	
	private ExecutorService executor;
	
	private ServerSocket serverSocket;

	private Map<String, Connection> socketWaitingConnections;
	private Map<String, ClientInterface> rmiWaitingConnections;
	
	private boolean launchingGame;
	private boolean socketActive;

	private GameInstancesSet gameInstances;
	
	private PrintStream output;
	
	private Server() {
		output = new PrintStream(System.out, true);
		gameInstances = new GameInstancesSet(CONNECTION_TIMEOUT);
		socketActive = false;
		executor = Executors.newCachedThreadPool();
		socketWaitingConnections = new HashMap<>();
		rmiWaitingConnections = new HashMap<>();
		launchingGame = false;
	}

	synchronized void initializeGame() {
		gameInstances.newGame(socketWaitingConnections, rmiWaitingConnections);
		launchingGame = false;
		socketWaitingConnections.clear();
		rmiWaitingConnections.clear();
		output.println("A new game is started.");
	}

	synchronized void setRMITimerEnd(Timer timer) {
		timer.cancel();
		for(Connection connection : socketWaitingConnections.values()) {
			connection.setStarted();
		}
		initializeGame();
	}
	

	private synchronized void startCountdownFromRMI() {
		if(socketWaitingConnections.size() + rmiWaitingConnections.size() == MINIMUM_PLAYERS_NUMBER) {
			launchingGame = true;
			output.println(LAUNCH_PRINT + LAUNCH_TIMEOUT + SECONDS_PRINT);
			String message = LAUNCH_PRINT + LAUNCH_TIMEOUT + SECONDS_PRINT;
			for(Connection connection : socketWaitingConnections.values()) {
				connection.send(NO_INPUT + message);
			}
			for(ClientInterface client : rmiWaitingConnections.values()) {
				infoMessage(client, message);
			}
			Timer timer = new Timer();
			timer.schedule(new LaunchingGameTask(timer, this, LAUNCH_TIMEOUT), LAUNCH_TIMEOUT, 1000L);
		}
	}

	private boolean isDouble(String name) {
		for(String playerName : socketWaitingConnections.keySet()) {
			if(name.equals(playerName)) {
				return true;
			}
		}
		for(String playerName : rmiWaitingConnections.keySet()) {
			if(name.equals(playerName)) {
				return true;
			}
		}
		if(gameInstances.checkIfAlreadyInGame(name)) {
			return true;
		}
		return false;
	}

	private String newName(String name, int i) {
		if(name.contains(".")) {
			return name.substring(0, name.indexOf('.') + 1) + new Random().nextInt(RANDOM_NUMBERS_POOL * i);
		}
		else {
			return name + "." + new Random().nextInt(RANDOM_NUMBERS_POOL * i);
		}
	}
	
	private String solveDoubles(String playerName, int i) {
		String newPlayerName = newName(playerName, i);
		if(isDouble(newPlayerName)) {
			newPlayerName = solveDoubles(playerName, i + 1);
		}
		return newPlayerName;
	}

	private void infoMessage(ClientInterface client, String message) {
		try {
			client.infoMessage(message);
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot send a message to the RMI client.", e);
		}
	}
	
	private void rmiChangeName(ClientInterface client, String newName) {
		try {
			client.changeName(newName);
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot change the name to the RMI client.", e);
		}
	}
	
	@Override
	public void registerRMIClient(String name, ClientInterface client) {
		boolean formerPlayer = gameInstances.checkIfFormerPlayer(name);
		if(!formerPlayer && !name.matches("[a-zA-Z]")) {
			infoMessage(client, "Invalid name format.");
			//TODO devo chiudere la connessione?
		}
		else {
			output.println("New RMI client connection received.");
			infoMessage(client, "Connection established at " + new Date().toString() + "\n");
			if(!formerPlayer) {
				String playerName = newName(name, 1);
				if(isDouble(playerName)) {
					playerName = solveDoubles(playerName, 2);
				}
				infoMessage(client, "Here you are your unique in-game name: \"" + playerName + "\".\nIn case of reconnection, use this name to rejoin your game.");
				rmiChangeName(client, playerName);
				rmiWaitingConnections.put(playerName,  client);
				output.println("Player " + playerName + " has been added to the waiting list.");
				String message = new String();
				if(launchingGame) {
					message += "A new game is starting in less than " + LAUNCH_TIMEOUT + SECONDS_PRINT + "\n";
				}
				infoMessage(client, message + "Waiting other players to connect...");
				startCountdownFromRMI();
			}
			else {
				output.println("Player " + name + " is being prompted to his previous game.");
				gameInstances.reconnectPlayer(name, client);
				infoMessage(client, "You have been prompted to your previous game, please wait your turn.");
			}
		}
	}

	private void startRMI() {
		try {
			Registry registry = LocateRegistry.createRegistry(RMI_PORT_NUMBER);
			ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(this, 0);
			registry.bind(POLICY_NAME, stub);
			output.println("Waiting for RMI connections...");
		} catch (RemoteException | AlreadyBoundException e) {
			socketActive = false;
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot create a new registry.", e);
		}
	}

	synchronized void deregisterSocketConnection(Connection connection) throws ViewNotFoundException {
		output.println("Player " + gameInstances.disconnectSocketPlayer(connection) + " has been disconnected from the game due to connection timeout.");
	}

	synchronized void setSocketTimerEnd() {
		notifyAll();
	}

	private synchronized void startCountdownFromSocket() {
		if(socketWaitingConnections.size() + rmiWaitingConnections.size() == MINIMUM_PLAYERS_NUMBER) {
			launchingGame = true;
			output.println(LAUNCH_PRINT + LAUNCH_TIMEOUT + SECONDS_PRINT);
			String message = LAUNCH_PRINT + LAUNCH_TIMEOUT + SECONDS_PRINT;
			for(Connection connection : socketWaitingConnections.values()) {
				connection.send(NO_INPUT + message);
			}
			for(ClientInterface client : rmiWaitingConnections.values()) {
				infoMessage(client, message);
			}
			Timer timer = new Timer();
			timer.schedule(new LaunchingGameTask(this, LAUNCH_TIMEOUT), LAUNCH_TIMEOUT, 1000L);
			try {
				wait();
			} catch (InterruptedException e) {
				socketActive = false;
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot wait new game countdown.", e);
				Thread.currentThread().interrupt();
			}
			timer.cancel();
			for(Connection connection : socketWaitingConnections.values()) {
				connection.setStarted();
			}
		}
	}

	synchronized void joinToSocketWaitingList(String name, Connection connection) {
		boolean formerPlayer = gameInstances.checkIfFormerPlayer(name);
		if(!formerPlayer && !name.matches("[a-zA-Z]")) {
			connection.send(NO_INPUT + "\nInvalid name format.");
			connection.closeConnection();
		}
		else {
			output.println("New socket client connection received.");
			connection.send(NO_INPUT + "Connection established at " + new Date().toString() + "\n");
			if(!formerPlayer) {
				String playerName = newName(name, 1);
				if(isDouble(playerName)) {
					playerName = solveDoubles(playerName, 1);
				}
				connection.send(NO_INPUT + "Here you are your unique in game name: \"" + playerName + "\".\nIn case of reconnection, use this name to rejoin your game.");
				output.println("Player " + playerName + " has been added to the waiting list.");
				socketWaitingConnections.put(playerName, connection);
				String message = new String();
				if(launchingGame) {
					message += "A new game is starting in less than " + LAUNCH_TIMEOUT + SECONDS_PRINT + "\n";
				}
				connection.send(NO_INPUT + message + "Waiting other players to connect...");
				startCountdownFromSocket();
			}
			else {
				output.println("Player " + name + " is being prompted to his previous game.");
				gameInstances.reconnectPlayer(name, connection);
				connection.send(NO_INPUT + "You have been prompted to your previous game, please wait your turn.");
			}
		}
	}

	private void newSocketConnection() {
		try {
			Socket newSocket = serverSocket.accept();
			Connection connection = new Connection(this, newSocket, CONNECTION_TIMEOUT);
			executor.submit(connection);
		} catch (IOException | NullPointerException e) {
			socketActive = false;
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot create a new connection socket.", e);
		}
	}
	
	private boolean isActive() {
		return socketActive;
	}

	private void startSocket() {
		try {
			serverSocket = new ServerSocket(SOCKET_PORT_NUMBER);
			socketActive = true;
			output.println("Waiting for socket connections...");
			while(isActive()) {
				newSocketConnection();
			}
		} catch (IOException e) {
			socketActive = false;
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot initialize the server socket connection.", e);
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.startRMI();
		server.startSocket();
	}

}
