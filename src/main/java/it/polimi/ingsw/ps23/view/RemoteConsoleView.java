package it.polimi.ingsw.ps23.view;

import it.polimi.ingsw.ps23.server.Connection;

import java.io.PrintStream;
import java.util.Scanner;

import it.polimi.ingsw.ps23.commons.remote.RemoteObserver;
import it.polimi.ingsw.ps23.model.state.State;

public class RemoteConsoleView extends ConsoleView implements RemoteObserver {
	
	private Connection connection;
	private Scanner scanner;
	private PrintStream output;

	private State state;
	
	public RemoteConsoleView(Scanner scanner, PrintStream output, Connection connection) {
		super(scanner, output);
		this.connection = connection;
		this.scanner = scanner;
		this.output = output;
		connection.register(this);
		//connection.asyncSend("msg da remoteconsoleview"); ?????????
	}

	protected void showMap(String msg) {
		connection.send(msg);
	}
	
	@Override
	public void update() {
		state.acceptView(this);
	}
	
	@Override
	public void update(State state) {
		this.state = state;
	}
	
}