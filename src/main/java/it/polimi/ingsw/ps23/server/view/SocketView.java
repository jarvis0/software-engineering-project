package it.polimi.ingsw.ps23.server.view;

import it.polimi.ingsw.ps23.server.Connection;

public abstract class SocketView extends View {
	
	public abstract Connection getConnection();
	
	public abstract void threadWakeUp();

	public abstract void setReconnected(boolean reconnected);
	
}
