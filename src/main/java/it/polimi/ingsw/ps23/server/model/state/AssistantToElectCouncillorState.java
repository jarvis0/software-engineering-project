package it.polimi.ingsw.ps23.server.model.state;


import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.AssistantToElectCouncillor;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class AssistantToElectCouncillorState extends ActionState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3158661826594339779L;
	private State electCouncillorState;
	
	AssistantToElectCouncillorState(String name) {
		super(name);
		electCouncillorState = new ElectCouncillorState(name);
	}

	public String getFreeCouncillors() {
		return ((ElectCouncillorState) electCouncillorState).getFreeCouncillors();
	}

	public String getCouncilsMap() {
		return ((ElectCouncillorState) electCouncillorState).getCouncilsMap();
	}	

	public Action createAction(String chosenCouncillor, String chosenBalcony) {
		return new AssistantToElectCouncillor(chosenCouncillor, ((ElectCouncillorState) electCouncillorState).getCouncilMap(chosenBalcony));
	}
	
	@Override
	public void changeState(Context context, Game game) {
		electCouncillorState.changeState(context, game);
		context.setState(this);
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
}
