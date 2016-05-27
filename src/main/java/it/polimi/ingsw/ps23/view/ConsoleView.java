package it.polimi.ingsw.ps23.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleView extends View {
	
	private Scanner scanner;
	private PrintStream output;
	
	public ConsoleView(InputStream inputStream, OutputStream output) {
		this.scanner = new Scanner(inputStream);
		this.output = new PrintStream(output);
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
		List<String> playersID = new ArrayList<>();
		for(int i = 0; i < playersNumber; i++) {
			output.println("Name Player " + (i + 1) + ": ");
			playersID.add(scanner.nextLine());
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
			//output.println("Make a choice: ");
			String text = scanner.next();
			try {				
				//Choice choice = Choice.parseInput(text);
				//processChoice(choice);			
			} catch(IllegalArgumentException e) {
				output.println("Input error!");
			}
		}		
	}
		
}
