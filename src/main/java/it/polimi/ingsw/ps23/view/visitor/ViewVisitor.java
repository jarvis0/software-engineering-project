package it.polimi.ingsw.ps23.view.visitor;

import it.polimi.ingsw.ps23.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;

public interface ViewVisitor {
	
	public void visit(GameStatusState currentState);
	public void visit(StartTurnState currentState);
	public void visit(ElectCouncillorState currentState);
}
