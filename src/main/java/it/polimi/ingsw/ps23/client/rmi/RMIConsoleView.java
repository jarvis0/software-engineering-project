package it.polimi.ingsw.ps23.client.rmi;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

class RMIConsoleView extends RMIView {

	private Scanner scanner;
	private PrintStream output;
	private String clientName;
	private State state;
	private boolean endGame;
	private Logger logger;
	
	RMIConsoleView(RMIClient client) {
		super(client);
		endGame = false;
		scanner = new Scanner(System.in);
		output = new PrintStream(System.out);
		//TODO client name
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	private synchronized void pause() {
		do {
			try {
				wait();
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, "Cannot put " + clientName + " on hold.", e);
				Thread.currentThread().interrupt();
			}
		} while (!(state instanceof StartTurnState || state instanceof MarketBuyPhaseState || state instanceof MarketOfferPhaseState));
	}

	@Override
	public void visit(StartTurnState currentState) {
		Player player = currentState.getCurrentPlayer();
		output.println(currentState.getStatus());
		if(player.getName().equals(clientName)) {
			output.println("Current player: " + player.toString() + " " + player.showSecretStatus() + "\n" + currentState.getAvaiableAction() + "\n\nChoose an action to perform? ");
			try {
				try {
					getControllerInterface().wakeUpServer(StateCache.getAction(scanner.nextLine().toLowerCase()));
				}
				catch(NullPointerException e) {
					logger.log(Level.SEVERE, "Cannot create the action.", e);
					getControllerInterface().wakeUpServer();
				}
			} catch(RemoteException e) {
				logger.log(Level.SEVERE, "Cannot reach remote server", e);
			}
		}
		else {
			output.println("It's player " + player.getName() + " turn.\n");
		}
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		output.println("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = scanner.nextLine().toLowerCase();
		output.println("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = scanner.nextLine().toLowerCase();
		try {
			getControllerInterface().wakeUpServer(currentState.createAction(chosenCouncillor, chosenBalcony));
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
		}		
	}
	
	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		List<String> removedCards = new ArrayList<>();
		output.println("Choose a council to satisfy: " + currentState.getCouncilsMap());
		String chosenCouncil = scanner.nextLine().toLowerCase();
		output.println("How many cards to you want to use (max " + currentState.getAvailablePoliticCardsNumber(chosenCouncil) + " )");
		int numberOfCards = Integer.parseInt(scanner.nextLine());
		boolean finished = false;
		for(int i = 0; i < numberOfCards && !finished; i++) {
			output.println("Choose a politic card you want to use from this list: " + currentState.getPoliticHandDeck());
			String chosenCard = scanner.nextLine().toLowerCase();
			removedCards.add(chosenCard);
		}
		output.println("Choose a permission card (press 1 or 2): " + currentState.getAvailablePermitTile(chosenCouncil));
		int chosenCard = Integer.parseInt(scanner.nextLine()) - 1;
		try {
			getControllerInterface().wakeUpServer(currentState.createAction(chosenCouncil, removedCards, chosenCard));
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
		}		
	}
	
	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		output.println("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = scanner.nextLine().toLowerCase();
		output.println("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = scanner.nextLine().toLowerCase();
		try {
			getControllerInterface().wakeUpServer(currentState.createAction(chosenCouncillor, chosenBalcony));
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
		}		
	}
	
	@Override
	public void visit(AdditionalMainActionState currentState) {
		try {
			getControllerInterface().wakeUpServer(currentState.createAction());
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
		}
		
	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		try {
			getControllerInterface().wakeUpServer(currentState.createAction());
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
		}		
	}

	@Override
	public void visit(ChangePermitsTileState currentState) {
		output.println("Choose a region:" + currentState.getPermitsMap());
		String chosenRegion = scanner.nextLine().toLowerCase();
		try {
			getControllerInterface().wakeUpServer(currentState.createAction(chosenRegion));
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
		}
		
	}	

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		List<String> removedCards = new ArrayList<>();
		output.println("Choose the number of cards you want for satisfy the King Council: "+ currentState.getAvailableCardsNumber());
		int numberOfCards = Integer.parseInt(scanner.nextLine());
		output.println("Player hand deck:" + currentState.getDeck());
		for (int i = 0; i < numberOfCards; i++) {
			output.println("Choose a politic card you want to use from this list: " + currentState.getAvailableCards());
			String chosenCard = scanner.nextLine().toLowerCase();
			removedCards.add(chosenCard);
		}
		output.println("please insert the route for the king.[king's initial position: " + currentState.getKingPosition()+"] insert the arrival city: ");
		String arrivalCity = scanner.nextLine().toUpperCase();
		try {
			getControllerInterface().wakeUpServer(currentState.createAction(removedCards, arrivalCity));
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
		}		
	}

	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		output.println("Choose the permit tile that you want to use for build an Emporium: (numerical input) " + currentState.getAvaibleCards());
		int chosenCard = Integer.parseInt(scanner.nextLine()) - 1;
		output.println("Choose the city where you what to build an emporium: " + currentState.getChosenCard(chosenCard));
		String chosenCity = scanner.nextLine().toUpperCase();
		try {
			getControllerInterface().wakeUpServer(currentState.createAction(chosenCity, chosenCard));
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
		}
		
	}

