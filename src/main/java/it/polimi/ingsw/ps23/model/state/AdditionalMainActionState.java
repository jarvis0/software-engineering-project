package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.actions.Action;
import it.polimi.ingsw.ps23.actions.AdditionalMainAction;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public class AdditionalMainActionState extends ActionState {

	public AdditionalMainActionState(String name) {
		super(name);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);		
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);		
	}
	
	public Action createAction() {
		return new AdditionalMainAction();
	}
}
