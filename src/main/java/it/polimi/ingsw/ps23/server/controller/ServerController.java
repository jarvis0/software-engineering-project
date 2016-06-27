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

/**
 * This class is an extension for the classic MVC controller.
 * In particular, it act like a remote controller for connected
 * RMI clients.
 * @author Mirco Manzoni & Giuseppe Mascellaro
 *
 */
public class ServerController extends Controller implements ServerControllerInterface {

	private static final String POLICY_NAME = "COFServer";

	/**
	 * Saves the model which is a remote MVC component.
	 * @param model - game model representation of data and business logical functions
	 * of the whole application
	 */
	public ServerController(Model model) {
		super(model);
	}

	@Override
	public void wakeUpServer() {
		update();
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
	public void wakeUpServer(Exception e) throws RemoteException {
		update(e);		
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
