package it.polimi.ingsw.ps23.server.commons.viewcontroller;

import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransation;
import it.polimi.ingsw.ps23.server.model.state.State;

public class ViewObservable {
	
	private ControllerObserver observer;
	
	public void attach(ControllerObserver observer) {
		this.observer = observer;
	}
	
	public void wakeUp() {
		observer.update();
	}
	
	public void wakeUp(State state) {
		observer.update(state);
	}
	
	public void wakeUp(Action action) {
		observer.update(action);
	}
	
	public void wakeUp(MarketObject marketObject) {
		observer.update(marketObject);
	}
	
	public void wakeUp(MarketTransation marketTransation) {
		observer.update(marketTransation);
	}
	
	public void wakeUp(SuperBonusGiver superBonusGiver) {
		observer.update(superBonusGiver);
	}
	
	public void wakeUp(Exception e) {
		observer.update(e);
	}

}