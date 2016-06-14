package it.polimi.ingsw.ps23.server.model.state;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.ChangePermitsTile;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class ChangePermitsTileState extends ActionState {
	
	private Map<String, Deck> permitsMap;

	ChangePermitsTileState(String name) {
		super(name);
		permitsMap = new HashMap<>();
	}

	public Action createAction(String chosenRegionTile) {
		return new ChangePermitsTile(chosenRegionTile);
	}
	
	public String getPermitsMap() {
		return permitsMap.toString();
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		game.getGameMap().getPermitMap();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
	}

}
