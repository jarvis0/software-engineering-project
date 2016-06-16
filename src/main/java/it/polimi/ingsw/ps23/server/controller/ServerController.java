package it.polimi.ingsw.ps23.server.controller;

import it.polimi.ingsw.ps23.server.model.Model;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransation;
import it.polimi.ingsw.ps23.server.model.state.State;

public class ServerController extends Controller implements ServerControllerInterface {

	public ServerController(Model model) {
		super(model);
	}

	@Override
	public void wakeUpServer() {
		update();
	}

	@Override
	public void wakeUpServer(String offlinePlayer) {
		update(offlinePlayer);
	}

	@Override
	public void wakeUpServer(State state) {
		update(state);
	}

	@Override
	public void wakeUpServer(Action action) {
		update(action);
	}

	@Override
	public void wakeUpServer(MarketObject marketObject) {
		update(marketObject);
	}

	@Override
	public void wakeUpServer(MarketTransation marketTransation) {
		update(marketTransation);
	}

	@Override
	public void wakeUpServer(SuperBonusGiver superBonusGiver) {
		update(superBonusGiver);
	}

	
	

}
