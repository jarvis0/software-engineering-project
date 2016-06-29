package it.polimi.ingsw.ps23.client.socket;

import java.io.PrintStream;

import it.polimi.ingsw.ps23.client.socket.gui.NoInputExpression;
import it.polimi.ingsw.ps23.client.socket.gui.KingPositionExpression;

public class RemoteGUIView extends RemoteView {

	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	
	private SocketSwingUI swingUI;
	
	private String playerName;
	
	private boolean endCLIPrints;
	
	RemoteGUIView(SocketClient client, PrintStream output, String playerName) {
		super(client, output);
		endCLIPrints = false;
	}

	private NoInputExpression getNoInputExpression() {
		Expression expression = new TerminalExpression(getNoInputTagOpen(), getNoInputTagClose());
		return new NoInputExpression(this, getOutput(), expression);
	}
	
	private KingPositionExpression getKingPositionExpression() {
		Expression expression = new TerminalExpression(KING_POSITION_TAG_OPEN, KING_POSITION_TAG_CLOSE);
		return new KingPositionExpression(swingUI, expression);
	}

	public void setEndCLIPrints() {
		endCLIPrints = true;
	}

	private void CLIPrints() {
		String message;
		NoInputExpression isNoInput = getNoInputExpression();
		do {
			message = getClient().receive();
			message = isNoInput.parse(message);
		} while(!endCLIPrints);
		swingUI = new SocketSwingUI(message, playerName);
	}
	
	@Override
	protected void run() {
		CLIPrints();
		KingPositionExpression isKingPosition = getKingPositionExpression();
		String message;
		do {
			message = getClient().receive();//TODO ricevo info su player disconnessi
			message = isKingPosition.parse(message);
		} while(!getConnectionTimedOut());
		getClient().closeConnection();
	}

}
