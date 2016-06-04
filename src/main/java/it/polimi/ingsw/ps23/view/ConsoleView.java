package it.polimi.ingsw.ps23.view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.state.ChangePermitsTileState;
import it.polimi.ingsw.ps23.model.state.AcquireBusinessPermitTileState;
import it.polimi.ingsw.ps23.model.state.AdditionalMainActionState;
import it.polimi.ingsw.ps23.model.state.AssistantToElectCouncillorState;
import it.polimi.ingsw.ps23.model.state.BuildEmporiumKingState;
import it.polimi.ingsw.ps23.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;
import it.polimi.ingsw.ps23.model.state.State;
import it.polimi.ingsw.ps23.model.state.StateCache;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public abstract class ConsoleView extends View implements ViewVisitor {
	
	private Scanner scanner;
	private PrintStream output;
	
	public ConsoleView(Scanner scanner, PrintStream output) {
		this.scanner = scanner;
		this.output = output;
	}
	
	public abstract void update(State state);
	
	protected abstract void showMap(String msg);
	
	@Override
	public void visit(GameStatusState currentState) {
		showMap("Map: " + currentState.getGameMap().toString() + "\nPlayers: " + currentState.getGamePlayersSet().toString()+"\nKings's Council: " +currentState.getKingCouncil().toString());
		wakeUp();
	}

	@Override
	public void visit(StartTurnState currentState) {
		Player player = currentState.getCurrentPlayer();
		output.println("Current player: " + player.toString() + player.showSecretStatus());
		output.println("Choose an action to perform? \nMain Action:\n Elect Councillor \nQuick Action:\n Engage Assistant\n Change Permit Tile\n");
		wakeUp(StateCache.getAction(scanner.nextLine().toLowerCase()));
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		output.println("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = scanner.nextLine();
		output.println("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = scanner.nextLine();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));
		
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		output.println("Choose a council to satisfy: " + currentState.getCouncilsMap());
		String chosenCouncil = scanner.nextLine();
		output.println("How many cards to you want to use ( min 1 - max " + currentState.getAvailablePoliticCardsNumber(chosenCouncil) + " )");
		int numberOfCards = scanner.nextInt();
		boolean finished = false;
		for(int i = 0; i < numberOfCards && !finished; i++) {
			output.println("Choose a politic card you want to use from this list: " + currentState.getPoliticHandDeck());
			String chosenCard = scanner.nextLine();
			//aggiungere un metodo per rimuovere le carte già scelte
		}
		output.print("Choose a permission card: "+ currentState.getAvailablePermitTile(chosenCouncil));
		String chosenCard = scanner.nextLine();
		
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		output.println("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = scanner.nextLine();
		output.println("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = scanner.nextLine();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));		
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		// non so cosa dobbiamo scrivere
		wakeUp(currentState.createAction());
	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		wakeUp(currentState.createAction());
		
	}

	@Override
	public void visit(ChangePermitsTileState currentState) {
		output.println("Choose a region:" +currentState.getPermitsMap());
		String chosenRegion = scanner.nextLine();
		output.println("Choose the tile to remove: \n1 for the first tile \n2 for the second tile");
		int chosenTile = Integer.parseInt(scanner.nextLine()) - 1;
		wakeUp(currentState.createAction(chosenRegion, chosenTile));
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		List<String> removedCards = new ArrayList<>();
		output.println("chose the number of cards you want for satisfy the King Council: "+currentState.getAvailableCardsNumber());
		int numberOfCards = Integer.parseInt(scanner.nextLine());
		output.println("player hand deck:" +currentState.getDeck());
		for (int i=0; i<numberOfCards; i++) {
			output.println("Choose a politic card you want to use from this list: " +currentState.getAvailableCards());
			String chosenCard = scanner.nextLine();
			removedCards.add(chosenCard);
		}
		output.println("please insert the route for the king.[king's initial position: " +currentState.getKingPosition()+"]");
		output.println("insert the arrival city: ");
		String arrivalCity = scanner.nextLine().toUpperCase();
		wakeUp(currentState.createAction(removedCards, arrivalCity));
	}

}
