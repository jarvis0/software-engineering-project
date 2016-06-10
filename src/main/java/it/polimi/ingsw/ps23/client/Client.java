package it.polimi.ingsw.ps23.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static final int PORT_NUMBER = 12345;
	
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

	public void send(String message) {
 		textOut.print(message + "EOM");
 		textOut.flush();
 	}
 	
 	public String receive() {
 		return textIn.next();
 	}
	
	private void run() {
		output.println(receive());
		output.print(receive());
		send(scanner.nextLine());
		output.println("Waiting others players to connect...");
		output.println(receive());
		RemoteConsoleView remoteConsoleView = new RemoteConsoleView(this, scanner, output);
		remoteConsoleView.run();
	}

	public static void main(String[] args) {
		Client client;
		try {
			client = new Client(PORT_NUMBER);
			client.run();
		} catch(IOException e) {
			System.out.println("Cannot connect to server.");
		}
	}

}
