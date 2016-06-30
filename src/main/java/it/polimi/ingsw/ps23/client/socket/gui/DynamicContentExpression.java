package it.polimi.ingsw.ps23.client.socket.gui;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;

public class DynamicContentExpression extends UIComponentsParser {

	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	private static final String FREE_COUNCILLORS_TAG_OPEN = "<free_councillors>";
	private static final String FREE_COUNCILLORS_TAG_CLOSE = "</free_councillors>";
	private static final String COUNCILS_TAG_OPEN = "<councils>";
	private static final String COUNCILS_TAG_CLOSE = "</councils>";
	private static final String BONUS_TILES_TAG_OPEN = "<bonus_tiles>";
	private static final String BONUS_TILES_TAG_CLOSE = "</bonus_tiles>";
	
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
	
	private CouncilsExpression getCouncilsExpression() {
		Expression councilsExpression = new TerminalExpression(COUNCILS_TAG_OPEN, COUNCILS_TAG_CLOSE);
		return new CouncilsExpression(councilsExpression);
	}
	
	private BonusTilesExpression getBonusTilesExpression() {
		Expression bonusTilesExpression = new TerminalExpression(BONUS_TILES_TAG_OPEN, BONUS_TILES_TAG_CLOSE);
		return new BonusTilesExpression(bonusTilesExpression);
	}
	
	@Override
	public void parse(String message) {
		if(expression.interpret(message)) {
			String noTagMessage = expression.selectBlock(message);
			KingPositionExpression isKingPosition = getKingPositionExpression();
			isKingPosition.parse(noTagMessage);
			FreeCouncillorsExpression isFreeCouncillors = getFreeCouncillorsExpression();
			isFreeCouncillors.parse(noTagMessage);
			CouncilsExpression areCouncils = getCouncilsExpression();
			areCouncils.parse(noTagMessage);
			BonusTilesExpression areBonusTiles = getBonusTilesExpression();
			areBonusTiles.parse(noTagMessage);
			swingUI.refreshDynamicContents(isKingPosition.getKingPosition(), isFreeCouncillors.getFreeCouncillors(), areCouncils.getCouncilsName(), areCouncils.getCouncilsColor(),
					areBonusTiles.getGroupsName(), areBonusTiles.getGroupsBonusName(), areBonusTiles.getGroupsBonusValue(), 
					areBonusTiles.getKingBonusName(), areBonusTiles.getKingBonusValue());
		}
	}

}
