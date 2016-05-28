package it.polimi.ingsw.ps23.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import it.polimi.ingsw.ps23.controller.ElectCouncillor;

public class ConsoleView extends View {
	
	private Scanner scanner;
	private PrintStream output;
	
	public ConsoleView(InputStream inputStream, OutputStream output) {
		this.scanner = new Scanner(inputStream);
		this.output = new PrintStream(output);
	}	
	
	private void chooseAction() {
	
		int choice;
		
		System.out.println("Choose an action for your turn:\n1. Main Action\n2. Quick Action ");
		choice = scanner.nextInt();
		scanner.next();
		
		switch(choice) {
			case 1: 
				doMainAction();
				break;
			case 2:
				//doQuickAction();
				break;
			default:
				break;			
		}
	}
	
	private void doMainAction() {
		int choice;
		
		System.out.println("Choose a Main action for your turn:\n1. Elect a Councillor\n2.  ");
		choice = scanner.nextInt();
		notifyObservers(choice);		
	}
		
	private void setPlayersNumber() {
		/*output.println("Players number: ");
		int playersNumber = scanner.nextInt();
		scanner.nextLine();
		setChanged();
		try {
			notifyObservers(playersNumber);
		} catch (Exception e) {
			System.out.println("error");
		}
		List<String> playersName = new ArrayList<>();
		for(int i = 0; i < playersNumber; i++) {
			output.println("Name Player " + (i + 1) + ": ");
			playersName.add(scanner.nextLine());
		}*/
		List<String> playersID = new ArrayList<>();
		playersID.add("Mirco");
		playersID.add("ERBA");
		setChanged();
		notifyObservers(playersID);
	}
	
	@Override
	public void run() {
		setPlayersNumber();
		while(true){
				doMainAction();
		}		
	}
	@Override
	public void update(Observable o, ElectCouncillor currentAction) {
		
	}
	
		
}