	@Override
	public void visit(MarketOfferPhaseState currentState) {
		List<String> chosenPoliticCards = new ArrayList<>();
		List<Integer> chosenPermissionCards = new ArrayList<>();
		String player = currentState.getPlayerName();
		output.println("It's " + player + " market phase turn.");
		if(player.equals(clientName)) {
			if(currentState.canSellPoliticCards()) {
				output.println("How many politic cards do you want to use? ");
				int numberOfCards = Integer.parseInt(scanner.nextLine());
				for(int i = 0; i < numberOfCards; i++) {
					output.println("Select a card from this list: " + currentState.getPoliticHandDeck());
					chosenPoliticCards.add(scanner.nextLine());
				}
			}
			if(currentState.canSellPermissionCards()) {
				output.println("How many permission cards do you want to use? (numerical input >0)");
				int numberOfCards = Integer.parseInt(scanner.nextLine());
				for(int i = 0; i < numberOfCards; i++) {
					output.println("Select a card from this list: " + currentState.getPermissionHandDeck());
					chosenPermissionCards.add(Integer.parseInt(scanner.nextLine()) - 1);
				}
			}
			int chosenAssistants = 0;
			if(currentState.canSellAssistants()) {
				output.println("Select the number of assistants " + currentState.getAssistants());
				chosenAssistants = Integer.parseInt(scanner.nextLine());
			}
			output.println("Choose the price for your offer: ");
			int cost = Integer.parseInt(scanner.nextLine());
			try {
				getControllerInterface().wakeUpServer(currentState.createMarketObject(chosenPoliticCards, chosenPermissionCards, chosenAssistants, cost));
			} catch (RemoteException e) {
				logger.log(Level.SEVERE, "Cannot reach remote server", e);
			}
		}
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {
		String player = currentState.getPlayerName();
		output.println("It's " + player + " market phase turn.");
		try {
			if(player.equals(clientName)) {
				if(currentState.canBuy()) {
					output.println("Avaible offers: " + currentState.getAvaiableOffers());
					getControllerInterface().wakeUpServer(currentState.createTransation(Integer.parseInt(scanner.nextLine())));
				}
				else {
					output.println("You can buy nothing.");
					getControllerInterface().wakeUpServer(currentState.createTransation());
				}
			}
		} catch(RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
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
					output.println(currentState.useBonus(currentBonus));
					chosenRegion = scanner.nextLine().toLowerCase();
					currentState.analyzeInput(chosenRegion, currentBonus);
				}
				output.println(currentState.useBonus(currentBonus));
				List<String> bonusesSelections = new ArrayList<>();
				if(selectedBonuses.containsKey(currentBonus)) { //TODO verificare modifiche
					bonusesSelections = selectedBonuses.get(currentBonus);
				}	
				if(currentState.isBuildingPemitTileBonus(currentBonus)) {
					bonusesSelections.add(chosenRegion);
					bonusesSelections.add(scanner.nextLine());
				}
				else {
					bonusesSelections.add(scanner.nextLine());
				}
				selectedBonuses.put(currentBonus, bonusesSelections);
			}	
		}
		try {
			getControllerInterface().wakeUpServer(currentState.createSuperBonusesGiver(selectedBonuses));
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Cannot reach remote server", e);
		}
	}

	@Override
	public void visit(EndGameState currentState) {
		output.println(currentState.getWinner());
		endGame = true;
	}
	
	@Override
	public synchronized void run() {
		do {
			pause();
			state.acceptView(this);
		} while(!endGame);
	}

	@Override
	public synchronized void update(State state) {
		this.state = state;
		notifyAll();
	}

}
