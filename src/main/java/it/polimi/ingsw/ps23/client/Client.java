package it.polimi.ingsw.ps23.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Client {

	private static final int PORT_NUMBER = 12345;
	private static final String LAUNCHING_GAME_PRINT = "STARTINGGAME";
	
	private Scanner scanner;
	private PrintStream output;
	
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private Client(int portNumber) throws IOException {
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out);
		this.socket = new Socket(InetAddress.getLocalHost().getHostName(), portNumber);
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter("EOM");
		textOut = new PrintStream(socket.getOutputStream());
	}

	void send(String message) {
 		textOut.print(message + "EOM");
 		textOut.flush();
 	}
 	
 	String receive() {
 		return textIn.next();
 	}
	
	private void run() {
		String message = receive();
		if(!message.contains(LAUNCHING_GAME_PRINT)) {
			output.print(message);
			send(scanner.nextLine());
			output.println("Waiting others players to connect...\n");
			output.println(receive());
		}
		else {
			output.print(message.replace(LAUNCHING_GAME_PRINT, ""));
			send(scanner.nextLine());
			output.println("Waiting others players to connect...\n");
		}
		RemoteConsoleView remoteConsoleView = new RemoteConsoleView(this, scanner, output);
		remoteConsoleView.run();
	}

	public static void main(String[] args) {
		Client client;
		Logger logger = Logger.getLogger("main");
		try {
			client = new Client(PORT_NUMBER);
			client.run();
		} catch(IOException e) {
			logger.log(Level.SEVERE, "Cannot connect to server.", e);
		}
	}

}
