package it.polimi.ingsw.ps23.client.rmi;

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

import it.polimi.ingsw.ps23.server.ServerInterface;

class RMIClient implements ClientInterface {
	
	private static final int RMI_PORT_NUMBER = 1099;
	private static final String POLICY_NAME = "COF_Server";
	
	private Scanner scanner;
	private PrintStream output;
	
	private RMIView remoteView;
	
	private RMIClient() {
		remoteView = new RMIConsoleView(this);
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out, true);
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		PrintStream output = new PrintStream(System.out, true);
		output.print("Welcome, what's your name? ");
		String playerName = scanner.next();
		scanner.close();
		try {
			Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress(), RMI_PORT_NUMBER);
			ServerInterface server = (ServerInterface) registry.lookup(POLICY_NAME);
			ClientInterface client =  new RMIClient();
			ClientInterface stub = (ClientInterface) UnicastRemoteObject.exportObject(client, 0);
			server.registerClient(playerName, stub);
		} catch (RemoteException | UnknownHostException | NotBoundException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Cannot connect to registry.", e);
		}
	}

	@Override
	public void infoMessage(String message) throws RemoteException {
		output.println(message);
		//remoteView.xxxxxx()
	}
	
}
