package it.polimi.ingsw.ps23.commons.modelview;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.state.Context;

public class ModelObservable {
	
	private List<ViewObserver> observers;
	
	public ModelObservable() {
		observers = new ArrayList<>();
	}
	
	public void attach(ViewObserver observer){
		observers.add(observer);
	}

	public void setState() {
		notifyAllObservers();
	}
	
	public void notifyAllObservers() {
		for(ViewObserver observer : observers) {
			observer.update();
		}
	}
	
	public void setState(Context context) {
		notifyAllObservers(context);
	}
	
	public void notifyAllObservers(Context context) {
		for(ViewObserver observer : observers) {
			observer.update(context);
		}
	}
	
}