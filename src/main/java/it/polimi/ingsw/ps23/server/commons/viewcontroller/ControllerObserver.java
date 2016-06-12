package it.polimi.ingsw.ps23.server.commons.viewcontroller;

import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.model.market.MarketObject;
import it.polimi.ingsw.ps23.model.market.MarketTransation;
import it.polimi.ingsw.ps23.model.state.State;

public interface ControllerObserver {

	public void update();
	
	public void update(State state);
	
	public void update(Action action);
	
	public void update(MarketObject marketObject);
	
	public void update(MarketTransation marketTransation);
	
	public void update(SuperBonusGiver superBonusGiver);
	
}
