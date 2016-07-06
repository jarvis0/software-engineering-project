package it.polimi.ingsw.ps23.server.view;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.Connection;
import it.polimi.ingsw.ps23.server.model.state.State;
/**
 * Provides methods to manage the connections and the transfer of data from {@link Server}.
 * @author Giuseppe Mascellaro
 *
 */
public abstract class SocketView extends View {
	
	private Connection connection;
	private String clientName;
	private State state;
	private boolean endGame;
	private boolean reconnected;
	/**
	 * COnstructs the objects assign it the name and its connection.
	 * @param clientName - the name of the client
	 * @param connection - the connection
	 */
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

	protected State getState() {
		return state;
	}

	protected void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}

	public void setReconnected(boolean reconnected) {
		this.reconnected = reconnected;
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
	/**
	 * Wake up this thread from a pause methods.
	 */
	public synchronized void threadWakeUp() {
		notifyAll();
	}

	@Override
	public void update(State state) {
		this.state = state;
	}

	@Override
	public synchronized void run() {
		if(reconnected) {
			pause();
		}
		do {
			getState().acceptView(this);
			if(state.arePresentException()) {
				connection.sendNoInput(state.getExceptionString());
			}
		} while(!endGame);
	}

}
