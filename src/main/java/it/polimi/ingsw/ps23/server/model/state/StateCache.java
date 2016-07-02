package it.polimi.ingsw.ps23.server.model.state;

import java.io.Serializable;
import java.util.HashMap;

import it.polimi.ingsw.ps23.server.model.state.ElectCouncillorState;
/**
 * Provides methods to generate a specific {@link ActionState} starting form a string.
 * @author Alessandro Erba & Mirco Manzoni
 *
 */
public class StateCache implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3414059855156529082L;
	private HashMap<String, ActionState> stateMap;
	private static final String ELECT_COUNCILLOR = "elect councillor";
	private static final String ACQUIRE_BUSINESS_PERMIT_TILE = "acquire business permit tile";
	private static final String ASSISTANT_TO_ELECT_COUNCILLOR = "assistant to elect councillor";
	private static final String ADDITIONAL_MAIN_ACTION = "additional main action";
	private static final String ENGAGE_ASSITANT= "engage assistant";
	private static final String CHANGE_PERMIT_TILE= "change permit tile";
	private static final String BUILD_EMPORIUM_KING= "build emporium king";
	private static final String BUILD_EMPORIUM_TILE = "build emporium permit tile";
	/**
	 * Initialize the object creating the cache of possible {@link ActionState}.
	 */
	public StateCache() {
		stateMap = new HashMap<>();
		loadCache();
	}

	public ActionState getAction(String actionStateName) {
		return (ActionState) stateMap.get(actionStateName).clone();
	}
	
	private void putAction(ActionState actionState) {
		stateMap.put(actionState.getName(), actionState);
	}

	private void loadCache() {
		
		ElectCouncillorState electCouncillorState = new ElectCouncillorState(ELECT_COUNCILLOR);
		putAction(electCouncillorState);
	
		EngageAnAssistantState engageAnAssistantState = new EngageAnAssistantState(ENGAGE_ASSITANT);
		putAction(engageAnAssistantState);
		
		ChangePermitsTileState changePermitsTileState = new ChangePermitsTileState(CHANGE_PERMIT_TILE);
		putAction(changePermitsTileState);
		
		BuildEmporiumKingState buildEmporiumKingState = new BuildEmporiumKingState(BUILD_EMPORIUM_KING);
		putAction(buildEmporiumKingState);
		
		BuildEmporiumPermitTileState buildEmporiumPermitTileState = new BuildEmporiumPermitTileState(BUILD_EMPORIUM_TILE);
		putAction(buildEmporiumPermitTileState);
	
		AcquireBusinessPermitTileState acquireBusinessPermitTileStatus = new AcquireBusinessPermitTileState(ACQUIRE_BUSINESS_PERMIT_TILE);
		putAction(acquireBusinessPermitTileStatus);
		
		AssistantToElectCouncillorState assistantToElectCouncillorState = new AssistantToElectCouncillorState(ASSISTANT_TO_ELECT_COUNCILLOR);
		putAction(assistantToElectCouncillorState);
		
		AdditionalMainActionState additionalMainActionState = new AdditionalMainActionState(ADDITIONAL_MAIN_ACTION);
		putAction(additionalMainActionState);
		
	}
}
