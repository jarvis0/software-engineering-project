package it.polimi.ingsw.ps23.server.model.state;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.ChangePermitsTile;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class ChangePermitsTileState extends QuickActionState {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6241571475175211290L;
	private Map<String, Deck> regionalPermissionDecks;

	ChangePermitsTileState(String name) {
		super(name);
		regionalPermissionDecks = new HashMap<>();
	}

	public Action createAction(String chosenRegionTile) {
		return new ChangePermitsTile(chosenRegionTile);
	}
	
	public String printRegionalPermissionDecks() {
		return regionalPermissionDecks.toString();
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		regionalPermissionDecks = game.getGameMap().getPermissionCardsUp();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
	}

}
