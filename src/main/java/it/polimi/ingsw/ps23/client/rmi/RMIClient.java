package it.polimi.ingsw.ps23.client.rmi;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.model.state.State;
/**
 * Provides methods to manage the client with RMI. With class handle the connection, disconnection and all 
 * state send by model.
 * @author Giuseppe Mascellaro
 *
 */
public class RMIClient implements ClientInterface {
	
	private RMIView rmiView;
	
	private ExecutorService executor;
	/**
	 * Constructs the client in rmi with the chosen visualization: GUI or CLI.
	 * @param playerName - the name of the user
	 * @param scanner - the scanner to take input
	 * @param output - object to take output from user
	 */
	public RMIClient(String playerName, Scanner scanner, PrintStream output) {
		String ui;
		do {
			output.print("Do you want to use CLI or GUI? ");
			ui = scanner.nextLine();
			
		} while(!(isCLI(ui) || isGUI(ui)));
		if(isCLI(ui)) {
			rmiView = new RMIConsoleView(playerName, scanner, output);
		}
		else {
			rmiView = new RMIGUIView(playerName, output);
		}
	}
	
	private boolean isCLI(String ui) {
		return "cli".equalsIgnoreCase(ui);
	}
	
	private boolean isGUI(String ui) {
		return "gui".equalsIgnoreCase(ui);
	}
	
	@Override
	public void setController(ServerControllerInterface controller) throws RemoteException {
		rmiView.setController(controller);
		executor = Executors.newSingleThreadExecutor();
		executor.submit(rmiView);
	}

	@Override
	public void infoMessage(String message) {
		if(!message.contains("<map_type>") && !message.contains("</map_type>")) {
			rmiView.infoMessage(message + "\n");
		}
		else {
			rmiView.setMapType(message.replace("<map_type>", "").replace("</map_type>", ""));
		}
	}

	@Override
	public void changeState(State currentState) {
		 rmiView.update(currentState);
	}

	@Override
	public void changeName(String newName) throws RemoteException {
		rmiView.setNewClientName(newName);
	}

	@Override
	public void disconnectRMIPlayer() throws RemoteException {
		rmiView.setEndGame();
	}

}
