package it.polimi.ingsw.ps23.server.view;

import it.polimi.ingsw.ps23.server.Connection;

public abstract class SocketView extends View {

	public abstract Connection getConnection();

	public abstract void threadWakeUp();

	public abstract String getClientName();

	public abstract void setPlayerOffline();

	public abstract void sendNoInput(String string);
	
}
