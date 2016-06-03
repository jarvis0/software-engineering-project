package it.polimi.ingsw.ps23.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Connection extends Thread {
	
	private final Socket socket;
	private BufferedReader socketIn;
	private BufferedWriter socketOut;

	public Connection(Socket socket) {
		super();
		this.socket = socket;
	}
	
	private void writeToClient(String message) {
		try {
			socketOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			socketOut.write(message + "\n");
			socketOut.flush();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Client error.");
		}
	}
	
	private String readFromClient() {
		String message = new String();
		try {
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			message = socketIn.readLine();
			socketIn.close();
		} catch (IOException e) {
			System.out.println("Client error.");
		}
		return message;
	}
	
	@Override
	public void run(){
		writeToClient("Connection established at " + new Date().toString());
	}
	
}
