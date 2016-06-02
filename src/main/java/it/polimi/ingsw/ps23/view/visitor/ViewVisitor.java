package it.polimi.ingsw.ps23.view.visitor;

import it.polimi.ingsw.ps23.model.state.ChangePermitsTileState;
import it.polimi.ingsw.ps23.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;

public interface ViewVisitor {
	
	public void visit(GameStatusState currentState);
	public void visit(StartTurnState currentState);
	public void visit(ElectCouncillorState currentState);
	public void visit(EngageAnAssistantState currentState);
	public void visit(ChangePermitsTileState currenState);
	
}
