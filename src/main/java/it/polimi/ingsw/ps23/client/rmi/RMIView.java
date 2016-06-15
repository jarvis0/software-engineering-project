package it.polimi.ingsw.ps23.client.rmi;

import it.polimi.ingsw.ps23.server.view.View;

public abstract class RMIView extends View {
	
	RMIClient client;
	
	RMIView(RMIClient client) {
		this.client = client;
	}
}
