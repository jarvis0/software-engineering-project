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
	//private ObjectInputStream objectIn;
	
	private boolean online;
	
	private Client(int portNumber) throws IOException {
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out);
		this.socket = new Socket(InetAddress.getLocalHost().getHostName(), portNumber);
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter("EOM");
		textOut = new PrintStream(socket.getOutputStream());
		//objectIn = new ObjectInputStream(socket.getInputStream());
		online = true;
	}
	
	private void sendText(String message) {
		textOut.print(message + "EOM");
		textOut.flush();
	}
	
	private String receiveText() {
		return textIn.next();
	}

	/*private State receiveObject() {
		State object = null;
		try {
			object = objectIn.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return object;
	}*/
	
	/*private void visit(StartTurnState currentState) {
		Player player = currentState.getCurrentPlayer();
		output.println("Current player: " + player.toString() + player.showSecretStatus());
		output.println("Choose an action to perform? " + currentState.getAvaiableAction());
		try {
			wakeUp(StateCache.getAction(scanner.nextLine().toLowerCase()));
		}
		catch(NullPointerException e) {
			wakeUp();
		}
	}*/
	
	private void run() {
		output.println(receiveText()); //ricevo l'ora di creazione
		output.println(receiveText());
		sendText(scanner.nextLine()); // invio il nome del giocatore
		output.println("Waiting others players to connect...");
		while(online) {
			output.println(receiveText()); //ricevo lo stato della mappa
			//visit(receiveObject());
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
