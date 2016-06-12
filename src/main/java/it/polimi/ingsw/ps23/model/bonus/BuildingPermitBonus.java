package it.polimi.ingsw.ps23.model.bonus;

import java.util.List;
import java.util.Map;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.Region;

public class BuildingPermitBonus extends Bonus implements SuperBonus {
	
	private static final int REGION_NAME_POSITION = 0;
	private static final int CHOSEN_TILE_POSITION = 1;

	boolean writeSecondOutput;
	Map<String, Region> regionMap;
	Deck permitDeck;
	
	
	public BuildingPermitBonus(String name) {
		super(name);
		writeSecondOutput = false;
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) throws InsufficientResourcesException {
		turnHandler.addSuperBonus(this);
		regionMap = game.getGameMap().getRegionMap();
	}

	@Override
	public String checkBonus(Player currentPlayer) {
		if(!writeSecondOutput) {
			return "You have enconutred a Building Permit Bonus on Nobility Track\n Choose the Region where to pick a permission card: " +regionMap.toString();
		}
		else {
			return "Choose a permission card (press 1 or 2): " + permitDeck.toString();
		}
		
	}

	@Override
	public void acquireSuperBonus(List<String> input, Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().pickPermitCard(game, turnHandler, game.getGameMap().getRegion(input.get(REGION_NAME_POSITION)), Integer.parseInt(input.get(CHOSEN_TILE_POSITION)) - 1);
	}

	public void selectRegion(String chosenRegion) {
		writeSecondOutput = true;
		permitDeck = ((GroupRegionalCity)regionMap.get(chosenRegion)).getPermissionDeckUp();
	}

}