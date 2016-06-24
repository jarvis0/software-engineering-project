package it.polimi.ingsw.ps23.server.model.state;


import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.AssistantToElectCouncillor;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class AssistantToElectCouncillorState extends ElectCouncillorActionState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3158661826594339779L;
	
	AssistantToElectCouncillorState(String name) {
		super(name);
	}

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
	
}
