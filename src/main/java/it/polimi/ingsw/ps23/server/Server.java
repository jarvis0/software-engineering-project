package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.ps23.controller.Controller;
import it.polimi.ingsw.ps23.model.Model;
import it.polimi.ingsw.ps23.view.ConsoleView;

public class Server {
	
	private static final int PORT = 12345;
	private static final int TIMEOUT = 1;
	
	private ExecutorService executor;
	
	private Timer timer;
	
	private ServerSocket serverSocket;

	private List<Connection> connections; //ridondante
	private Map<String, Connection> waitingConnections;
	private Map<String, Connection> playingConnections; //super-ridondante
	
	private PrintStream output;

	private Model model;
	private List<ConsoleView> consoleViews;
	private Controller controller;
	
	private Server() throws IOException {
		serverSocket = new ServerSocket(PORT);
		executor = Executors.newCachedThreadPool();
		connections = new ArrayList<>();
		waitingConnections = new HashMap<>();
		playingConnections = new HashMap<>();
		output = new PrintStream(System.out);
	}
	
	private synchronized void registerConnection(Connection c) {
		connections.add(c);
	}
	
	public synchronized void deregisterConnection(Connection c) {
		//player in un certo specifico game ??
		connections.remove(c);
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
	}
	
	public synchronized void setTimerEnd() {
		notifyAll();
	}

	private synchronized void startCountdown() {
		if(waitingConnections.size() == 2) {
			output.println("A new game is starting in " + TIMEOUT + " seconds...");
			timer = new Timer();
			timer.schedule(new RemindTask(this, TIMEOUT), TIMEOUT, 1000L);
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timer.cancel();
			for(Connection connection : connections) {
				connection.setStarted();
			}
		}
	}
	
	public void joinToWaitingList(Connection c, String name) {
		output.println("Player " + name + " has been added to the waiting list.");
		waitingConnections.put(name, c);
		startCountdown();
	}
	
	public synchronized void initializeGame() {
		model = new Model();
		controller = new Controller(model);
		List<String> playersName = new ArrayList<>(waitingConnections.keySet());
		consoleViews = new ArrayList<>();
		for(int i = 0; i < playersName.size(); i++) {			
			playingConnections.put(playersName.get(i), waitingConnections.get(playersName.get(i)));
			consoleViews.add(new ConsoleView(playersName.get(i), waitingConnections.get(playersName.get(i)), output));
			connections.get(i).setConsoleView(consoleViews.get(i));
			model.attach(consoleViews.get(i));
			consoleViews.get(i).attach(controller);
		}
		for(ConsoleView consoleView : consoleViews) {
			consoleView.setOtherViews(consoleViews);
		}
		model.setUpModel(playersName);
		for(Connection connection : waitingConnections.values()) {
			connection.startGame();
		}
		waitingConnections.clear();
	}
	
	private void newConnection() {
		try {
			output.println("Waiting for connections...");
			Socket newSocket = serverSocket.accept();
			output.println("I've received a new Client connection.");
			Connection connection = new Connection(this, newSocket);
			registerConnection(connection);
			executor.submit(connection);
		} catch (IOException e) {
			output.println("Connection error.");
		}
	}

	public synchronized void resumeView(List<ConsoleView> consoleViews, ConsoleView currentConsoleView) {
		for(ConsoleView consoleView : consoleViews) {
			if(consoleView != currentConsoleView) {
				consoleView.threadWakeUp();
			}
		}
	}
	
	public void run() {
		while(true) {
			newConnection();
		}
	}
	
	public static void main(String[] args) {
		Server server;
		try {
			server = new Server();
			server.run();
		} catch (IOException e) {
			System.out.println("Cannot initialize Server.");
		}		
	}

}
