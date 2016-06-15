package it.polimi.ingsw.ps23.server.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.Connection;
import it.polimi.ingsw.ps23.server.commons.modelview.ViewObserver;
import it.polimi.ingsw.ps23.server.commons.viewcontroller.ViewObservable;
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
import it.polimi.ingsw.ps23.server.model.state.StateCache;
import it.polimi.ingsw.ps23.server.model.state.SuperBonusState;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class View extends ViewObservable implements Runnable, ViewObserver, ViewVisitor {
	
	private static final String NO_INPUT = "NOINPUTNEEDED";
	
	private Connection connection;

	private String clientName;

	private State state;
	
	private Logger logger;

	private boolean endGame;
	
	public View(String clientName, Connection connection) {
		this.connection = connection;
		this.clientName = clientName;
		this.connection = connection;
		endGame = false;
		logger = Logger.getLogger(this.getClass().getName());
	}

	public Connection getConnection() {
		return connection;
	}

	public String getClientName() {
		return clientName;
	}

	public void sendNoInput(String message) {
		connection.send(NO_INPUT + message);
	}
	
	private void sendWithInput(String message) {
		connection.send(message);
	}
	
	private String receive() {
		return connection.receive();
	}
	
	private synchronized void pause() {
		boolean loop = true;
		while(loop) {
			try {
				wait();
				loop = false;
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, "Cannot put " + clientName + " on hold.", e);
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public synchronized void threadWakeUp() {
		notifyAll();
	}

	public void setPlayerOffline() {
		wakeUp(clientName);
	}

	@Override
	public void visit(StartTurnState currentState) {
		Player player = currentState.getCurrentPlayer();
		sendNoInput(currentState.getStatus());
		if(player.getName().equals(clientName)) {
			sendWithInput("Current player: " + player.toString() + " " + player.showSecretStatus() + "\n" + currentState.getAvaiableAction() + "\n\nChoose an action to perform? ");
			try {
				wakeUp(StateCache.getAction(receive().toLowerCase()));
			}
			catch(NullPointerException e) {
				logger.log(Level.SEVERE, "Cannot create the action.", e);
				wakeUp();
			}
		}
		else {
			sendNoInput("It's player " + player.getName() + " turn.\n");
			pause();
		}
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		sendWithInput("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = receive().toLowerCase();
		sendWithInput("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = receive().toLowerCase();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		List<String> removedCards = new ArrayList<>();
		sendWithInput("Choose a council to satisfy: " + currentState.getCouncilsMap());
		String chosenCouncil = receive().toLowerCase();
		sendWithInput("How many cards to you want to use (max " + currentState.getAvailablePoliticCardsNumber(chosenCouncil) + " )");
		int numberOfCards = Integer.parseInt(receive());
		boolean finished = false;
		for(int i = 0; i < numberOfCards && !finished; i++) {
			sendWithInput("Choose a politic card you want to use from this list: " + currentState.getPoliticHandDeck());
			String chosenCard = receive().toLowerCase();
			removedCards.add(chosenCard);
		}
		sendWithInput("Choose a permission card (press 1 or 2): " + currentState.getAvailablePermitTile(chosenCouncil));
		int chosenCard = Integer.parseInt(receive()) - 1;
		wakeUp(currentState.createAction(chosenCouncil, removedCards, chosenCard));
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		sendWithInput("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = receive().toLowerCase();
		sendWithInput("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
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
		sendWithInput("Choose a region:" + currentState.getPermitsMap());
		String chosenRegion = receive();
		wakeUp(currentState.createAction(chosenRegion));
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		List<String> removedCards = new ArrayList<>();
		sendWithInput("Choose the number of cards you want for satisfy the King Council: "+ currentState.getAvailableCardsNumber());
		int numberOfCards = Integer.parseInt(receive());
		sendNoInput("Player hand deck:" + currentState.getDeck());
		for (int i = 0; i < numberOfCards; i++) {
			sendWithInput("Choose a politic card you want to use from this list: " + currentState.getAvailableCards());
			String chosenCard = receive().toLowerCase();
			removedCards.add(chosenCard);
		}
		sendWithInput("please insert the route for the king.[king's initial position: " + currentState.getKingPosition()+"] insert the arrival city: ");
		String arrivalCity = receive().toUpperCase();
		wakeUp(currentState.createAction(removedCards, arrivalCity));
	}
	
	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		sendWithInput("Choose the permit tile that you want to use for build an Emporium: (numerical input) " + currentState.getAvaibleCards());
		int chosenCard = Integer.parseInt(receive()) - 1;
		sendWithInput("Choose the city where you what to build an emporium: " + currentState.getChosenCard(chosenCard));
		String chosenCity = receive().toUpperCase();
		wakeUp(currentState.createAction(chosenCity, chosenCard));
	}

	@Override
	public void visit(MarketOfferPhaseState currentState) {
		List<String> chosenPoliticCards = new ArrayList<>();
		List<Integer> chosenPermissionCards = new ArrayList<>();
		String player = currentState.getPlayerName();
		sendNoInput("It's " + player + " market phase turn.");
		if(player.equals(clientName)) {
			if(currentState.canSellPoliticCards()) {
				sendWithInput("How many politic cards do you want to use? ");
				int numberOfCards = Integer.parseInt(receive());
				for(int i = 0; i < numberOfCards; i++) {
					sendWithInput("Select a card from this list: " + currentState.getPoliticHandDeck());
					chosenPoliticCards.add(receive());
				}
			}
			if(currentState.canSellPermissionCards()) {
				sendWithInput("How many permission cards do you want to use? (numerical input >0)");
				int numberOfCards = Integer.parseInt(receive());
				for(int i = 0; i < numberOfCards; i++) {
					sendWithInput("Select a card from this list: " + currentState.getPermissionHandDeck());
					chosenPermissionCards.add(Integer.parseInt(receive()) - 1);
				}
			}
			int chosenAssistants = 0;
			if(currentState.canSellAssistants()) {
				sendWithInput("Select the number of assistants " + currentState.getAssistants());
				chosenAssistants = Integer.parseInt(receive());
			}
			sendWithInput("Choose the price for your offer: ");
			int cost = Integer.parseInt(receive());
			wakeUp(currentState.createMarketObject(chosenPoliticCards, chosenPermissionCards, chosenAssistants, cost));
		}
		else {
			pause();
		}
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {		
		String player = currentState.getPlayerName();
		sendNoInput("It's " + player + " market phase turn.");
		if(player.equals(clientName)) {
			if(currentState.canBuy()) {
				sendWithInput("Avaible offers: " + currentState.getAvaiableOffers());
				wakeUp(currentState.createTransation(Integer.parseInt(receive())));
			}
			else {
				sendNoInput("You can buy nothing.");
				wakeUp(currentState.createTransation());
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
			Bonus currentBonus = currentState.getCurrentBonus();
			String chosenRegion = new String();
			int numberOfCurrentBonus = currentBonus.getValue();
			for(int numberOfBonuses = 0; numberOfBonuses < numberOfCurrentBonus; numberOfBonuses++) {
				if(currentState.isBuildingPemitTileBonus(currentBonus)) {
					sendWithInput(currentState.useBonus(currentBonus));
					chosenRegion = receive().toLowerCase();
					currentState.analyzeInput(chosenRegion, currentBonus);
				}
				sendWithInput(currentState.useBonus(currentBonus));
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
		sendNoInput(currentState.getWinner());
		endGame = true;
		//TODO send a tutti i player di chi ha vinto e non solo al player corrente
	}

	@Override
	public void update(State state) {
		this.state = state;
	}
	
	@Override
	public synchronized void run() {
		do {
			state.acceptView(this);
		} while(!endGame);
	}
	
}
