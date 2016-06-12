package it.polimi.ingsw.ps23.model.state;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.map.Region;
import it.polimi.ingsw.ps23.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.model.map.regions.Council;
import it.polimi.ingsw.ps23.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.ElectCouncillor;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public class ElectCouncillorState extends ActionState {
	
	private FreeCouncillorsSet freeCouncillors;
	private Map<String, Council> councilsMap;
	
	public ElectCouncillorState(String name) {
		super(name);
		councilsMap = new HashMap<>();
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		freeCouncillors = game.getFreeCouncillors();
		councilsMap.put("King", game.getKing().getCouncil());
		for (Region region : game.getGameMap().getGroupRegionalCity()) {
			councilsMap.put(region.getName(), ((GroupRegionalCity) region).getCouncil());
		}
	}

	public String getFreeCouncillors() {
		return freeCouncillors.toString();
	}

	public String getCouncilsMap() {
		return councilsMap.toString();
	}	
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
	}

	public Action createAction(String chosenCouncillor, String chosenBalcony) {
		return new ElectCouncillor(chosenCouncillor, getCouncilMap(chosenBalcony));
	}
	
	Council getCouncilMap(String chosenBalcony) {
		return councilsMap.get(chosenBalcony);
	}
}
