package it.polimi.ingsw.ps23.client.rmi;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.model.player.Player;
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
import it.polimi.ingsw.ps23.server.model.state.State;
import it.polimi.ingsw.ps23.server.model.state.SuperBonusState;

public class RMIGUIView extends RMIView {
	
	private PrintStream output;
	private String clientName;
	private State state;
	private boolean endGame;
	private boolean waiting;
	private GUIDisplayer guiDisplayer;

	RMIGUIView(RMIClient client) {
		super(client);
		guiDisplayer = new GUIDisplayer();
	}

	@Override
	public synchronized void run() {
		waiting = true;
		pause();
		waiting = false;
		do {
			state.acceptView(this);
		} while(!endGame);
	}

	@Override
	public void update(State state) {
		this.state = state;
		if(waitResumeCondition() && waiting) {
			resume();
			waiting = false;
		}
	}
	
	private synchronized void pause() {
		try {
			wait();
		} catch (InterruptedException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot put " + clientName + " on hold.", e);
			Thread.currentThread().interrupt();
		}
	}
	
	private synchronized void resume() {
		notifyAll();
	}

	private boolean waitResumeCondition() {
		return state instanceof StartTurnState || state instanceof MarketBuyPhaseState || state instanceof MarketOfferPhaseState;
	}

	@Override
	public void visit(StartTurnState currentState) {
		
		Player player = currentState.getCurrentPlayer();
		guiDisplayer.showMap(currentState.getGameMap(), currentState.getPlayerSet());	
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ChangePermitsTileState currenState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MarketOfferPhaseState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SuperBonusState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EndGameState currentState) {
		// TODO Auto-generated method stub
		
	}

}
