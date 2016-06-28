package it.polimi.ingsw.ps23.client.socket;

import it.polimi.ingsw.ps23.client.socket.gui.KingPositionExpression;

class RemoteGUIView extends RemoteView {

	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	
	RemoteGUIView(SocketClient client) {
		super(client);
	}
	
	private KingPositionExpression getKingPositionExpression() {
		Expression expression = new TerminalExpression(KING_POSITION_TAG_OPEN, KING_POSITION_TAG_CLOSE);
		return new KingPositionExpression(, expression);
	}

	@Override
	void run() {
		
		KingPositionExpression isKingPosition = getKingPositionExpression();
		String message;
		do {
			message = getClient().receive();
			isKingPosition.parse(message);
		} while(!getConnectionTimedOut());
		getClient().closeConnection();
	}

}
