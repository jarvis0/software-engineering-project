package it.polimi.ingsw.ps23.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.model.state.State;

public interface ClientInterface extends Remote {
	
	public void infoMessage(String message) throws RemoteException;
	
	public void changeState(State currentState) throws RemoteException;
	
	public void setController(ServerControllerInterface controller) throws RemoteException;
	
}
