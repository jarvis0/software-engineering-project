package it.polimi.ingsw.ps23.client.rmi;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.model.state.State;

public class RMIClient implements ClientInterface {
	
	private RMIView rmiView;
	
	private ExecutorService executor;

	public RMIClient(String playerName, Scanner scanner, PrintStream output) {
		scanner.nextLine();//TODO
		rmiView = new RMIConsoleView(playerName, scanner, output);
		//rmiView = new RMIGUIView(playerName, output);
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
			rmiView.infoMessage(message);
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
