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
	private List<Bonus> superBonusesSet;
	
	/**
	 * Initialize a new turn handler for the current player setting
	 * the initial turn settings.
	 */
	public TurnHandler() {
		mainActionsNumber = 1;
		quickAction = true;
		resetSuperBonusesSet();
	}
	
	/**
	 * @return true if the current player can perform a main action.
	 */
	public boolean isAvailableMainAction() {
		return mainActionsNumber > 0;
	}
	
	/**
	 * @return true if the current player can perform a quick action.
	 */
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
	
	private void resetSuperBonusesSet() {
		superBonusesSet = new ArrayList<>();
	}
	
	/**
	 * Adds a nobility track super bonus to super bonuses set.
	 * @param superBonus - the found nobility track super bonus during the nobility track walk
	 */
	public void addSuperBonus(Bonus superBonus) {
		superBonusesSet.add(superBonus);
	}
	
	/**
	 * @return true if the current player can perform a super bonus action based on the fact
	 * that he won a bonus on the nobility track that is a super bonus.
	 */
	public boolean isStartSuperTurnState() {
		return !superBonusesSet.isEmpty();
		
	}
	
	public List<Bonus> getSuperBonuses() {
		return superBonusesSet;
	}
	
}
