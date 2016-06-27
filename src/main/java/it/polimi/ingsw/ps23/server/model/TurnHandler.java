package it.polimi.ingsw.ps23.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;

/**
 * This classes provides methods for handling game turn system differentating
 * main, quick action and nobility track super bonus.
 * @author Alessandro Erba & Mirco Manzoni
 *
 */
public class TurnHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5789375179006568941L;
	private int mainActionsNumber;
	private boolean quickAction;
	private List<Bonus> superBonusSet;
	
	/**
	 * Initialize a new turn handler for the current player setting
	 * the initiali turn settings.
	 */
	public TurnHandler() {
		mainActionsNumber = 1;
		quickAction = true;
		resetSuperBonusSet();
	}
	
	public boolean isAvailableMainAction() {
		return mainActionsNumber > 0;
	}
	
	public boolean isAvailableQuickAction() {
		return quickAction;
	}
	
	/**
	 * Adds a main action due to additional main action bonus or quick action.
	 */
	public void addMainAction() {
		mainActionsNumber++;
	}
	
	/**
	 * Decrease the number of available main actions.
	 */
	public void useMainAction() {
		mainActionsNumber--;
	}
	
	/**
	 * Sets the current player turn quick action as already used.
	 */
	public void useQuickAction() {
		quickAction = false;
	}
	
	/**
	 * 
	 */
	public void resetSuperBonusSet() {
		superBonusSet = new ArrayList<>();
	}
	
	public void addSuperBonus(Bonus superBonus) {
		superBonusSet.add(superBonus);
	}
	
	public boolean isStartSuperTurnState() {
		return !superBonusSet.isEmpty();
		
	}
	
	public List<Bonus> getSuperBonuses() {
		return superBonusSet;
	}
}
