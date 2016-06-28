package it.polimi.ingsw.ps23.server.view;

import it.polimi.ingsw.ps23.server.model.state.StartTurnState;

class SocketParameterCreator {

	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	
	private String addKingPosition(String kingPosition) {
		return KING_POSITION_TAG_OPEN + kingPosition + KING_POSITION_TAG_CLOSE;
	}
	
	String createUIStatus(StartTurnState currentState) {
		String message = addKingPosition(currentState.getKing().getPosition().getName());
		return message;
	}
	
}
