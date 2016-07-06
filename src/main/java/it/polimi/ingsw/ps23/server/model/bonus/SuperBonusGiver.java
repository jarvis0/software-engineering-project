package it.polimi.ingsw.ps23.server.model.bonus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
/**
 * Provide methods to give all the {@link SuperBonus} selected.
 * @author Alessandro Erba
 *
 */
public class SuperBonusGiver implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4395249203502864570L;
	private Map<Bonus, List<String>> selectedBonuses;
	/**
	 * Constructs the object starting from a map with {@link SuperBonus} and a list of input.
	 * @param selectedBonuses - map of selected bonus and the relative input
	 */
	public SuperBonusGiver(Map<Bonus, List<String>> selectedBonuses) {
		this.selectedBonuses = selectedBonuses;
	}
	/**
	 * Gives all {@link SuperBonus} in the map to the current {@link Player}.
	 * @param game - current game to add bonuses
	 * @param turnHandler - current turn handler to add bonuses
	 * @throws InvalidCardException if it's selected an invalid card
	 * @throws InvalidCityException if it's selected an invalid city
	 */
	public void giveBonus(Game game, TurnHandler turnHandler) throws InvalidCityException, InvalidCardException {
		for(Entry<Bonus, List<String>> entry : selectedBonuses.entrySet()) {
			Bonus bonus = entry.getKey();
			List<String> values = entry.getValue();
			if(bonus instanceof BuildingPermitBonus) {
				for (int i = 0 ; i < values.size(); i = i + 2) {
					List<String> inputs = new ArrayList<>();
					inputs.add(values.get(i));
					inputs.add(values.get(i + 1));
					((SuperBonus) bonus).acquireSuperBonus(inputs, game, turnHandler);
				}
			} 
			else {
				((SuperBonus) bonus).acquireSuperBonus(values, game, turnHandler);
			}			
		}
	}
	
}
