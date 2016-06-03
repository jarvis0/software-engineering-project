package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

import it.polimi.ingsw.ps23.controller.Controller;
import it.polimi.ingsw.ps23.model.Model;

public class Server {
	
	private static final int PORT = 12345;
	
	private final ServerSocket serverSocket;

	private Controller controller;
	private Model model;
	
	private Server() throws IOException {
		serverSocket = new ServerSocket(PORT);
	}
	
	private void newConnection() {
		try {
			System.out.println("Waiting for connections...");
			Socket newSocket = serverSocket.accept();
			System.out.println("I've received a new Client connection.");
			new Connection(newSocket).start();
		} catch (IOException e) {
			System.out.println("Connection error.");
		}
	}
	
	public void run(){
		newConnection();
		newConnection();
		//Timer timer = new Timer();
		//this.wa
		while(true) {
			newConnection();
			//model = new Model();
			//controller = new Controller(model);
		}
	}
	
	public static void main(String[] args) {
		Server server;
		try {
			server = new Server();
			server.run();
		} catch (IOException e) {
			System.err.println("Cannot initialize Server.");
		}		
	}
	
}
