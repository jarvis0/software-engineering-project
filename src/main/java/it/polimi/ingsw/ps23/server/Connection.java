package it.polimi.ingsw.ps23.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.view.View;

public class Connection implements Runnable {
	
	private Server server;
	private Socket socket;
	private Scanner textIn;
	private PrintStream textOut;
	
	private int timeout;

	private ExecutorService executor;
	
	private boolean started;

	private View view;
	
	private Logger logger;
	
	Connection(Server server, Socket socket, int timeout) throws IOException {
		super();
		this.server = server;
		this.socket = socket;
		this.timeout = timeout;
		textIn = new Scanner(socket.getInputStream());
		textIn.useDelimiter("EOM");
		textOut = new PrintStream(socket.getOutputStream());
		executor = Executors.newCachedThreadPool();
		logger = Logger.getLogger(this.getClass().getName());
		started = false;
	}
	
	Server getServer() {
		return server;
	}
	
	public void send(String message) {
 		textOut.print(message + "EOM");
 		textOut.flush();
 	}
 	
 	public String receive() {
 		//SocketReceiver socketReceiver = new SocketReceiver(this, timeout);
 		//executor.submit(new SocketReceiver(this, timeout));
		Timer timer = new Timer();
		timer.schedule(new TimeoutTask(this), timeout * 1000L);
		String message = textIn.next();
 		timer.cancel();
 		return message;
 	}

	synchronized void closeConnection() {
		/*final String message = "\nConnection timed out. You have been kicked out.";
		new Thread(new Runnable() {
			@Override
			public void run() {
				send(message);
			}
		}).start();*/
		send("\nConnection timed out. You have been kicked out.");
		try {
			socket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Cannot close the connection.", e);
		}
	}
	
	void close() {
		closeConnection();
		server.deregisterConnection(this);
	}

	synchronized void setStarted() {
		started = true;
	}
	
	synchronized void startGame() {
		notifyAll();
	}
	
	private synchronized void initialization() {
		boolean loop = true;
		if(!started) {
			while(loop) {
				try {
					wait();
					loop = false;
				} catch (InterruptedException e) {
					logger.log(Level.SEVERE, "Cannot put connection " + this + " on hold.", e);
					Thread.currentThread().interrupt();
				}
			}
		}
		else {
			server.initializeGame();
		}
	}

	void setView(View view) {
		this.view = view;
	}
	
	@Override
	public void run() {
		server.joinToWaitingList(this, receive());
		initialization();
		view.run();
		close();
	}

}
