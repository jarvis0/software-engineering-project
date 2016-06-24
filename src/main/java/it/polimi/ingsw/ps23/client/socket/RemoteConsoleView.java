package it.polimi.ingsw.ps23.client.socket;

import java.io.PrintStream;
import java.util.Scanner;

class RemoteConsoleView {

	private static final String NO_INPUT = "NOINPUTNEEDED";
	
	private SocketClient client;
	
	private Scanner scanner;
	private PrintStream output;
	
	private boolean connectionTimedOut;
	
	RemoteConsoleView(SocketClient client, Scanner scanner, PrintStream output) {
		this.client = client;
		this.scanner = scanner;
		this.output = output;
		connectionTimedOut = false;
	}
	
	void setConnectionTimedOut() {
		connectionTimedOut = true;
	}
	
	void run() {
		String message;
		do {
			message = client.receive();
			if(!message.contains(NO_INPUT)) {
				output.println(message);
				client.send(scanner.nextLine());
			}
			else {
				output.println(message.replace(NO_INPUT, ""));
			}
		} while(!connectionTimedOut);
		client.closeConnection();
	}

}
