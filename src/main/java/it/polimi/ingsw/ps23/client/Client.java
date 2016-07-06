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
	
	//private static final String CONSOLE_TAG = "<console>";
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
	
	private void initializeSocket(String playerName) {
		//if GUI then add GUI_TAG else....
		String clientInfos = GUI_TAG + "AleGiuMir";
		//String clientInfos = CONSOLE_TAG + "AleGiuMir";
		try {
			SocketClient client = new SocketClient(SOCKET_PORT_NUMBER);
			client.start(clientInfos);
		} catch(IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot connect to server.", e);
		}
	}
	
	private void start() {
		output.print("Welcome, what's your name (only letters or previous in game name)? ");
		String playerName = scanner.next();
		//if vuole avviare rmi then... else
		initializeRMI(playerName);
		//initializeSocket(playerName);
	}
	
	/**
	 * This is the client entry point for Council Of Four application.
	 * When starts, it asks for the player game name
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
	
}
