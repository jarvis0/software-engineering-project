package it.polimi.ingsw.ps23.commons.remote;

import java.util.ArrayList;
import java.util.List;

public class RemoteObservable {
	
	private List<RemoteObserver> observers = new ArrayList<>();
	
	public void register(RemoteObserver observer){
		synchronized (observers) {
			observers.add(observer);			
		}		
	}
	
	public void deregister(RemoteObserver observer){
		synchronized (observers) {
			observers.remove(observer);
		}
	}
	
	protected void remoteWakeUp(){
		synchronized (observers) {
			for(RemoteObserver observer : observers){
				observer.update();
			}
		}
	}

}
