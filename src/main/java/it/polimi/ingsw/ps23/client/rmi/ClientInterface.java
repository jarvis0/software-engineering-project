package it.polimi.ingsw.ps23.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.model.state.State;

/**
 * This interface provides public remote methods to be invoked by the server
 * into the client application.
 * @author Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public interface ClientInterface extends Remote {
	
	/**
	 * Invokes a remote method which prints the specified message.
	 * @param message - message to be send to the client application
	 * @throws RemoteException if the remote client is unreachable
	 */
	public void infoMessage(String message) throws RemoteException;
	
	/**
	 * Invokes a remote method which sets the new specified game state.
	 * @param currentState - new game state to be set
	 * @throws RemoteException if the remote client is unreachable
	 */
	public void changeState(State currentState) throws RemoteException;
	
	/**
	 * Invokes a remote method which sets the specified server controller to
	 * make the client reach his game entry point.
	 * @param controller - bridge to the game into the server
	 * @throws RemoteException if the remote client is unreachable
	 */
	public void setController(ServerControllerInterface controller) throws RemoteException;
	
	/**
	 * Invokes a remote method which sets a new game player name so as to avoid
	 * game names conflicts.
	 * @param newName - new name to be set for the client
	 * @throws RemoteException if the remote client is unreachable
	 */
	public void changeName(String newName) throws RemoteException;
	
}
