package it.polimi.ingsw.ps23;

import it.polimi.ingsw.ps23.controller.ElectCouncillor;

public interface Observer {
	
	public void update();
	
	public void update(ElectCouncillor choice);

}
