package it.polimi.ingsw.ps23.client.socket;

import java.io.PrintStream;

abstract class RemoteView {

	private static final String NO_INPUT_TAG_OPEN = "<no_input>";
	private static final String NO_INPUT_TAG_CLOSE = "</no_input>";
	
	private SocketClient client;
	
	private PrintStream output;
	
	private boolean connectionTimedOut;
	
	private boolean endGame;

	protected RemoteView(SocketClient client, PrintStream output) {
		this.client = client;
		this.output = output;
		connectionTimedOut = false;
		endGame = false;
	}
	
	protected String getNoInputTagOpen() {
		return NO_INPUT_TAG_OPEN;
	}
	
	protected String getNoInputTagClose() {
		return NO_INPUT_TAG_CLOSE;
	}
	
	protected void setConnectionTimedOut() {
		connectionTimedOut = true;
	}
	
	public SocketClient getClient() {
		return client;
	}
	
	/**
	 * Sets the end of the game on the remote client.
	 */
	public void setEndGame() {
		endGame = true;
	}
	
	protected boolean getEndGame() {
		return endGame;
	}
	
	protected boolean getConnectionTimedOut() {
		return connectionTimedOut;
	}
	
	protected PrintStream getOutput() {
		return output;
	}
	
	protected abstract void run();
	
}
