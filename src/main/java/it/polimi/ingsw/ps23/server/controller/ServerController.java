package it.polimi.ingsw.ps23.server.controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.model.Model;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransation;
import it.polimi.ingsw.ps23.server.model.state.State;
//TODO va in questo package?
public class ServerController extends Controller implements ServerControllerInterface {

	private static final String POLICY_NAME = "COFServer";

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

	@Override
	public ServerControllerInterface setStub() {
		try {
			ServerControllerInterface stub = (ServerControllerInterface) UnicastRemoteObject.exportObject(this, 0);
			Naming.rebind(POLICY_NAME, stub);
			return stub;
		} catch (RemoteException | MalformedURLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Cannot rebind the RMI registry.", e);
		}
		return null;
	}

}
