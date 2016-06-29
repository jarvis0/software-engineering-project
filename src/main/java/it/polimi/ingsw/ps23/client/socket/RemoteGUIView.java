package it.polimi.ingsw.ps23.client.socket;

import java.io.PrintStream;

import it.polimi.ingsw.ps23.client.socket.gui.NoInputExpression;
import it.polimi.ingsw.ps23.client.socket.gui.RewardTokensExpression;
import it.polimi.ingsw.ps23.client.socket.gui.KingPositionExpression;

public class RemoteGUIView extends RemoteView {

	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	private static final String REWARD_TOKENS_TAG_OPEN = "<reward_tokens>";
	private static final String REWARD_TOKENS_TAG_CLOSE = "</reward_tokens>";
	
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

	private RewardTokensExpression getRewardTokensExpression() {
		Expression expression = new TerminalExpression(REWARD_TOKENS_TAG_OPEN, REWARD_TOKENS_TAG_CLOSE);
		return new RewardTokensExpression(swingUI, expression);
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
		RewardTokensExpression isRewardToken = getRewardTokensExpression();
		String message;
		do {
			message = getClient().receive();//TODO ricevo info su player disconnessi
			message = isKingPosition.parse(message);
			message = isRewardTokens.parse(message);
		} while(!getConnectionTimedOut());
		getClient().closeConnection();
	}

}
