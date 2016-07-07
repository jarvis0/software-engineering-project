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

/**
 * Provides all needed to make both MVC and Observer/Observable
 * pattern work.
 * @author Giuseppe Mascellaro
 *
 */
public class ModelObservable {
	
	private List<ViewObserver> observers;
	private Map<String, ClientInterface> rmiObservers;
	private String currentPlayer;
	private GameInstance gameInstance;
	private Timer timer;
	private int timeout;
	
	/**
	 * Initializes observers which are of two kinds:
	 * classic MVC observers and RMI observers.
	 */
	public ModelObservable() {
		observers = new ArrayList<>();
		rmiObservers = new HashMap<>();
		timer = null;
	}
	
	/**
	 * Reset the current disconnecting player timer.
	 */
	public void resetTimer() {
		timer = null;
	}
	
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Sets up RMI resources to make remote MVC work.
	 * @param gameInstance - created game instance for a set of players
	 * @param timeout - RMI connection timeout in seconds
	 */
	public void setUpRMI(GameInstance gameInstance, int timeout) {
		this.gameInstance = gameInstance;
		this.timeout = timeout;
	}
	
	/**
	 * Adds to classic MVC pattern observers, the specified view.	
	 * <p>
	 * Permits to notify the view with model updates.
	 * @param observer - the views you want to notify when a model update
	 * occur
	 */
	public void attach(ViewObserver observer) {
		observers.add(observer);
	}

	/**
	 * Deletes from the list of classic MVC pattern, the specified view.
	 * <p>
	 * No more model updates will be notified to the specified view.
	 * @param observer - the view you want to stop being notified from the model.
	 */
	public void detach(ViewObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Adds to RMI MVC pattern observers, the specified remote view which is a client
	 * interface.
	 * <p>
	 * Permits to notify the remote RMI view with model updates.
	 * @param rmiPlayerName - game player name to be added to the RMI observers list and mapped
	 * with the specified remote client interface
	 * @param client - remote client interface to be added to the RMI observers list
	 */
	public void attachRMIClient(String rmiPlayerName, ClientInterface client) {
		rmiObservers.put(rmiPlayerName, client);
	}
	
	/**
	 * Deletes from the list of RMI MVC pattern observers, the current player
	 * which is gone offline due to game connection timeout.
	 */
	public void detachRMIClient() {
		rmiObservers.remove(currentPlayer);
	}
	
	/**
	 * Sends a info message to a specific remote RMI client.
	 * @param client - who will receive the info message
	 * @param message - the info message to be sent
	 */
	public void rmiInfoMessage(ClientInterface client, String message) {
		try {
			client.infoMessage(message);
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach the RMI remote client.", e);
		}
	}
	
	/**
	 * Sends a RMI info message to all online players for this game.
	 * @param message - message to be received from the online RMI players
	 */
	public void sendRMIInfoMessage(String message) {
		for(ClientInterface client : rmiObservers.values()) {
			rmiInfoMessage(client, message);
		}
	}

	private void notifyRMIObservers(State state) {
		Set<Entry<String, ClientInterface>> rmiPlayers = rmiObservers.entrySet();
		for(Entry<String, ClientInterface> rmiPlayer : rmiPlayers) {
			if(rmiPlayer.getKey() == currentPlayer) {
				if(timer == null) {
					timer = new Timer();
					timer.schedule(new RMITimeoutTask(gameInstance, rmiPlayer.getValue(), timer), timeout * 1000L);
				}
				else {
					timer.cancel();
					timer = null;
				}
			}
			try {
				rmiPlayer.getValue().changeState(state);
			} catch (RemoteException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot reach the RMI remote client.", e);
			}
		}
	}

	private void setCurrentPlayer(State state) {
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
		setCurrentPlayer(state);
		for(ViewObserver observer : observers) {
			observer.update(state);
		}
		if(!rmiObservers.isEmpty()) {
			notifyRMIObservers(state);
		}
	}
	
	/**
	 * Notifies a new game state to all observers related to this MVC pattern model.
	 * <p>
	 * Called by a model update method.
	 * @param state - new game state to be send to all attached view: for both
	 * remote and classic MVC pattern.
	 */
	public void wakeUp(State state) {
		notifyAllObservers(state);
	}

}
