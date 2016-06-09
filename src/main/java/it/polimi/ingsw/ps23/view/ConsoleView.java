package it.polimi.ingsw.ps23.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.bonus.Bonus;
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
import it.polimi.ingsw.ps23.model.state.State;
import it.polimi.ingsw.ps23.model.state.StateCache;
import it.polimi.ingsw.ps23.model.state.SuperBonusState;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class ConsoleView extends View implements ViewVisitor {
	
	private Scanner scanner;
	private PrintStream output;
	private State state;
	
	public ConsoleView(InputStream inputStream, OutputStream output) {
		this.scanner = new Scanner(inputStream);
		this.output = new PrintStream(output);
	}	

	private void setPlayersNumber() {
		output.println("Players number: ");
		int playersNumber = scanner.nextInt();
		scanner.nextLine();
		List<String> playersName = new ArrayList<>();
		for(int i = 0; i < playersNumber; i++) {
			output.println("Name Player " + (i + 1) + ": ");
			playersName.add(scanner.nextLine());
		}
		wakeUp(playersName);
	}
	
	@Override
	public void run() {
		setPlayersNumber();
		while(true) {
			state.acceptView(this);
		}
	}

	@Override
	public void update(State state) {
		this.state = state;
	}
	
	@Override
	public void visit(GameStatusState currentState) {
		output.println(currentState.getStatus());
		wakeUp();
	}

	@Override
	public void visit(StartTurnState currentState) {
		Player player = currentState.getCurrentPlayer();
		output.println("Current player: " + player.toString() + player.showSecretStatus());
		output.println("Choose an action to perform? " + currentState.getAvaiableAction());
		try {
			wakeUp(StateCache.getAction(scanner.nextLine().toLowerCase()));
		}
		catch(NullPointerException e) {
			wakeUp();
		}
	}
	
	@Override
	public void visit(ElectCouncillorState currentState) {
		output.println("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = scanner.nextLine().toLowerCase();
		output.println("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = scanner.nextLine().toLowerCase();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));
		
	}
	
	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		List<String> removedCards = new ArrayList<>();
		output.println("Choose a council to satisfy: " + currentState.getCouncilsMap());
		String chosenCouncil = scanner.nextLine().toLowerCase();
		output.println("How many cards to you want to use ( min 1 - max " + currentState.getAvailablePoliticCardsNumber(chosenCouncil) + " )");
		int numberOfCards = Integer.parseInt(scanner.nextLine());
		boolean finished = false;
		for(int i = 0; i < numberOfCards && !finished; i++) {
			output.println("Choose a politic card you want to use from this list: " + currentState.getPoliticHandDeck());
			String chosenCard = scanner.nextLine().toLowerCase();
			removedCards.add(chosenCard);
		}
		output.print("Choose a permission card (press 1 or 2): " + currentState.getAvailablePermitTile(chosenCouncil));
		int chosenCard = Integer.parseInt(scanner.nextLine()) - 1;
		wakeUp(currentState.createAction(chosenCouncil, removedCards, chosenCard));
		
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		output.println("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = scanner.nextLine().toLowerCase();
		output.println("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = scanner.nextLine().toLowerCase();
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
		output.println("Choose a region:" + currentState.getPermitsMap());
		String chosenRegion = scanner.nextLine();
		wakeUp(currentState.createAction(chosenRegion));

	}
	
	@Override
	public void visit(BuildEmporiumKingState currentState) {
		List<String> removedCards = new ArrayList<>();
		output.println("Choose the number of cards you want for satisfy the King Council: "+currentState.getAvailableCardsNumber());
		int numberOfCards = Integer.parseInt(scanner.nextLine());
		output.println("player hand deck:" + currentState.getDeck());
		for (int i=0; i<numberOfCards; i++) {
			output.println("Choose a politic card you want to use from this list: " + currentState.getAvailableCards());
			String chosenCard = scanner.nextLine().toLowerCase();
			removedCards.add(chosenCard);
		}
		output.println("please insert the route for the king.[king's initial position: " + currentState.getKingPosition()+"]");
		output.println("insert the arrival city: ");
		String arrivalCity = scanner.nextLine().toUpperCase();
		wakeUp(currentState.createAction(removedCards, arrivalCity));
	}
	
	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		output.println("Choose the permit tile that you want to use for build an Emporium: (numerical input) " + currentState.getAvaibleCards());
		int chosenCard = Integer.parseInt(scanner.nextLine()) - 1;
		output.println("Choose the city where you what to build an emporium: " + currentState.getChosenCard(chosenCard));
		String chosenCity = scanner.nextLine().toUpperCase();
		wakeUp(currentState.createAction(chosenCity, chosenCard));
	}

	@Override
	public void visit(MarketOfferPhaseState currentState) {
		List<String> chosenPoliticCards = new ArrayList<>();
		List<Integer> chosenPermissionCards = new ArrayList<>();
		output.println("It's " + currentState.getCurrentPlayer() + " market phase turn.");
		if(currentState.canSellPoliticCards()) {
			output.println("How many politic cards do you want to use? ");
			int numberOfCards = Integer.parseInt(scanner.nextLine());
			for(int i = 0; i < numberOfCards; i++) {
				output.println("Select a card from this list: " + currentState.getPoliticHandDeck());
				chosenPoliticCards.add(scanner.nextLine());
			}
		}
		if(currentState.canSellPoliticCards()) {
			output.println("How many permission cards do you want to use? ");
			int numberOfCards = Integer.parseInt(scanner.nextLine());
			for(int i = 0; i < numberOfCards; i++) {
				output.println("Select a card from this list: " + currentState.getPermissionHandDeck());
				chosenPermissionCards.add(Integer.parseInt(scanner.nextLine()));
			}
		}
		int chosenAssistants = 0;
		if(currentState.canSellAssistants()) {
			output.println("Select the number of assistants " + currentState.getAssistants());
			chosenAssistants = Integer.parseInt(scanner.nextLine());
		}
		output.println("Choose the price for your offer: ");
		int cost = Integer.parseInt(scanner.nextLine());
		wakeUp(currentState.createMarketObject(chosenPoliticCards, chosenPermissionCards, chosenAssistants, cost));
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {
		output.println("Market turn, current Player: " + currentState.getCurrentPlayer());
		if(currentState.canBuy()) {
			output.println("Avaible offers: " + currentState.getAvaiableOffers());
			wakeUp(currentState.createTransation(Integer.parseInt(scanner.nextLine())));
		}
		else {
			output.println("You can buy nothing");
			wakeUp(currentState.createTransation());
		}
	}

	@Override
	public void visit(SuperBonusState currentState) {
		Map<Bonus, List<String>> selectedBonuses = new HashMap<>();
		while(currentState.hasNext()) {
			Bonus currentBonus = currentState.getCurrentBonus();
			String chosenRegion = null;
			int numberOfCurrentBonus = currentBonus.getValue();
			for(int numberOfBonuses = 0; numberOfBonuses < numberOfCurrentBonus; numberOfBonuses++) {
				if(currentState.isBuildingPemitTileBonus(currentBonus)) {
					output.println(currentState.useBonus(currentBonus));
					chosenRegion = scanner.nextLine().toLowerCase();
					currentState.analyzeInput(chosenRegion, currentBonus);
				}
				output.println(currentState.useBonus(currentBonus));
				List<String> bonusesSelections = new ArrayList<>();
				if (selectedBonuses.get(currentBonus) != null) {			
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
		wakeUp(currentState.createSuperBonusesGiver(selectedBonuses));
		}
		
	}
}