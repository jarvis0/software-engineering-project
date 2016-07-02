package it.polimi.ingsw.ps23.server.model.bonus;

import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.BusinessPermitTile;
import it.polimi.ingsw.ps23.server.model.player.Player;
/**
 * Provides methods to take the specified bonus
 * @author Alessandro Erba
 *
 */
public class RecycleBuildingPermitBonus extends Bonus implements SuperBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 384058475289488324L;
	private static final int  VALUE_POSITION = 0;
	/**
	 * Construct the bonus to be cloned by {@link BonusCache}.
	 * @param name - the name of the bonus
	 */
	public RecycleBuildingPermitBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		turnHandler.addSuperBonus(this);		
	}

	@Override
	public String checkBonus(Player currentPlayer) {
		if(!currentPlayer.getAllPermitHandDeck().getCards().isEmpty()){
			return  "You have encountred a Recycle Building Permit Bonus on Nobility Track \nchoose the used permit tile for take bonuses: " +currentPlayer.getAllPermitHandDeck().toString();
		}
		return "Impossible using Recycle Building Permit Bonus because your Permission Hand Deck is empty (0 to skip)";
	}	

	@Override
	public void acquireSuperBonus(List<String> input, Game game, TurnHandler turnHandler) throws InvalidCardException {
		if(Integer.parseInt(input.get(VALUE_POSITION)) != 0) {
			((BusinessPermitTile) game.getCurrentPlayer().getAllPermitHandDeck().getCardInPosition(Integer.parseInt(input.get(VALUE_POSITION)) - 1)).useBonus(game, turnHandler);
		}
	}

}
