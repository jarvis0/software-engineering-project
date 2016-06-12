package it.polimi.ingsw.ps23.server.view;

import it.polimi.ingsw.ps23.server.model.state.AcquireBusinessPermitTileState;
import it.polimi.ingsw.ps23.server.model.state.AdditionalMainActionState;
import it.polimi.ingsw.ps23.server.model.state.AssistantToElectCouncillorState;
import it.polimi.ingsw.ps23.server.model.state.BuildEmporiumKingState;
import it.polimi.ingsw.ps23.server.model.state.BuildEmporiumPermitTileState;
import it.polimi.ingsw.ps23.server.model.state.ChangePermitsTileState;
import it.polimi.ingsw.ps23.server.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.server.model.state.EndGameState;
import it.polimi.ingsw.ps23.server.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.SuperBonusState;

public interface ViewVisitor {
	
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
	
	public void visit(SuperBonusState currentState);
	
	public void visit(EndGameState currentState);

}
