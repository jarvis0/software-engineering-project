package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;

public class DynamicContentsExpression extends GUIParser {

	private static final String KING_POSITION_TAG_OPEN = "<king_position>";
	private static final String KING_POSITION_TAG_CLOSE = "</king_position>";
	private static final String FREE_COUNCILLORS_TAG_OPEN = "<free_councillors>";
	private static final String FREE_COUNCILLORS_TAG_CLOSE = "</free_councillors>";
	private static final String COUNCILS_TAG_OPEN = "<councils>";
	private static final String COUNCILS_TAG_CLOSE = "</councils>";
	private static final String BONUS_TILES_TAG_OPEN = "<bonus_tiles>";
	private static final String BONUS_TILES_TAG_CLOSE = "</bonus_tiles>";
	private static final String PLAYERS_PARAMETERS_TAG_OPEN = "<players_parameters>";
	private static final String PLAYERS_PARAMETERS_TAG_CLOSE = "</players_parameters>";
	private static final String PERMIT_TILES_UP_TAG_OPEN = "<permit_tiles_up>";
	private static final String PERMIT_TILES_UP_TAG_CLOSE = "</permit_tiles_up>";
	private static final String TURN_PARAMETERS_TAG_OPEN = "<turn_parameters>";
	private static final String TURN_PARAMETERS_TAG_CLOSE = "</turn_parameters>";
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;
	
	private Expression expression;
	
	public DynamicContentsExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
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
	
	private PlayersParameterExpression getPlayersParameterExpression() {
		Expression politicCardsExpression = new TerminalExpression(PLAYERS_PARAMETERS_TAG_OPEN, PLAYERS_PARAMETERS_TAG_CLOSE);
		return new PlayersParameterExpression(politicCardsExpression);
	}
	
	private PermitTilesUpExpression getPermitTilesUpExpression() {
		Expression permitTilesUpExpression = new TerminalExpression(PERMIT_TILES_UP_TAG_OPEN, PERMIT_TILES_UP_TAG_CLOSE);
		return new PermitTilesUpExpression(permitTilesUpExpression);
	}
	
	private TurnParametersExpression getTurnParametersExpression() {
		Expression turnParametersExpression = new TerminalExpression(TURN_PARAMETERS_TAG_OPEN, TURN_PARAMETERS_TAG_CLOSE);
		return new TurnParametersExpression(turnParametersExpression);
	}
	
	@Override
	public void parse(String message) {
		if(expression.interpret(message)) {
			String noTagMessage = expression.selectBlock(message);
			KingPositionExpression isKingPosition = getKingPositionExpression();
			isKingPosition.parse(noTagMessage);
			FreeCouncillorsExpression areFreeCouncillors = getFreeCouncillorsExpression();
			areFreeCouncillors.parse(noTagMessage);
			CouncilsExpression areCouncils = getCouncilsExpression();
			areCouncils.parse(noTagMessage);
			BonusTilesExpression areBonusTiles = getBonusTilesExpression();
			areBonusTiles.parse(noTagMessage);
			PlayersParameterExpression arePlayersParameter = getPlayersParameterExpression();
			arePlayersParameter.parse(noTagMessage);
			PermitTilesUpExpression arePermitTilesUp = getPermitTilesUpExpression();
			arePermitTilesUp.parse(noTagMessage);
			swingUI.refreshDynamicContents(isKingPosition, areFreeCouncillors, areCouncils, arePermitTilesUp, areBonusTiles, arePlayersParameter);
			TurnParametersExpression areTurnParameters = getTurnParametersExpression();
			areTurnParameters.parse(noTagMessage);
			if(areTurnParameters.getCurrentPlayer().equals(guiView.getPlayerName())) {
				//guiView.resume();//TODO
				swingUI.showAvailableActions(areTurnParameters.isAvailableMainAction(), areTurnParameters.isAvailableQuickAction());
				guiView.pause();
				guiView.getClient().send(swingUI.getChosenAction());
			}
			else {
				swingUI.showAvailableActions(false, false);
				//guiView.pause();
			}
		}
	}

}
