package it.polimi.ingsw.ps23.server.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.Connection;
import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCostException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidNumberOfAssistantException;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
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

public class SocketConsoleView extends SocketView {
	
	public SocketConsoleView(String clientName, Connection connection) {
		super(clientName, connection);
	}
	
	@Override
	public void visit(StartTurnState currentState) {
		Player player = currentState.getCurrentPlayer();
		getConnection().sendNoInput(currentState.getStatus());
		if(player.getName().equals(getClientName())) {
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
		}
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		getConnection().sendYesInput("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = receive().toLowerCase();
		getConnection().sendYesInput("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = receive().toLowerCase();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		try {
			List<String> removedCards = new ArrayList<>();
			getConnection().sendYesInput("Choose a council to satisfy: " + currentState.getCouncilsMap());
			String chosenCouncil = receive().toLowerCase();
			getConnection().sendYesInput("How many cards to you want to use (max " + currentState.getAvailablePoliticCardsNumber(chosenCouncil) + " )");
			int numberOfCards = Integer.parseInt(receive());
			for(int i = 0; i < numberOfCards && i < currentState.getPoliticHandSize() ; i++) {
				getConnection().sendYesInput("Choose a politic card you want to use from this list: " + currentState.getPoliticHandDeck());
				String chosenCard = receive().toLowerCase();
				removedCards.add(chosenCard);
			}
			getConnection().sendYesInput("Choose a permission card (press 1 or 2): " + currentState.getAvailablePermitTile(chosenCouncil));
			int chosenCard = Integer.parseInt(receive()) - 1;
			wakeUp(currentState.createAction(chosenCouncil, removedCards, chosenCard));
		} catch(InvalidCouncilException | InvalidCardException | NumberFormatException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			getState().setExceptionString(e.toString());
		}
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		getConnection().sendYesInput("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = receive().toLowerCase();
		getConnection().sendYesInput("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = receive().toLowerCase();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));		
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		wakeUp(currentState.createAction());
	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		wakeUp(currentState.createAction());
	}

	@Override
	public void visit(ChangePermitsTileState currentState) {
		getConnection().sendYesInput("Choose a region:" + currentState.printRegionalPermissionDecks());
		String chosenRegion = receive().toLowerCase();
		wakeUp(currentState.createAction(chosenRegion));
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		try {
			List<String> removedCards = new ArrayList<>();
			getConnection().sendYesInput("Choose the number of cards you want for satisfy the King Council: "+ currentState.getAvailableCardsNumber());
			int numberOfCards = Integer.parseInt(receive());
			getConnection().sendNoInput("Player hand deck:" + currentState.getDeck());
			for (int i = 0; i < numberOfCards && i < currentState.getPoliticHandSize(); i++) {
				getConnection().sendYesInput("Choose a politic card you want to use from this list: " + currentState.getAvailableCards());
				String chosenCard = receive().toLowerCase();
				removedCards.add(chosenCard);
			}
			getConnection().sendYesInput("please insert the route for the king.[king's initial position: " + currentState.getKingPosition()+"] insert the arrival city: ");
			String arrivalCity = receive();
			try {
				wakeUp(currentState.createAction(removedCards, arrivalCity));
			} catch (InvalidCardException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
				getState().setExceptionString(e.toString());
			}
		} catch(IllegalActionSelectedException | NumberFormatException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			wakeUp(e);
		}
	}
	
	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		try {
			getConnection().sendYesInput("Choose the permit tile that you want to use for build an Emporium: (numerical input) " + currentState.getAvaibleCards());
			int chosenCard = Integer.parseInt(receive()) - 1;
			getConnection().sendYesInput("Choose the city where you what to build an emporium: " + currentState.getChosenCard(chosenCard));
			String chosenCity = receive();
			wakeUp(currentState.createAction(chosenCity, chosenCard));
		} catch (IllegalActionSelectedException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			wakeUp(e);
		} catch (InvalidCardException | NumberFormatException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			getState().setExceptionString(e.toString());
		}
	}
	
	private List<String> sellPoliticCard(MarketOfferPhaseState currentState) throws NumberFormatException{
		List<String> chosenPoliticCards = new ArrayList<>();
		if(currentState.canSellPoliticCards()) {
			getConnection().sendYesInput("How many politic cards do you want to use? ");
			int numberOfCards = Integer.parseInt(receive());
			for(int i = 0; i < numberOfCards && i < currentState.getPoliticHandSize(); i++) {
				getConnection().sendYesInput("Select a card from this list: " + currentState.getPoliticHandDeck());
				chosenPoliticCards.add(receive());
			}
		}
		return chosenPoliticCards;
	}
	
	private List<Integer> sellPermitCards(MarketOfferPhaseState currentState) throws NumberFormatException {
		List<Integer> chosenPermissionCards = new ArrayList<>();
		if(currentState.canSellPermissionCards()) {
			getConnection().sendYesInput("How many permission cards do you want to use? (numerical input >0)");
			int numberOfCards = Integer.parseInt(receive());
			for(int i = 0; i < numberOfCards && i < currentState.getPoliticHandSize(); i++) {
				getConnection().sendYesInput("Select a card from this list: " + currentState.getPermissionHandDeck());
				chosenPermissionCards.add(Integer.parseInt(receive()) - 1);
			}
		}
		return chosenPermissionCards;
	}
	
	private int sellAssistant(MarketOfferPhaseState currentState) throws NumberFormatException {
		int chosenAssistants = 0;
		if(currentState.canSellAssistants()) {
			getConnection().sendYesInput("Select the number of assistants " + currentState.getAssistants());
			chosenAssistants = Integer.parseInt(receive());
		}
		return chosenAssistants;
	}

	@Override
	public void visit(MarketOfferPhaseState currentState) {
		String player = currentState.getPlayerName();
		getConnection().sendNoInput("It's " + player + " market phase turn.");
		if(player.equals(getClientName())) {
			List<String> chosenPoliticCards = sellPoliticCard(currentState);
			List<Integer> chosenPermissionCards = sellPermitCards(currentState);
			int chosenAssistants = sellAssistant(currentState);
			getConnection().sendYesInput("Choose the price for your offer: ");
			int cost = Integer.parseInt(receive());
			try {
				wakeUp(currentState.createMarketObject(chosenPoliticCards, chosenPermissionCards, chosenAssistants, cost));
			} catch (InvalidCardException | InvalidNumberOfAssistantException | InvalidCostException | NumberFormatException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
				getState().setExceptionString(e.toString());
			}
		}
		else {
			pause();
		}
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {		
		String player = currentState.getPlayerName();
		getConnection().sendNoInput("It's " + player + " market phase turn.");
		if(player.equals(getClientName())) {
			try {
				if(currentState.canBuy()) {
					getConnection().sendYesInput("Available offers: " + currentState.getAvaiableOffers());
					wakeUp(currentState.createTransation(Integer.parseInt(receive())));
				}
				else {
					getConnection().sendNoInput("You can buy nothing.");
					wakeUp(currentState.createTransation());
				}
			} catch(NumberFormatException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			}
		}
		else {
			pause();
		}
	}
	
	@Override
	public void visit(SuperBonusState currentState) {
		Map<Bonus, List<String>> selectedBonuses = new HashMap<>();
		while(currentState.hasNext()) {
			String chosenRegion = new String();
			Bonus currentBonus = currentState.getCurrentBonus();
			int numberOfCurrentBonus = currentBonus.getValue();
			for(int numberOfBonuses = 0; numberOfBonuses < numberOfCurrentBonus; numberOfBonuses++) {
				if(currentState.isBuildingPemitTileBonus(currentBonus)) {
					getConnection().sendYesInput(currentState.useBonus(currentBonus));
					chosenRegion = receive().toLowerCase();
					currentState.analyzeInput(chosenRegion, currentBonus);
				}
				getConnection().sendYesInput(currentState.useBonus(currentBonus));
				List<String> bonusesSelections = new ArrayList<>();
				if(selectedBonuses.containsKey(currentBonus)) { //TODO verificare modifiche
					bonusesSelections = selectedBonuses.get(currentBonus);
				}	
				if(currentState.isBuildingPemitTileBonus(currentBonus)) {
					bonusesSelections.add(chosenRegion);
					bonusesSelections.add(receive());
				}
				else {
					bonusesSelections.add(receive());
				}
				selectedBonuses.put(currentBonus, bonusesSelections);
			}	
		}
		wakeUp(currentState.createSuperBonusesGiver(selectedBonuses));
	}

	@Override
	public void visit(EndGameState currentState) {
		getConnection().sendNoInput(currentState.getWinner());
		setEndGame(true);
		//TODO send a tutti i player di chi ha vinto e non solo al player corrente
	}

	@Override
	public void update(State state) {
		setState(state);
	}
	
	@Override
	public synchronized void run() {
		if(isReconnected()) {
			pause();
		}
		do {
			getState().acceptView(this);
			if(getState().arePresentException()) {
				getConnection().sendNoInput(getState().getExceptionString());
			}
		} while(!isEndGame());
	}

}
