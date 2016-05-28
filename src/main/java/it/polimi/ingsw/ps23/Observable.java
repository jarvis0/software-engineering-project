package it.polimi.ingsw.ps23;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public abstract class Observable {
	
	private List<Observer> observers;
	
	public Observable(){
		observers=new ArrayList<Observer>();
	}
	public void registerObserver(Observer o){
		observers.add(o);
	}
	public void unregisterObserver(Observer o){
		this.observers.remove(o);
	}
	public void notifyObservers(){
		for(Observer o: this.observers){
			o.update(null, o);
		}
	}
	public <C> void notifyObservers(C c){
		for(Observer o: this.observers){
			o.update(null, c);
		}
	}
	
}
