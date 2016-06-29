package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.ElectCouncillor;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class ElectCouncillorState extends ElectCouncillorActionState {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4320899244481923736L;
	
	ElectCouncillorState(String name) {
		super(name);
	}

	@Override
	public Action createAction(String chosenCouncillor, String chosenBalcony) {
		return new ElectCouncillor(chosenCouncillor, chosenBalcony);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		setParameters(game);
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
	}

	@Override
	public void canPerformThisAction(TurnHandler turnHandler) throws IllegalActionSelectedException {
		if(!turnHandler.isAvailableMainAction()) {
			throw new IllegalActionSelectedException();
		}
	}

}
