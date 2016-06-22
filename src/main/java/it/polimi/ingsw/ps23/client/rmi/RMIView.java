package it.polimi.ingsw.ps23.client.rmi;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.view.View;

public abstract class RMIView extends View {
	
	private ServerControllerInterface controllerInterface;
	private String clientName;
	
	protected RMIView(String clientName) {
		this.clientName = clientName;
	}
	
	protected String getClientName() {
		return clientName;
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
	
	protected synchronized void resume() {
		notifyAll();
	}
	
}
