package it.polimi.ingsw.ps23.server.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransaction;
import it.polimi.ingsw.ps23.server.model.state.State;

/**
 * This interface defines a set of remote method which
 * can be invoked and executed by a remote connected RMI client.
 * @author Mirco Manzoni & Giuseppe Mascellaro
 *
 */
public interface ServerControllerInterface extends Remote {

	/**
	 * The remote client can invoke this method in order to register his RMI stub.
	 * This is fundamental to make RMI connection work.
	 * @return the server controller interface to be used by the client in order
	 * to reach remote methods into the server.
	 * @throws RemoteException if the remote server is unreachable
	 */
	public ServerControllerInterface setStub() throws RemoteException;
	
	/**
	 * Directly invokes the Controller class update method and returns immediately.
	 * @see {@link Controller#update()}
	 * @throws RemoteException if the remote server is unreachable
	 */
	public void wakeUpServer() throws RemoteException;
	
	/**
	 * Directly invokes the Controller class update method and returns immediately.
	 * @see {@link Controller#update(State)}
	 * @param state - state request to be set in the Controller class
	 * @throws RemoteException if the remote server is unreachable
	 */
	public void wakeUpServer(State state) throws RemoteException;
	
	/**
	 * Directly invokes the Controller class update method and returns immediately.
	 * @see {@link Controller#update(Action)}
	 * @param action - action parameters to be set in the Controller class
	 * @throws RemoteException if the remote server is unreachable
	 */
	public void wakeUpServer(Action action) throws RemoteException;
	
	public void wakeUpServer(MarketObject marketObject) throws RemoteException;
	
	public void wakeUpServer(MarketTransaction marketTransaction) throws RemoteException;
	
	public void wakeUpServer(SuperBonusGiver superBonusGiver) throws RemoteException;
	
	public void wakeUpServer(Exception e) throws RemoteException;

}
