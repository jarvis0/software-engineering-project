package it.polimi.ingsw.ps23.client.socket;

import java.io.PrintStream;

import it.polimi.ingsw.ps23.client.socket.gui.DynamicContentExpression;
import it.polimi.ingsw.ps23.client.socket.gui.NoInputExpression;
import it.polimi.ingsw.ps23.client.socket.gui.SocketSwingUI;
import it.polimi.ingsw.ps23.client.socket.gui.StaticContentExpression;

public class RemoteGUIView extends RemoteView {

	private static final String STATIC_CONTENT_TAG_OPEN = "<static_content>";
	private static final String STATIC_CONTENT_TAG_CLOSE = "</static_content>";
	private static final String DYNAMIC_CONTENT_TAG_OPEN = "<dynamic_content>";
	private static final String DYNAMIC_CONTENT_TAG_CLOSE = "</dynamic_content>";
	
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

	private StaticContentExpression getStaticContentExpression() {
		Expression expression = new TerminalExpression(STATIC_CONTENT_TAG_OPEN, STATIC_CONTENT_TAG_CLOSE);
		return new StaticContentExpression(swingUI, expression);
	}

	private DynamicContentExpression getDynamicContentExpression() {
		Expression expression = new TerminalExpression(DYNAMIC_CONTENT_TAG_OPEN, DYNAMIC_CONTENT_TAG_CLOSE);
		return new DynamicContentExpression(swingUI, expression);
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
		DynamicContentExpression isDynamicContent = getDynamicContentExpression();
		String message;
		do {
			message = getClient().receive();//TODO ricevo info su player disconnessi
			isDynamicContent.parse(message);
		} while(!getConnectionTimedOut());
		getClient().closeConnection();
	}

}
