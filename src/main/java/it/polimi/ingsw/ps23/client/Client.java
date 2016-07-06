package it.polimi.ingsw.ps23.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.client.rmi.RMIClient;
import it.polimi.ingsw.ps23.client.socket.SocketClient;
import it.polimi.ingsw.ps23.server.ServerInterface;

class Client {

	private static final int RMI_PORT_NUMBER = 1099;
	private static final String POLICY_NAME = "cofRegistry";
	private static final int SOCKET_PORT_NUMBER = 12345;
	
	private static final String CLI_TAG = "<console>";
	private static final String GUI_TAG = "<gui>";

	private Scanner scanner;
	private PrintStream output;
	
	private Client() {
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out, true);
	}
	
	private void initializeRMI(String playerName) {
		try {
			Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress(), RMI_PORT_NUMBER);
			ServerInterface server = (ServerInterface) registry.lookup(POLICY_NAME);
			ClientInterface client = new RMIClient(playerName, scanner, output);
			ClientInterface stub = (ClientInterface) UnicastRemoteObject.exportObject(client, 0);
			server.registerRMIClient(playerName, stub);
		} catch (RemoteException | UnknownHostException | NotBoundException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot connect to RMI registry.", e);
		}
	}
	
	private boolean isCLI(String ui) {
		return "cli".equalsIgnoreCase(ui);
	}
	
	private boolean isGUI(String ui) {
		return "gui".equalsIgnoreCase(ui);
	}
	
	private void initializeSocket(String playerName) {
		String ui;
		do {
			output.print("Do you want to use CLI or GUI? ");
			ui = scanner.nextLine();
			
		} while(!(isCLI(ui) || isGUI(ui)));
		String clientInfo;
		if(isCLI(ui)) {
			clientInfo = CLI_TAG + playerName;
		}
		else {
			clientInfo = GUI_TAG + playerName;
		}
		try {
			SocketClient client = new SocketClient(SOCKET_PORT_NUMBER, scanner, output);
			client.start(clientInfo);
		} catch(IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot connect to server.", e);
		}
	}
	
	private boolean isRMI(String connection) {
		return "rmi".equalsIgnoreCase(connection);
	}
	
	private boolean isSocket(String connection) {
		return "socket".equalsIgnoreCase(connection);
	}
	
	private void start() {
		output.print("Welcome, what's your name (only letters or previous in game name)? ");
		String playerName = scanner.nextLine();
		String connection;
		do {
			output.print("Choose the connection protocolol [RMI/Socket]: ");
			connection = scanner.nextLine();
		} while(!(isRMI(connection) || isSocket(connection)));
		if(isRMI(connection)) {
			initializeRMI(playerName);
		}
		else {
			initializeSocket(playerName);
		}
	}
	
	/**
	 * This is the client entry point for Council Of Four application.
	 * When started, it asks for the player game name
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
	
}
