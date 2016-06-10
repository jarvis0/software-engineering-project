package it.polimi.ingsw.ps23.commons.viewcontroller;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.model.market.MarketObject;
import it.polimi.ingsw.ps23.model.market.MarketTransation;
import it.polimi.ingsw.ps23.model.state.State;

public class ViewObservable {
	
	private List<ControllerObserver> observers;
	
	public ViewObservable() {
		observers = new ArrayList<>();
	}
	
	public void attach(ControllerObserver observer) {
		observers.add(observer);
	}
	
	public void wakeUp(List<String> state) {
		notifyAllObservers(state);
	}
	
	private void notifyAllObservers(List<String> state) {
		for(ControllerObserver observer : observers) {
			observer.update(state);
		}
	}
	
	public void wakeUp() {
		notifyAllObservers();
	}
	
	private void notifyAllObservers() {
		for(ControllerObserver observer : observers) {
			observer.update();
		}
	}
	
	public void wakeUp(State state) {
		notifyAllObservers(state);
	}
	
	private void notifyAllObservers(State state) {
		for(ControllerObserver observer : observers) {
			observer.update(state);
		}
	}
	
	public void wakeUp(Action action) {
		notifyAllObservers(action);
	}
	
	private void notifyAllObservers(Action action) {
		for(ControllerObserver observer : observers) {
			observer.update(action);
		}
	}
	
	public void wakeUp(MarketObject marketObject) {
		notifyAllObservers(marketObject);
	}
	
	private void notifyAllObservers(MarketObject marketObject) {
		for(ControllerObserver observer : observers) {
			observer.update(marketObject);
		}
	}
	
	public void wakeUp(MarketTransation marketTransation) {
		notifyAllObservers(marketTransation);
	}
	
	private void notifyAllObservers(MarketTransation marketTransation) {
		for(ControllerObserver observer : observers) {
			observer.update(marketTransation);
		}
	}
	
	public void wakeUp(SuperBonusGiver superBonusGiver) {
		notifyAllObservers(superBonusGiver);
	}
	
	private void notifyAllObservers(SuperBonusGiver superBonusGiver) {
		for(ControllerObserver observer : observers) {
			observer.update(superBonusGiver);
		}
	}

}