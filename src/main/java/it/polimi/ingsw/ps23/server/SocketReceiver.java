package it.polimi.ingsw.ps23.server;

class SocketReceiver implements Runnable {

	private Connection connection;
	
	private int timeout;
		
	SocketReceiver(Connection connection, int timeout) {
		this.connection = connection;
		this.timeout = timeout;
	}
	
	@Override
	public void run() {
		
	}

}
