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
	private Scanner socketIn;
	private PrintStream socketOut;
	
	private Client(int portNumber) throws IOException {
		this.socket = new Socket(InetAddress.getLocalHost().getHostName(), portNumber);
		try {
			socketIn = new Scanner(socket.getInputStream());
			socketIn.useDelimiter("EOM");
			socketOut = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out);
	}
	
	private void send(String message) {
		socketOut.print(message + "EOM");
		socketOut.flush();
	}
	
	private String receive() {
		return socketIn.next();
	}

	private void run() {
		output.println(receive()); //ricevo l'ora di creazione
		output.println(receive()); 
		send(scanner.nextLine()); 
		while(true) {
			output.println(receive()); 
		}
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
