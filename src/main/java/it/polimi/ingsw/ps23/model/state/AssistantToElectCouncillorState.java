package it.polimi.ingsw.ps23.model.state;


import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.AssistantToElectCouncillor;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public class AssistantToElectCouncillorState extends ActionState {

	private State electCouncillorState;
	
	public AssistantToElectCouncillorState(String name) {
		super(name);
		electCouncillorState = new ElectCouncillorState(name);
	}
	
	@Override
	public void changeState(Context context, Game game) {
		electCouncillorState.changeState(context, game);
		context.setState(this);
	}
	
	public String getFreeCouncillors() {
		return ((ElectCouncillorState) electCouncillorState).getFreeCouncillors();
	}

	public String getCouncilsMap() {
		return ((ElectCouncillorState) electCouncillorState).toString();
	}	

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
	public Action createAction(String chosenCouncillor, String chosenBalcony) {
		return new AssistantToElectCouncillor(chosenCouncillor, ((ElectCouncillorState) electCouncillorState).getCouncilMap(chosenBalcony));
	}

}
