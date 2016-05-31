package it.polimi.ingsw.ps23.commons.viewcontroller;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.state.Context;

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
	
	public void setState() {
		notifyAllObservers();
	}
	
	public void notifyAllObservers() {
		for(ControllerObserver observer : observers) {
			observer.update();
		}
	}
	
	public void setState(Context context) {
		notifyAllObservers(context);
	}
	
	public void notifyAllObservers(Context context) {
		for(ControllerObserver observer : observers) {
			observer.update(context);
		}
	}
	
}