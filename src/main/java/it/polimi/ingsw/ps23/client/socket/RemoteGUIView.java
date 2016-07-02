package it.polimi.ingsw.ps23.client.socket;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.client.GUIView;
import it.polimi.ingsw.ps23.client.socket.gui.AcquireBusinessPermitTileExpression;
import it.polimi.ingsw.ps23.client.socket.gui.DynamicContentsExpression;
import it.polimi.ingsw.ps23.client.socket.gui.NoInputExpression;
import it.polimi.ingsw.ps23.client.socket.gui.SocketSwingUI;
import it.polimi.ingsw.ps23.client.socket.gui.StaticContentExpression;

public class RemoteGUIView extends RemoteView implements GUIView {

	private static final String STATIC_CONTENT_TAG_OPEN = "<static_content>";
	private static final String STATIC_CONTENT_TAG_CLOSE = "</static_content>";
	private static final String DYNAMIC_CONTENT_TAG_OPEN = "<dynamic_content>";
	private static final String DYNAMIC_CONTENT_TAG_CLOSE = "</dynamic_content>";
	private static final String ACQUIRE_BUSINESS_PERMIT_TILE_TAG = "<AcquireBusinessPermitTile>";
	
	private SocketSwingUI swingUI;
	
	private String playerName;
	
	private boolean endCLIPrints;
	
	RemoteGUIView(SocketClient client, PrintStream output) {
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

	private DynamicContentsExpression getDynamicContentExpression() {
		Expression expression = new TerminalExpression(DYNAMIC_CONTENT_TAG_OPEN, DYNAMIC_CONTENT_TAG_CLOSE);
		return new DynamicContentsExpression(swingUI, this, expression);
	}
	
	private AcquireBusinessPermitTileExpression getAcquireBusinessPermitTile() {
		Expression expression = new TerminalExpression(ACQUIRE_BUSINESS_PERMIT_TILE_TAG, "");
		return new AcquireBusinessPermitTileExpression(swingUI, this, expression);
	}
	
	public void setEndCLIPrints() {
		endCLIPrints = true;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	private void cliPrints() {
		String message;
		NoInputExpression isNoInput = getNoInputExpression();
		do {
			message = getClient().receive();
			message = isNoInput.parse(message);
		} while(!endCLIPrints);
		swingUI = new SocketSwingUI(this, message, playerName);
	}
	
	public synchronized void pause() {
		try {
			wait();
		} catch (InterruptedException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot put " + playerName + " on hold.", e);
			Thread.currentThread().interrupt();
		}
	}
	
	@Override
	public synchronized void resume() {
		notifyAll();
	}

	@Override
	protected void run() {
		cliPrints();
		getStaticContentExpression().parse(getClient().receive());
		DynamicContentsExpression isDynamicContent = getDynamicContentExpression();
		AcquireBusinessPermitTileExpression isAction = getAcquireBusinessPermitTile();
		String message;
		do {
			message = getClient().receive();//TODO ricevo info su player disconnessi
			isDynamicContent.parse(message);
			isAction.parse(message);
		} while(!getConnectionTimedOut());
		getClient().closeConnection();
	}

}
