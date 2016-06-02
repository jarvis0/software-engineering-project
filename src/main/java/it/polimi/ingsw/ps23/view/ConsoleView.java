package it.polimi.ingsw.ps23.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.state.ChangePermitsTileState;
import it.polimi.ingsw.ps23.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;
import it.polimi.ingsw.ps23.model.state.State;
import it.polimi.ingsw.ps23.model.state.StateCache;
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
		output.println("Map: " + currentState.getGameMap().toString() + "\nPlayers: " + currentState.getGamePlayersSet().toString());
		wakeUp();
		//stampa altre robe
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
	public void visit(EngageAnAssistantState currentState) {
		wakeUp(currentState.createAction());
		
	}

	@Override
	public void visit(ChangePermitsTileState currentState) {
		output.println("Choose a region:" +currentState.getPermitsMap());
		String chosenRegion = scanner.nextLine();
		output.println("Chose the tile to remove: \n1 for the first tile \n2 for the second tile");
		int chosenTile = scanner.nextInt()-1;
		wakeUp(currentState.createAction(chosenRegion, chosenTile));
	}
		
}
