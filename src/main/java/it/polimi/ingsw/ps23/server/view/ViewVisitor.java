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
/**
 * Provide methods to perform visit pattern
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public interface ViewVisitor {
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(StartTurnState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(ElectCouncillorState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(EngageAnAssistantState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(ChangePermitsTileState currenState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(AcquireBusinessPermitTileState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(AssistantToElectCouncillorState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(AdditionalMainActionState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(BuildEmporiumKingState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(BuildEmporiumPermitTileState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(MarketOfferPhaseState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(MarketBuyPhaseState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(SuperBonusState currentState);
	/**
	 * Start the visit of the selected state.
	 * @param currentState - the current state of the game
	 */
	public void visit(EndGameState currentState);

}
