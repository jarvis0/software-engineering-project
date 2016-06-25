package it.polimi.ingsw.ps23.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;

/**
 * This interface provides public remote methods to be invoked by the client
 * into the server application.
 * @author Giuseppe Mascellaro & Mirco Manzoni
 *
 */
@FunctionalInterface
public interface ServerInterface extends Remote {

	/**
	 * Invoke remote method which make the client register
	 * to the server and to join to RMI waiting connections.
	 * @param name - name of the player which has to be in a valid format
	 * @param client - client interface to make the server invoke remote methods
	 * into the client application
	 * @throws RemoteException if the remote server is unreachable
	 */
	public void registerRMIClient(String name, ClientInterface client) throws RemoteException;
		
}
