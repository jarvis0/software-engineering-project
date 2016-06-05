package it.polimi.ingsw.ps23.model.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.actions.ChangePermitsTile;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.Region;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class ChangePermitsTileState extends ActionState {
	
	private Map<String, Deck> permitsMap;

	public ChangePermitsTileState(String name) {
		super(name);
		permitsMap = new HashMap<>();
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		for(Region region : game.getGameMap().getGroupRegionalCity()) {
			permitsMap.put(region.getName(), ((GroupRegionalCity) region).getPermissionDeckUp());
		}
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
	}

	public Action createAction(String chosenRegionTile) {
		return new ChangePermitsTile(chosenRegionTile);
	}
	
	public String getPermitsMap() {
		return permitsMap.toString();
	}

}
