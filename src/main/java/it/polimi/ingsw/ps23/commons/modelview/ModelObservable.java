package it.polimi.ingsw.ps23.commons.modelview;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.state.State;

public class ModelObservable {
	
	private List<ViewObserver> observers;
	
	public ModelObservable() {
		observers = new ArrayList<>();
	}
	
	public void attach(ViewObserver observer){
		observers.add(observer);
	}

	public void wakeUp(State state) {
		notifyAllObservers(state);
	}
	
	private void notifyAllObservers(State state) {
		for(ViewObserver observer : observers) {
			observer.update(state);
		}
	}
	
}