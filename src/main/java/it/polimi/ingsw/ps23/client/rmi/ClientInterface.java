package it.polimi.ingsw.ps23.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	
	public void notify(String message) throws RemoteException;
	
}