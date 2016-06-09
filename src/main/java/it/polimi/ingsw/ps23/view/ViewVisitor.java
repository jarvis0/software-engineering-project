package it.polimi.ingsw.ps23.view;

import it.polimi.ingsw.ps23.model.state.ChangePermitsTileState;
import it.polimi.ingsw.ps23.model.state.AcquireBusinessPermitTileState;
import it.polimi.ingsw.ps23.model.state.AdditionalMainActionState;
import it.polimi.ingsw.ps23.model.state.AssistantToElectCouncillorState;
import it.polimi.ingsw.ps23.model.state.BuildEmporiumKingState;
import it.polimi.ingsw.ps23.model.state.BuildEmporiumPermitTileState;
import it.polimi.ingsw.ps23.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;
import it.polimi.ingsw.ps23.model.state.SuperBonusState;

public interface ViewVisitor {
	
	public void visit(GameStatusState currentState);
	public void visit(StartTurnState currentState);
	public void visit(ElectCouncillorState currentState);
	public void visit(EngageAnAssistantState currentState);
	public void visit(ChangePermitsTileState currenState);
	public void visit(AcquireBusinessPermitTileState currentState);
	public void visit(AssistantToElectCouncillorState currentState);
	public void visit(AdditionalMainActionState currentState);
	public void visit(BuildEmporiumKingState currentState);
	public void visit(BuildEmporiumPermitTileState currentState);
	public void visit(MarketOfferPhaseState currentState);
	public void visit(MarketBuyPhaseState currentState);
	public void visit(SuperBonusState superBonusState);

}
