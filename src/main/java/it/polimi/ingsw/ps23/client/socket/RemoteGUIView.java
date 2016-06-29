package it.polimi.ingsw.ps23.client.socket;

import java.io.PrintStream;

import it.polimi.ingsw.ps23.client.socket.gui.NoInputExpression;
import it.polimi.ingsw.ps23.client.socket.gui.StaticContentExpression;
import it.polimi.ingsw.ps23.client.socket.gui.KingPositionExpression;

public class RemoteGUIView extends RemoteView {

	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	private static final String STATIC_CONTENT_TAG_OPEN = "<static_content>";
	private static final String STATIC_CONTENT_TAG_CLOSE = "</static_content>";
	
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
	
	private StaticContentExpression getStaticContentExpression() {
		Expression expression = new TerminalExpression(STATIC_CONTENT_TAG_OPEN, STATIC_CONTENT_TAG_CLOSE);
		return new StaticContentExpression(swingUI, expression);
	}

	public void setEndCLIPrints() {
		endCLIPrints = true;
	}

	private void cliPrints() {
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
		cliPrints();
		getStaticContentExpression().parse(getClient().receive());
		KingPositionExpression isKingPosition = getKingPositionExpression();
		String message;
		do {
			message = getClient().receive();//TODO ricevo info su player disconnessi
			message = isKingPosition.parse(message);
		} while(!getConnectionTimedOut());
		getClient().closeConnection();
	}

}
