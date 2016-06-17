package it.polimi.ingsw.ps23.client.rmi;

import it.polimi.ingsw.ps23.server.controller.ServerControllerInterface;
import it.polimi.ingsw.ps23.server.view.View;

public abstract class RMIView extends View {
	
	private RMIClient client;
	private ServerControllerInterface controllerInterface;
	
	RMIView(RMIClient client) {
		this.client = client;
	}
	
	void setController(ServerControllerInterface controllerInterface) {
		this.controllerInterface = controllerInterface;
	}
	
	protected ServerControllerInterface getControllerInterface() {
		return controllerInterface;
	}
}
