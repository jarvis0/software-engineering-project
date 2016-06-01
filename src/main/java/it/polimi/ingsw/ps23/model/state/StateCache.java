package it.polimi.ingsw.ps23.model.state;

import java.util.HashMap;

import it.polimi.ingsw.ps23.model.state.ElectCouncillorState;

public class StateCache {
	
	private static HashMap<String, ActionState> stateMap = new HashMap<>();
	private static final String ELECT_COUNCILLOR = "elect councillor";
	private static final String ACQUIRE_BUSINESS_PERMIT_TILE = "acquire business permit tile";
	private static final String ASSISTANT_TO_ELECT_COUNCILLOR = "assistant to elect councillor";
	private static final String ADDITIONAL_MAIN_ACTION = "additional main action";

	
	public static ActionState getAction(String actionStateName) {
		return (ActionState) stateMap.get(actionStateName).clone();
	}
	
	private static void putAction(ActionState actionState) {
		stateMap.put(actionState.getName(), actionState);
	}
	
	public static void loadCache() {
		ElectCouncillorState electCouncillorState = new ElectCouncillorState(ELECT_COUNCILLOR);
		putAction(electCouncillorState);
		
		AcquireBusinessPermitTileState acquireBusinessPermitTileStatus = new AcquireBusinessPermitTileState(ACQUIRE_BUSINESS_PERMIT_TILE);
		putAction(acquireBusinessPermitTileStatus);
		
		AssistantToElectCouncillorState assistantToElectCouncillorState = new AssistantToElectCouncillorState(ASSISTANT_TO_ELECT_COUNCILLOR);
		putAction(assistantToElectCouncillorState);
		
		AdditionalMainActionState additionalMainActionState = new AdditionalMainActionState(ADDITIONAL_MAIN_ACTION);
		putAction(additionalMainActionState);
	}
}
