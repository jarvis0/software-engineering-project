package it.polimi.ingsw.ps23.client.socket.gui;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.SocketSwingUI;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;

public class DynamicContentExpression extends UIComponentsParser {

	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	private static final String FREE_COUNCILLORS_TAG_OPEN = "<free_councillors>";
	private static final String FREE_COUNCILLORS_TAG_CLOSE = "</free_councillors>";
	
	private SocketSwingUI swingUI;
	
	private Expression expression;
	
	public DynamicContentExpression(SocketSwingUI swingUI, Expression expression) {
		this.swingUI = swingUI;
		this.expression = expression;
	}
	
	private KingPositionExpression getKingPositionExpression() {
		Expression kingPositionExpression = new TerminalExpression(KING_POSITION_TAG_OPEN, KING_POSITION_TAG_CLOSE);
		return new KingPositionExpression(kingPositionExpression);
	}
	
	private FreeCouncillorsExpression getFreeCouncillorsExpression() {
		Expression freeCouncillorsExpression = new TerminalExpression(FREE_COUNCILLORS_TAG_OPEN, FREE_COUNCILLORS_TAG_CLOSE);
		return new FreeCouncillorsExpression(freeCouncillorsExpression);
	}
	
	@Override
	public void parse(String message) {
		if(expression.interpret(message)) {
			String noTagMessage = expression.selectBlock(message);
			KingPositionExpression isKingPosition = getKingPositionExpression();
			isKingPosition.parse(noTagMessage);
			FreeCouncillorsExpression isFreeCouncillors = getFreeCouncillorsExpression();
			isFreeCouncillors.parse(noTagMessage);
			swingUI.refreshDynamicContent(isKingPosition.getKingPosition(), isFreeCouncillors.getFreeCouncillors());
		}
	}

}
