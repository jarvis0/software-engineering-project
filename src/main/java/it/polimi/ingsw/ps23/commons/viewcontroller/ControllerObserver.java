package it.polimi.ingsw.ps23.commons.viewcontroller;

import java.util.List;

import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.market.MarketObject;
import it.polimi.ingsw.ps23.model.market.MarketTransation;
import it.polimi.ingsw.ps23.model.state.State;

public interface ControllerObserver {

	public abstract void update();
	public abstract void update(List<String> state);
	public abstract void update(State state);
	public abstract void update(Action action);
	public abstract void update(MarketObject marketObject);
	public abstract void update(MarketTransation marketTransation);
	
}
