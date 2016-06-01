package it.polimi.ingsw.ps23.model;

public class TurnHandler {

	private int mainActionsNumber;
	private boolean quickAction;
	
	public TurnHandler() {
		mainActionsNumber = 1;
		quickAction = true;
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
}
