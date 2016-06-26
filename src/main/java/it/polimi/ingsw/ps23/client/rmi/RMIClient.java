package it.polimi.ingsw.ps23.client.rmi;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.ServerInterface;
import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.model.state.State;

class RMIClient implements ClientInterface {
	
	private static final int RMI_PORT_NUMBER = 1099;
	private static final String POLICY_NAME = "cofRegistry";
	
	private PrintStream output;
	
	private RMIView rmiView;

	private ExecutorService executor;
	
	private RMIClient(String playerName) {
		rmiView = new RMIConsoleView(playerName);
		//rmiView = new RMIGUIView(playerName);
		executor = Executors.newSingleThreadExecutor();
		executor.submit(rmiView);
		output = new PrintStream(System.out);
	}

	public static void main(String[] args) {
		//@SuppressWarnings("resource")
		//Scanner scanner = new Scanner(System.in);
		PrintStream output = new PrintStream(System.out, true);
		output.print("Welcome, what's your name (only letters)? ");
		String playerName = "AleGiuMir";
		try {
			Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress(), RMI_PORT_NUMBER);
			ServerInterface server = (ServerInterface) registry.lookup(POLICY_NAME);
			ClientInterface client = new RMIClient(playerName);
			ClientInterface stub = (ClientInterface) UnicastRemoteObject.exportObject(client, 0);
			server.registerRMIClient(playerName, stub);
		} catch (RemoteException | UnknownHostException | NotBoundException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Cannot connect to RMI registry.", e);
		}
	}
	
	@Override
	public void setController(ServerControllerInterface controller) {
		rmiView.setController(controller);
	}

	@Override
	public void infoMessage(String message) {
		output.println(message);
	}

	@Override
	public void changeState(State currentState) {
		((RMIView) rmiView).update(currentState);
	}

	@Override
	public void changeName(String newName) throws RemoteException {
		rmiView.setNewClientName(newName);
	}

}
