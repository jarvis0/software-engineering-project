package it.polimi.ingsw.ps23.server.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.Connection;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.state.AcquireBusinessPermitTileState;
import it.polimi.ingsw.ps23.server.model.state.AdditionalMainActionState;
import it.polimi.ingsw.ps23.server.model.state.AssistantToElectCouncillorState;
import it.polimi.ingsw.ps23.server.model.state.BuildEmporiumKingState;
import it.polimi.ingsw.ps23.server.model.state.BuildEmporiumPermitTileState;
import it.polimi.ingsw.ps23.server.model.state.ChangePermitTilesState;
import it.polimi.ingsw.ps23.server.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.server.model.state.EndGameState;
import it.polimi.ingsw.ps23.server.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.SuperBonusState;

public class SocketGUIView extends SocketView {

	private SocketParametersCreator gameParameters;
	private boolean firstUIrefresh;
	
	public SocketGUIView(String clientName, Connection connection) {
		super(clientName, connection);
		gameParameters = new SocketParametersCreator();
		firstUIrefresh = true;
	}

	@Override
	public void visit(StartTurnState currentState) {
		if(firstUIrefresh) {
			getConnection().send(gameParameters.createUIStaticContents(currentState.getGameMap().getCities(), currentState.getNobilityTrack()));
			firstUIrefresh = false;
		}
		getConnection().send(gameParameters.createUIDynamicContents(currentState));
		Player player = currentState.getCurrentPlayer();
		if(player.getName().equals(getClientName())) {
			//getConnection().sendYesInput("Current player: " + player.toString() + " " + player.showSecretStatus() + "\n" + currentState.getAvaiableAction() + "\n\nChoose an action to perform? ");
			try {
				wakeUp(currentState.getStateCache().getAction(receive()));
			}
			catch(NullPointerException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot create the action.", e);
				wakeUp();
			}
		}
		else {
			//getConnection().sendNoInput("It's player " + player.getName() + " turn.");
			pause();
		}
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		getConnection().send(gameParameters.createElectCouncillor());
		String chosenCouncillor = receive();
		String chosenBalcony = receive();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));
	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		getConnection().send(gameParameters.createEngageAnAssistant());
		wakeUp(currentState.createAction());
	}

	@Override
	public void visit(ChangePermitTilesState currentState) {
		getConnection().send(gameParameters.createChangePermitTiles());
		wakeUp(currentState.createAction(receive()));
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		getConnection().send(gameParameters.createAcquireBusinessPermitTile());
		getConnection().send(String.valueOf(currentState.getPoliticHandSize()));
		String chosenCouncil = receive();
		List<String> removedCards = new ArrayList<>();
		int removedCardsNumber = Integer.parseInt(receive());
		for(int i = 0; i < removedCardsNumber; i++) {
			removedCards.add(receive());
		}
		int chosenTile = Integer.parseInt(receive());
		try {
			wakeUp(currentState.createAction(chosenCouncil, removedCards, chosenTile));
		} catch (InvalidCardException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			getState().setExceptionString(e.toString());
		}
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		getConnection().send(gameParameters.createAssistantToElectCouncillor());
		String chosenCouncillor = receive();
		String chosenBalcony = receive();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		getConnection().send(gameParameters.createAdditionalMainAction());
		wakeUp(currentState.createAction());
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		getConnection().send(gameParameters.createBuildKingEmpoium());
		getConnection().send(String.valueOf(currentState.getPoliticHandSize()));
		int removedCardsNumber = Integer.parseInt(getConnection().receive());
		List<String> removedCards = new ArrayList<>();
		for(int i = 0; i < removedCardsNumber; i++) {
			removedCards.add(getConnection().receive());
		}
		String arrivalCity = getConnection().receive();
		try {
			wakeUp(currentState.createAction(removedCards, arrivalCity));
		} catch (InvalidCardException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			getState().setExceptionString(e.toString());
		}
	}

	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		getConnection().send(gameParameters.createBuildPermitTile());
		String chosenCity = receive();
		int chosenCard = Integer.parseInt(receive());
		wakeUp(currentState.createAction(chosenCity, chosenCard));
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
