package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class TurnHandler {

	private int mainActionsNumber;
	private boolean quickAction;
	private List<Bonus> superBonusSet;
	
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
	
	public void addMainAction() {
		mainActionsNumber++;
	}
	
	public void useMainAction() {
		mainActionsNumber--;
	}
	
	public void useQuickAction() {
		quickAction = false;
	}
	
	public void resetSuperBonusSet() {
		superBonusSet = new ArrayList<>();
	}
	
	public void addSuperBonus(Bonus superBonus) {
		superBonusSet.add(superBonus);
	}
	
	public Bonus getSuperBonus(int index) {
		return superBonusSet.get(index);
	}
}
