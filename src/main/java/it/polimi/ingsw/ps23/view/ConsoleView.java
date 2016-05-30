package it.polimi.ingsw.ps23.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.ps23.model.Game;

public class ConsoleView extends View {
	
	private Scanner scanner;
	private PrintStream output;
	
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
		setState(playersName);
	}
	
	private void doMainAction() {
		int choice;
		output.println("Choose a Main action for your turn:\n1. Elect a Councillor\n ");
		choice = scanner.nextInt();	
	}

	@Override
	public void run() {
		setPlayersNumber();
		while(true) {
			doMainAction();
		}		
	}

	@Override
	public void update(Game game) {
		output.println(game.toString());
	}
		
}
