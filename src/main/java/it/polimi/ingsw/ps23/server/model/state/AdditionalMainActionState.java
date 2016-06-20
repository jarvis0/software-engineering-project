package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.AdditionalMainAction;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class AdditionalMainActionState extends ActionState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4576967266256686342L;

	AdditionalMainActionState(String name) {
		super(name);
	}

	public Action createAction() {
		return new AdditionalMainAction();
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
