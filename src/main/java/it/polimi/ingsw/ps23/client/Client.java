package it.polimi.ingsw.ps23.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import it.polimi.ingsw.ps23.view.View;

public class Client {

	private static final int PORT_NUMBER = 12345;
	
	private View consoleView;
	private final Socket socket;
	private BufferedReader socketIn;
	private BufferedWriter socketOut;
	
	private Client(int portNumber) throws IOException {
		this.socket = new Socket(InetAddress.getLocalHost().getHostName(), portNumber);
	}

	private void run() {
		System.out.println(readFromServer());
		//consoleView = new ConsoleView(System.in, System.out);
		//consoleView.attach(controller);
		//model.attach(consoleView);
		//consoleView = new ConsoleView(System.in, System.out);
		//consoleView.run();
	}
	
	private String readFromServer() {
		String message = null;
		try {
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//while(!socketIn.ready());
			message = socketIn.readLine();
			socketIn.close();
		} catch (IOException e) {
			System.out.println("Server error.");
		}
		return message;
	}
	
	public static void main(String[] args) {
		Client client;
		try {
			client = new Client(PORT_NUMBER);
			client.run();
		} catch(IOException e) {
			System.out.println("Cannot connect to given Server.");
		}
	}

}
