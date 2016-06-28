package it.polimi.ingsw.ps23.client.socket;

import java.io.PrintStream;
import java.util.Scanner;

import it.polimi.ingsw.ps23.client.socket.console.NoInputExpression;
import it.polimi.ingsw.ps23.client.socket.console.YesInputExpression;

class RemoteConsoleView extends RemoteView {

	private static final String NO_INPUT_TAG_OPEN = "<no_input>";
	private static final String NO_INPUT_TAG_CLOSE = "</no_input>";
	private static final String YES_INPUT_TAG_OPEN = "<yes_input>";
	private static final String YES_INPUT_TAG_CLOSE = "</yes_input>";
	
	private Scanner scanner;
	private PrintStream output;
	
	RemoteConsoleView(SocketClient client, Scanner scanner, PrintStream output) {
		super(client);
		this.scanner = scanner;
		this.output = output;
	}

	private NoInputExpression getNoInputExpression() {
		Expression expression = new TerminalExpression(NO_INPUT_TAG_OPEN, NO_INPUT_TAG_CLOSE);
		return new NoInputExpression(output, expression);
	}
	
	private YesInputExpression getYesInputExpression() {
		Expression expression = new TerminalExpression(YES_INPUT_TAG_OPEN, YES_INPUT_TAG_CLOSE);
		return new YesInputExpression(scanner, output, getClient(), expression);
	}

	@Override
	void run() {
		String message;
		YesInputExpression isYesInput = getYesInputExpression();
		NoInputExpression isNoInput = getNoInputExpression();
		String updatedMessage;
		do {
			message = getClient().receive();
			updatedMessage = isYesInput.parse(message);
			isNoInput.parse(updatedMessage);
		} while(!getConnectionTimedOut());
		getClient().closeConnection();
	}

}
