package it.polimi.ingsw.ps23.server.model.state;


import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.AssistantToElectCouncillor;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

@SuppressWarnings("serial")
public class AssistantToElectCouncillorState extends ElectCouncillorActionState {
	
	AssistantToElectCouncillorState(String name) {
		super(name);
	}

	@Override
	public Action createAction(String chosenCouncillor, String chosenBalcony) {
		return new AssistantToElectCouncillor(chosenCouncillor, chosenBalcony);
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
		if(!turnHandler.isAvailableQuickAction()) {
			throw new IllegalActionSelectedException();
		}
	}
	
}
