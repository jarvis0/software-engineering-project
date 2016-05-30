package it.polimi.ingsw.ps23.commons.modelview;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.Game;

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
	
	public void setState(Game game) {
		notifyAllObservers(game);
	}
	
	public void notifyAllObservers(Game game) {
		for(ViewObserver observer : observers) {
			observer.update(game);
		}
	}
	
}