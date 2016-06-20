package it.polimi.ingsw.ps23.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;

@FunctionalInterface
public interface ServerInterface extends Remote {

	public void registerRMIClient(String name, ClientInterface client) throws RemoteException;
		
}
