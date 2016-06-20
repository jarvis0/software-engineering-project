package it.polimi.ingsw.ps23.server.commons.modelview;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.client.rmi.ClientInterface;
import it.polimi.ingsw.ps23.server.GameInstance;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.State;

public class ModelObservable {
	
	private List<ViewObserver> observers;
	private Map<String, ClientInterface> rmiObservers;
	private String currentPlayer;
	private GameInstance gameInstance;
	private Timer timer;
	private int timeout;
	
	public ModelObservable() {
		observers = new ArrayList<>();
		rmiObservers = new HashMap<>();
	}
	
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setUpRMI(GameInstance gameInstance, int timeout) {
		this.gameInstance = gameInstance;
		this.timeout = timeout;
	}
	
	public void attach(ViewObserver observer) {
		observers.add(observer);
	}

	public void detach(ViewObserver observer) {
		observers.remove(observer);
	}
	
	public void attachRMIClient(String rmiPlayerName, ClientInterface client) {
		rmiObservers.put(rmiPlayerName, client);
	}
	
	public void detachRMIClient() {
		rmiObservers.remove(currentPlayer);
	}
	
	public void sendRMIInfoMessage(String message) {
		for(ClientInterface client : rmiObservers.values()) {
			try {
				client.infoMessage(message);
			} catch (RemoteException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach the RMI remote client.", e);
			}
		}
	}

	private void notifyRMIObservers(State state) {
		Set<Entry<String, ClientInterface>> rmiPlayers = rmiObservers.entrySet();
		for(Entry<String, ClientInterface> rmiPlayer : rmiPlayers) {
			if(rmiPlayer.getKey() == currentPlayer) {
				timer = new Timer();
				timer.schedule(new RMITimeoutTask(gameInstance, timer), timeout * 1000L);
			}
			try {
				rmiPlayer.getValue().changeState(state);
			} catch (RemoteException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach the RMI remote client.", e);
			}
		}
	}

	private void findCurrentPlayer(State state) {
		if(state instanceof StartTurnState) {
			currentPlayer = ((StartTurnState) state).getCurrentPlayer().getName();
		}
		else {
			if(state instanceof MarketBuyPhaseState) {
				currentPlayer = ((MarketBuyPhaseState) state).getPlayerName();
			}
			else {
				if(state instanceof MarketOfferPhaseState) {
					currentPlayer = ((MarketOfferPhaseState) state).getPlayerName();
				}
			}
		}
	}
	
	private void notifyAllObservers(State state) {
		findCurrentPlayer(state);
		for(ViewObserver observer : observers) {
			observer.update(state);
		}
		if(!rmiObservers.isEmpty()) {
			notifyRMIObservers(state);
		}
	}
	
	public void wakeUp(State state) {
		notifyAllObservers(state);
	}

}
