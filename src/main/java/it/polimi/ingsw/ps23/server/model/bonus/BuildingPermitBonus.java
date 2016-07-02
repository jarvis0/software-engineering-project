package it.polimi.ingsw.ps23.server.model.bonus;

import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.player.Player;
/**
 * Provides methods to take the specified bonus
 * @author Alessandro Erba
 *
 */
public class BuildingPermitBonus extends Bonus implements SuperBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 71835727198785851L;
	private static final int REGION_NAME_POSITION = 0;
	private static final int CHOSEN_TILE_POSITION = 1;

	private boolean writeSecondOutput;
	private Map<String, Region> regionMap;
	private Deck permitDeck;
	
	/**
	 * Construct the bonus to be cloned by {@link BonusCache} and initialize all local variables to default values.
	 * @param name - the name of the bonus
	 */
	public BuildingPermitBonus(String name) {
		super(name);
		writeSecondOutput = false;
	}
	
	public void selectRegion(String chosenRegion) throws InvalidRegionException {
		if(((GroupRegionalCity)regionMap.get(chosenRegion)).getPermitTilesUp() == null) {
			throw new InvalidRegionException();
		}
		permitDeck = ((GroupRegionalCity)regionMap.get(chosenRegion)).getPermitTilesUp();
		writeSecondOutput = true;		
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		turnHandler.addSuperBonus(this);
		regionMap = game.getGameMap().getRegionMap();
	}

	@Override
	public String checkBonus(Player currentPlayer) {
		if(!writeSecondOutput) {
			return "You have encountred a Building Permit Bonus on Nobility Track\n Choose the Region where to pick a permission card: " + regionMap.toString();
		}
		else {
			return "Choose a permission card (press 1 or 2): " + permitDeck.toString();
		}
	}

	@Override
	public void acquireSuperBonus(List<String> input, Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().pickPermitCard(game, turnHandler, game.getGameMap().getRegion(input.get(REGION_NAME_POSITION)), Integer.parseInt(input.get(CHOSEN_TILE_POSITION)) - 1);
	}

}