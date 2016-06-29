package it.polimi.ingsw.ps23.client.socket;

abstract class RemoteView {

	private SocketClient client;
	
	private boolean connectionTimedOut;

	RemoteView(SocketClient client) {
		this.client = client;
	}
	
	void setConnectionTimedOut() {
		connectionTimedOut = true;
	}
	
	SocketClient getClient() {
		return client;
	}
	
	boolean getConnectionTimedOut() {
		return connectionTimedOut;
	}
	
	abstract void run();
	
}
