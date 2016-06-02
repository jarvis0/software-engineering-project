package it.polimi.ingsw.ps23.model.state;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.HandDeck;
import it.polimi.ingsw.ps23.model.PoliticHandDeck;
import it.polimi.ingsw.ps23.model.actions.AcquireBusinessPermitTile;
import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.Region;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class AcquireBusinessPermitTileState extends ActionState {

	private HandDeck politicHandDeck;
	private Map<String, Council> councilsMap;
	private Map<String, Deck> buildingPermits;
	
	public AcquireBusinessPermitTileState(String name) {
		super(name);
		councilsMap = new HashMap<>();
		buildingPermits = new HashMap<>();
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		politicHandDeck = new PoliticHandDeck(game.getCurrentPlayer().getPoliticHandDeck().getCards());
		for (Region region : game.getGameMap().getGroupRegionalCity()) {
			councilsMap.put(region.getName(), ((GroupRegionalCity) region).getCouncil());
			buildingPermits.put(region.getName(), ((GroupRegionalCity) region).getPermissionDeck());
		}
	}

	public String getPoliticHandDeck() {
		return politicHandDeck.toString();
	}
	
	public String getCouncilsMap() {
		return councilsMap.toString();
	}
	
	public int getAvailablePoliticCardsNumber(String chosenCouncil) {
		politicHandDeck = ((PoliticHandDeck)politicHandDeck).getAvailableCards(councilsMap.get(chosenCouncil));
		return politicHandDeck.getHandSize();
	}
		
	public String getAvailablePermitTile(String chosenCouncil) {
		return buildingPermits.get(chosenCouncil).toString();
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
	public Action createAction() {
		return new AcquireBusinessPermitTile();
	}

}
