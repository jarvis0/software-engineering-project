package it.polimi.ingsw.ps23.server.model.bonus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class SuperBonusGiver implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4395249203502864570L;
	private Map<Bonus, List<String>> selectedBonuses;
	
	public SuperBonusGiver(Map<Bonus, List<String>> selectedBonuses) {
		this.selectedBonuses = selectedBonuses;
	}
	
	public void giveBonus(Game game, TurnHandler turnHandler) throws InvalidCardException {
		for(Entry<Bonus, List<String>> entry : selectedBonuses.entrySet()) {
			Bonus bonus = entry.getKey();
			List <String> values = entry.getValue();
			if(bonus instanceof BuildingPermitBonus) {
				for (int i = 0 ; i< values.size(); i = i + 2) {
					List<String> inputs = new ArrayList<>();
					inputs.add(values.get(i));
					inputs.add(values.get(i+1));
					game.getCurrentPlayer().updateSuperBonus(bonus, inputs, game, turnHandler);
				}
			} 
			else {
				game.getCurrentPlayer().updateSuperBonus(bonus, values, game, turnHandler);
			}			
		}
	}
	
}
