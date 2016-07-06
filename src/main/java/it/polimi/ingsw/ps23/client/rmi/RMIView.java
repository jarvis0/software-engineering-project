package it.polimi.ingsw.ps23.client.rmi;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.ServerInterface;
import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.state.EndGameState;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.State;
import it.polimi.ingsw.ps23.server.view.View;

abstract class RMIView extends View {
	
	private static final String CANNOT_REACH_SERVER_PRINT = "Cannot reach remote server.";
	
	private ServerControllerInterface controllerInterface;
	private ServerInterface server;
	private String clientName;
	private State state;
	private boolean waiting;
	
	protected RMIView(String clientName) {
		this.clientName = clientName;
		waiting = false;
	}

	abstract void infoMessage(String message);
	
	abstract void setMapType(String mapType);
	
	protected State getState() {
		return state;
	}
	
	protected void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}
	
	protected String getClientName() {
		return clientName;
	}
	
	protected void setNewClientName(String clientName) {
		this.clientName = clientName;
	}
	
	protected void setServer(ServerInterface server) {
		this.server = server;
	}

	protected void setServerEndGame() {
		try {
			server.endGame();
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
		}
	}
	
	protected void setController(ServerControllerInterface controllerInterface) {
		this.controllerInterface = controllerInterface;
	}

	protected ServerControllerInterface getControllerInterface() {
		return controllerInterface;
	}
	
	protected synchronized void pause() {
		try {
			wait();
		} catch (InterruptedException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot put " + clientName + " on hold.", e);
			Thread.currentThread().interrupt();
		}
	}
	/**
	 * Wake up the RMI view if it's waiting in a wait method.
	 * @see {@link #pause()}
	 */
	public synchronized void resume() {
		notifyAll();
	}
	
	protected void sendAction(Action action) {
		try {
			getControllerInterface().wakeUpServer(action);
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
		}
	}
	
	private boolean waitResumeCondition() {
		return state instanceof StartTurnState || state instanceof MarketBuyPhaseState || state instanceof MarketOfferPhaseState || state instanceof EndGameState;
	}

	@Override
	public void update(State state) {
		this.state = state;
		if(waitResumeCondition() && waiting) {
			resume();
			waiting = false;
		}
	}

	abstract void setEndGame();

}
