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
import it.polimi.ingsw.ps23.view.RemoteConsoleView;

public class Server {
	
	private static final int PORT = 12345;
	
	private ExecutorService executor = Executors.newFixedThreadPool(128);
	
	private ServerSocket serverSocket;

	private List<Connection> connections = new ArrayList<>();
	private Map<String, Connection> waitingConnection = new HashMap<>();
	private Map<String, Connection> playingConnection = new HashMap<>();
	
	private Scanner scanner;
	private PrintStream output;
	
	private List<RemoteConsoleView> remoteConsoleView;
	private Controller controller;
	private Model model;
	
	private Server() throws IOException {
		serverSocket = new ServerSocket(PORT);
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out);
	}
	
	private synchronized void registerConnection(Connection c){
		connections.add(c);
	}
	
	public synchronized void deregisterConnection(Connection c){
		connections.remove(c);
		Connection enemy = playingConnection.get(c);
		if(enemy != null)
			enemy.closeConnection();
		playingConnection.remove(c);
		playingConnection.remove(enemy);
		Iterator<String> iterator = waitingConnection.keySet().iterator();
		while(iterator.hasNext()){
			if(waitingConnection.get(iterator.next()) == c){
				iterator.remove();
			}
		}
	}
	
	public synchronized void rendezvous(Connection c, String name) {
		output.println("Player " + name + " has been added to the waiting list.");
		waitingConnection.put(name, c);
		if(waitingConnection.size() == 2) {
			//inizio a contare 20 secondi se ho 2 giocatori --> poi faccio le istruzioni seguenti
			model = new Model();
			controller = new Controller(model);
			List<String> keys = new ArrayList<>(waitingConnection.keySet());
			Connection c1 = waitingConnection.get(keys.get(0));
			Connection c2 = waitingConnection.get(keys.get(1));
			playingConnection.put(keys.get(0), c1);
			playingConnection.put(keys.get(1), c2);
			remoteConsoleView = new ArrayList<>();
			remoteConsoleView.add(new RemoteConsoleView(scanner, output, c1));
			remoteConsoleView.add(new RemoteConsoleView(scanner, output, c2));
			model.attach(remoteConsoleView.get(0));
			model.attach(remoteConsoleView.get(1));
			remoteConsoleView.get(0).attach(controller);
			remoteConsoleView.get(1).attach(controller);
			waitingConnection.clear();
			model.setUpModel(keys);
			c1.setOnline();
			c2.setOnline();
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
	
	public void run(){
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
