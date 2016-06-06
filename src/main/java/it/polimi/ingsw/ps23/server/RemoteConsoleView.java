package it.polimi.ingsw.ps23.server;

import it.polimi.ingsw.ps23.server.Connection;
import it.polimi.ingsw.ps23.view.ConsoleView;

import java.io.PrintStream;
import java.util.Scanner;

import it.polimi.ingsw.ps23.commons.remote.RemoteObserver;
import it.polimi.ingsw.ps23.model.state.State;

public class RemoteConsoleView extends ConsoleView implements RemoteObserver {
	
	private Connection connection;

	private State state;
	
	public RemoteConsoleView(Scanner scanner, PrintStream output, Connection connection) {
		super(scanner, output);
		this.connection = connection;
		connection.register(this);
		//connection.asyncSend("msg da remoteconsoleview"); ?????????
	}

	protected void showMap(String message) {
		connection.send(message);
	}

	@Override
	public void remoteUpdate() {
		state.acceptView(this);
	}
	
	@Override
	public void update(State state) {
		this.state = state;
	}
	
}