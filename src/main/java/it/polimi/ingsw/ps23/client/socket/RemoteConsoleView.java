package it.polimi.ingsw.ps23.client.socket;

import java.io.PrintStream;
import java.util.Scanner;

import it.polimi.ingsw.ps23.client.socket.console.NoInputExpression;
import it.polimi.ingsw.ps23.client.socket.console.YesInputExpression;

class RemoteConsoleView extends RemoteView {

	private static final String YES_INPUT_TAG_OPEN = "<yes_input>";
	private static final String YES_INPUT_TAG_CLOSE = "</yes_input>";
	
	private Scanner scanner;
	
	RemoteConsoleView(SocketClient client, Scanner scanner, PrintStream output) {
		super(client, output);
		this.scanner = scanner;
	}

	private NoInputExpression getNoInputExpression() {
		Expression expression = new TerminalExpression(getNoInputTagOpen(), getNoInputTagClose());
		return new NoInputExpression(getOutput(), expression);
	}
	
	private YesInputExpression getYesInputExpression() {
		Expression expression = new TerminalExpression(YES_INPUT_TAG_OPEN, YES_INPUT_TAG_CLOSE);
		return new YesInputExpression(scanner, getOutput(), getClient(), expression);
	}

	@Override
	protected void run() {
		NoInputExpression isNoInput = getNoInputExpression();
		YesInputExpression isYesInput = getYesInputExpression();
		String message;
		do {
			message = getClient().receive();
			message = isYesInput.parse(message);
			isNoInput.parse(message);
		} while(!getConnectionTimedOut());
		getClient().closeConnection();
	}

}
