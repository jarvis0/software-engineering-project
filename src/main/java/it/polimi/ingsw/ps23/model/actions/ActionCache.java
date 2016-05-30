package it.polimi.ingsw.ps23.model.actions;

import java.util.HashMap;

public class ActionCache {
	
	private static HashMap<String, Action> actionsMap = new HashMap<>();
	private static final String ELECT_COUNCILLOR = "elect councillor";
	
	public static Action getAction(String actionName) {
		Action cachedAction = actionsMap.get(actionName);
		Action action = (Action) cachedAction.clone();
		return action;
	}
	private static void putAction(Action action) {
		actionsMap.put(action.getName(), action);
	}
	
	public static void loadCache() {
		ElectCouncillor electCouncillor = new ElectCouncillor(ELECT_COUNCILLOR);
		putAction(electCouncillor);
	}
}
