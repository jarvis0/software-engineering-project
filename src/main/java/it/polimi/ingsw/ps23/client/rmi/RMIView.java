package it.polimi.ingsw.ps23.client.rmi;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.model.state.EndGameState;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.State;
import it.polimi.ingsw.ps23.server.view.View;

abstract class RMIView extends View {
	
	private ServerControllerInterface controllerInterface;
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

}
