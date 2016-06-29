package it.polimi.ingsw.ps23.server.view;

import it.polimi.ingsw.ps23.server.Connection;
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

public class SocketGUIView extends SocketView {

	public SocketGUIView(String clientName, Connection connection) {
		super(clientName, connection);
	}

	@Override
	public void visit(StartTurnState currentState) {
		getConnection().send(new SocketParameterCreator().createUIStatus(currentState));
		/*List<Region> regions = currentState.getGameMap().getGroupRegionalCity();
		String message = "<regions>" + currentState.getGameMap().getGroupRegionalCity().size() + ",";
		for()*/
		//Player player = currentState.getCurrentPlayer();
		/*if(player.getName().equals(getClientName())) {
			getConnection().sendYesInput("Current player: " + player.toString() + " " + player.showSecretStatus() + "\n" + currentState.getAvaiableAction() + "\n\nChoose an action to perform? ");
			try {
				wakeUp(currentState.getStateCache().getAction(receive().toLowerCase()));
			}
			catch(NullPointerException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot create the action.", e);
				wakeUp();
			}
		}
		else {
			getConnection().sendNoInput("It's player " + player.getName() + " turn.");
			pause();
		}*/
		pause();		
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
