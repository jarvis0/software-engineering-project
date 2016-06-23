package it.polimi.ingsw.ps23.server.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransation;
import it.polimi.ingsw.ps23.server.model.state.State;

public interface ServerControllerInterface extends Remote {

	public void wakeUpServer() throws RemoteException;
	
	public void wakeUpServer(State state) throws RemoteException;
	
	public void wakeUpServer(Action action) throws RemoteException;
	
	public void wakeUpServer(MarketObject marketObject) throws RemoteException;
	
	public void wakeUpServer(MarketTransation marketTransation) throws RemoteException;
	
	public void wakeUpServer(SuperBonusGiver superBonusGiver) throws RemoteException;
	
	public void wakeUpServer(Exception e) throws RemoteException;

	public ServerControllerInterface setStub() throws RemoteException;
	
}
