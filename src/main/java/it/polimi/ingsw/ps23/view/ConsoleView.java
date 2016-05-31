package it.polimi.ingsw.ps23.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.ps23.model.state.Context;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class ConsoleView extends View {
	
	private Scanner scanner;
	private PrintStream output;
	private Context context;
	
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
	
	@Override
	public void run() {
		setPlayersNumber();
		while(true) {
			context.getState().acceptView(new ViewVisitor(scanner, output));
			setState();
			context.getState().acceptView(new ViewVisitor(scanner, output));
			break;
		}
	}

	@Override
	public void update(Context context) {
		this.context = context;
	}
		
}
