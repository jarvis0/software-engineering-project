package it.polimi.ingsw.ps23.server.commons.modelview;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.State;

public class ModelObservable {
	
	private List<ViewObserver> observers;
	private List<ClientInterface> rmiObservers;
	
	public ModelObservable() {
		observers = new ArrayList<>();
		rmiObservers = new ArrayList<>();
	}
	
	public void attach(ViewObserver observer) {
		observers.add(observer);
	}

	public void attachStub(ClientInterface client) {
		rmiObservers.add(client);
	}
	
	public void detach(ViewObserver observer) {
		observers.remove(observer);
	}
	
	public void sendRMIInfoMessage(String message) {
		for(ClientInterface client : rmiObservers) {
			try {
				client.infoMessage(message);
			} catch (RemoteException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach the RMI remote client.", e);
			}
		}
	}
	
	public void wakeUp(State state) {
		notifyAllObservers(state);
	}
	
	private void notifyAllObservers(State state) {
		for(ViewObserver observer : observers) {
			observer.update(state);
		}
		for(ClientInterface client : rmiObservers) {
			try {
				if(state instanceof StartTurnState) {
					((StartTurnState) state).getCurrentPlayer();
				}
				client.changeState(state);
			} catch (RemoteException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach the RMI remote client.", e);
			}
		}
	}
	
}