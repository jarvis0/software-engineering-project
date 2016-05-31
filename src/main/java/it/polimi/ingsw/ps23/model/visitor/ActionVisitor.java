package it.polimi.ingsw.ps23.model.visitor;

import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;

public interface ActionVisitor {
	
	public void visit(GameStatusState currentState);
	public void visit(StartTurnState currentState);
}
