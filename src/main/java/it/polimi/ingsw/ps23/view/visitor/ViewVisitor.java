package it.polimi.ingsw.ps23.view.visitor;

import java.io.PrintStream;
import java.util.Scanner;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;
import it.polimi.ingsw.ps23.model.visitor.ActionVisitor;

public class ViewVisitor implements ActionVisitor{

	private Scanner scanner;
	private PrintStream output;
	
	public ViewVisitor(Scanner scanner, PrintStream output) {
		this.scanner = scanner;
		this.output = output;
	}
	
	@Override
	public void visit(GameStatusState currentState) {
		output.println("Map: " + currentState.getGameMap().toString());
		//stampa altre robe
	}

	@Override
	public void visit(StartTurnState currentState) {
		Player player = currentState.getCurrentPlayer();
		output.println("Current player: " + player.showPublicStatus() + player.showSecretStatus());
	}

}
