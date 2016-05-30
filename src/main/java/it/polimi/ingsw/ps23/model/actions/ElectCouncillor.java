package it.polimi.ingsw.ps23.model.actions;

import java.util.List;

import it.polimi.ingsw.ps23.model.map.Councillor;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;
import it.polimi.ingsw.ps23.model.map.Region;

public class ElectCouncillor extends MainAction {
	
	private final static int COLOR_NAME_POSITION = 0;
	private final static int REGION_NAME_POSITION = 0;
	
	private Councillor councillorColor;
	private Region region;
	
	public ElectCouncillor(String name) {
		super(name);
	}
	
	@Override
	public void setParameters(List<String> parameters) {
		
		//for(Councillor freeCouncillors : freeCouncillorsList)
			
		return;
	}

}
