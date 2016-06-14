package it.polimi.ingsw.ps23.client;

import java.io.PrintStream;
import java.util.Scanner;

class RemoteConsoleView {

	private static final String NO_INPUT = "NOINPUTNEEDED";
	
	private Client client;
	
	private Scanner scanner;
	private PrintStream output;
	
	private boolean connectionTimedOut;
	
	RemoteConsoleView(Client client, Scanner scanner, PrintStream output) {
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
		while(!connectionTimedOut) {
			message = client.receive();
			if(!message.contains(NO_INPUT)) {
				output.println(message);
				client.send(scanner.nextLine());
			}
			else {
				output.println(message.replace(NO_INPUT, ""));
			}
		}
	}

}
