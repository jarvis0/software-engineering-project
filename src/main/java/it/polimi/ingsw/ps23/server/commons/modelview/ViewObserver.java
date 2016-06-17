package it.polimi.ingsw.ps23.server.commons.modelview;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps23.server.model.state.State;

@FunctionalInterface
public interface ViewObserver extends Remote {

	public void update(State state) throws RemoteException;
	
}
