package it.polimi.ingsw.ps23.commons.viewcontroller;

import java.util.ArrayList;
import java.util.List;

public class ViewObservable {
	
	private List<ControllerObserver> observers;
	
	public ViewObservable() {
		observers = new ArrayList<>();
	}
	
	public void attach(ControllerObserver observer){
		observers.add(observer);
	}

	public void setState(List<String> state) {
		notifyAllObservers(state);
	}
	
	//all ?
	public void notifyAllObservers(List<String> state) {
		for(ControllerObserver observer : observers) {
			observer.update(state);
		}
	}
	
}