package it.polimi.ingsw.ps23.model.state;

import java.util.HashMap;

import it.polimi.ingsw.ps23.model.state.ElectCouncillorState;

public class StateCache {
	
	private static HashMap<String, ActionState> stateMap = new HashMap<>();
	private static final String ELECT_COUNCILLOR = "elect councillor";
	private static final String ENGAGE_ASSITANT= "engage assistant";
	private static final String CHANGE_PERMIT_TILE= "change permit tile";
	
	public static ActionState getAction(String actionStateName) {
		return (ActionState) stateMap.get(actionStateName).clone();
	}
	
	private static void putAction(ActionState actionState) {
		stateMap.put(actionState.getName(), actionState);
	}
	
	public static void loadCache() {
		ElectCouncillorState electCouncillorState = new ElectCouncillorState(ELECT_COUNCILLOR);
		putAction(electCouncillorState);
		
		EngageAnAssistantState engageAnAssistantState = new EngageAnAssistantState(ENGAGE_ASSITANT);
		putAction(engageAnAssistantState);
		
		ChangePermitsTileState changePermitsTileState = new ChangePermitsTileState(CHANGE_PERMIT_TILE);
		putAction(changePermitsTileState);
		
	}
}
