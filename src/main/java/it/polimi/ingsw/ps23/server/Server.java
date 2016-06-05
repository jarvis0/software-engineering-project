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
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.ps23.controller.Controller;
import it.polimi.ingsw.ps23.model.Model;
import it.polimi.ingsw.ps23.server.Reminder;
import it.polimi.ingsw.ps23.view.RemoteConsoleView;

public class Server {
	
	private static final int PORT = 12345;
	private static final int TIMEOUT = 6;
	
	private ExecutorService executor;
	private ExecutorService timer;
	
	private ServerSocket serverSocket;

	private List<Connection> connections;
	private Map<String, Connection> waitingConnections;
	private Map<String, Connection> playingConnections;
	
	private Scanner scanner;
	private PrintStream output;
	
	private List<RemoteConsoleView> remoteConsoleView;
	private Controller controller;
	private Model model;
	
	private Server() throws IOException {
		serverSocket = new ServerSocket(PORT);
		executor = Executors.newCachedThreadPool();
		timer = Executors.newSingleThreadExecutor();
		connections = new ArrayList<>();
		waitingConnections = new HashMap<>();
		playingConnections = new HashMap<>();
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out);
	}
	
	private synchronized void registerConnection(Connection c) {
		connections.add(c);
	}
	
	public synchronized void deregisterConnection(Connection c) {
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
	
	public synchronized void joinToWaitingList(Connection c, String name) {
		output.println("Player " + name + " has been added to the waiting list.");
		waitingConnections.put(name, c);
		if(waitingConnections.size() == 2) {
			output.print("A new game is starting in " + TIMEOUT + " seconds...");
			Reminder reminder = new Reminder(this, TIMEOUT);
			timer.submit(reminder);
		}
	}
	
	public synchronized void startGame() {
		model = new Model();
		controller = new Controller(model);
		List<String> playersName = new ArrayList<>(waitingConnections.keySet());
		List<Connection> readyConnections = new ArrayList<>();
		remoteConsoleView = new ArrayList<>();
		for(int i = 0; i < playersName.size(); i++) {
			readyConnections.add(waitingConnections.get(playersName.get(i)));
			playingConnections.put(playersName.get(i), readyConnections.get(i));
			remoteConsoleView.add(new RemoteConsoleView(scanner, output, readyConnections.get(i)));
			model.attach(remoteConsoleView.get(i));
			remoteConsoleView.get(i).attach(controller);
		}
		model.setUpModel(new ArrayList<>(playingConnections.keySet()));
		waitingConnections.clear();
		for(Connection connection : connections) {
			connection.startGame();
		}
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
