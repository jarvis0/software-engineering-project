package it.polimi.ingsw.ps23.server.commons.modelview;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
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
	
	public void wakeUp(State state) {
		notifyAllObservers(state);
	}
	
	private void notifyAllObservers(State state) {
		for(ViewObserver observer : observers) {
			observer.update(state);
		}
		for(ClientInterface client : rmiObservers) {
			try {
				client.changeState(state);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
}