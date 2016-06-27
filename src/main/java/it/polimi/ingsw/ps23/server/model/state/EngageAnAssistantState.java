package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.EngageAnAssistant;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class EngageAnAssistantState extends ActionState {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -93833003270086175L;

	EngageAnAssistantState(String name) {
		super(name);
	}

	public Action createAction() {
		return new EngageAnAssistant();
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
	}
	
}



