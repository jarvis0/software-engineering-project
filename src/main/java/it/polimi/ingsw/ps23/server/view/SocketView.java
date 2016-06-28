package it.polimi.ingsw.ps23.server.view;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.Connection;
import it.polimi.ingsw.ps23.server.model.state.State;

public abstract class SocketView extends View {
	
	private Connection connection;
	private String clientName;
	private State state;
	private boolean endGame;

	private boolean reconnected;
	
	public SocketView(String clientName, Connection connection) {
		this.connection = connection;
		this.clientName = clientName;
		this.connection = connection;
		endGame = false;
		reconnected = false;
	}

	public Connection getConnection() {
		return connection;
	}
	
	protected String getClientName() {
		return clientName;
	}
	
	protected String receive() {
		return connection.receive();
	}
	
	protected synchronized void pause() {
		try {
			wait();
		} catch (InterruptedException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot put " + clientName + " on hold.", e);
			Thread.currentThread().interrupt();
		}
	}
	
	public synchronized void threadWakeUp() {
		notifyAll();
	}

	public void setReconnected(boolean reconnected) {
		this.reconnected = reconnected;
	}
	
	protected void setState(State state) {
		this.state = state;
	}
	
	protected State getState() {
		return state;
	}
	
	protected void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}
	
	protected boolean isEndGame() {
		return endGame;
	}
	
	protected boolean isReconnected() {
		return reconnected;
	}
	
}
