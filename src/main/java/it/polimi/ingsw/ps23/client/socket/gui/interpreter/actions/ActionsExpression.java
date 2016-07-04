package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

public class ActionsExpression extends GUIParser {

	private static final String ELECT_COUNCILLOR_TAG = "<elect_councillor>";
	private static final String ENGAGE_AN_ASSISTANT_TAG = "<engage_an_assistant>";
	private static final String ACQUIRE_BUSINESS_PERMIT_TILE_TAG = "<acquire_business_permit_tile>";
	private static final String CHANGE_PERMIT_TILES_TAG = "<change_permit_tiles>";
	private static final String ASSISTANT_TO_ELECT_COUNCILLOR_TAG = "<assistant_to_elect_councillor>";
	private static final String ADDITIONAL_MAIN_ACTION_TAG = "<additional_main_action>";
	private static final String BUILD_EMPORIUM_KING_TAG = "<build_emporium_king>";
	private static final String BUILD_EMPORIUM_PERMIT_TILE = "<build_emporium_permit_tile>";
	private static final String MARKET_OFFER_PHASE_TAG = "<market_offer_phase>";
	private static final String MARKET_BUY_PHASE_TAG = "<market_buy_phase>";
	private static final String SUPER_BONUS_TAG = "<super_bonus>";
	private static final String END_GAME_TAG = "<end_game>";
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;

	private Expression expression;
	
	public ActionsExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}
	
	private ElectCouncillorExpression getElectCouncillorExpression() {
		Expression electCouncillorExpression = new TerminalExpression(ELECT_COUNCILLOR_TAG, "");
		return new ElectCouncillorExpression(swingUI, guiView, electCouncillorExpression);
	}
	
	private EngageAnAssistantExpression getEngageAnAssistantExpression() {
		Expression engageAnAssistantExpression = new TerminalExpression(ENGAGE_AN_ASSISTANT_TAG, "");
		return new EngageAnAssistantExpression(swingUI, engageAnAssistantExpression);
	}
	
	private AcquireBusinessPermitTileExpression getAcquireBusinessPermitTileExpression() {
		Expression acquireBusinessPermitTileExpression = new TerminalExpression(ACQUIRE_BUSINESS_PERMIT_TILE_TAG, "");
		return new AcquireBusinessPermitTileExpression(swingUI, guiView, acquireBusinessPermitTileExpression);
	}
	
	private ChangePermitTilesExpression getChangePermitTilesExpression() {
		Expression changePermitTilesExpression = new TerminalExpression(CHANGE_PERMIT_TILES_TAG, "");
		return new ChangePermitTilesExpression(swingUI, guiView, changePermitTilesExpression);
	}
	
	private AssistantToElectCouncillorExpression getAssistantToElectCouncillorExpression() {
		Expression assistantToElectCouncillorExpression = new TerminalExpression(ASSISTANT_TO_ELECT_COUNCILLOR_TAG, "");
		return new AssistantToElectCouncillorExpression(swingUI, guiView, assistantToElectCouncillorExpression);
	}
	
	private AdditionalMainActionExpression getAdditionalMainActionExpression() {
		Expression additionalMainACtionExpression = new TerminalExpression(ADDITIONAL_MAIN_ACTION_TAG, "");
		return new AdditionalMainActionExpression(swingUI, additionalMainACtionExpression);
	}
	
	private BuildEmporiumKingExpression getBuildEmporiumKingExpression() {
		Expression buildEmporiumKingExpression = new TerminalExpression(BUILD_EMPORIUM_KING_TAG, "");
		return new BuildEmporiumKingExpression(swingUI, guiView, buildEmporiumKingExpression);
	}
	
	private BuildEmporiumPermitTileExpression getBuildEmporiumPermitTileExpression() {
		Expression buildEmporiumPermitTileExpression = new TerminalExpression(BUILD_EMPORIUM_PERMIT_TILE, "");
		return new BuildEmporiumPermitTileExpression(swingUI, guiView, buildEmporiumPermitTileExpression);
	}
	
	private MarketOfferPhaseExpression getMarketOfferPhaseExpression() {
		Expression marketOfferPhaseExpression = new TerminalExpression(MARKET_OFFER_PHASE_TAG, "");
		return new MarketOfferPhaseExpression(swingUI, guiView, marketOfferPhaseExpression);
	}
	
	private MarketBuyPhaseExpression getMarketBuyPhaseExpression() {
		Expression marketBuyPhaseExpression = new TerminalExpression(MARKET_BUY_PHASE_TAG, "");
		return new MarketBuyPhaseExpression(swingUI, guiView, marketBuyPhaseExpression);
	}
	
	private SuperBonusExpression getSuperBonusExpression() {
		Expression superBonusExpression = new TerminalExpression(SUPER_BONUS_TAG, "");
		return new SuperBonusExpression(swingUI, guiView, superBonusExpression);
	}

	private EndGameExpression getEndGameExpression() {
		Expression endGameExpression = new TerminalExpression(END_GAME_TAG, "");
		return new EndGameExpression(swingUI, guiView, endGameExpression);
	}

	@Override
	public void parse(String message) {
		if(expression.interpret(message)) {
			ElectCouncillorExpression isElectCouncillorAction = getElectCouncillorExpression();
			isElectCouncillorAction.parse(message);
			EngageAnAssistantExpression isEngageAnAssistant = getEngageAnAssistantExpression();
			isEngageAnAssistant.parse(message);
			AcquireBusinessPermitTileExpression isAcquireBusinessPermitTilesAction = getAcquireBusinessPermitTileExpression();
			isAcquireBusinessPermitTilesAction.parse(message);
			ChangePermitTilesExpression isChangePermitTilesAction = getChangePermitTilesExpression();
			isChangePermitTilesAction.parse(message);
			AssistantToElectCouncillorExpression isAssistantToElectCouncillorAction = getAssistantToElectCouncillorExpression();
			isAssistantToElectCouncillorAction.parse(message);
			AdditionalMainActionExpression isAdditionalMainActionAction = getAdditionalMainActionExpression();
			isAdditionalMainActionAction.parse(message);
			BuildEmporiumKingExpression isBuildEmporiumAction = getBuildEmporiumKingExpression();
			isBuildEmporiumAction.parse(message);
			BuildEmporiumPermitTileExpression isBuildEmporiumPermitTileAction = getBuildEmporiumPermitTileExpression();
			isBuildEmporiumPermitTileAction.parse(message);
			MarketOfferPhaseExpression isMarketOfferPhaseAction = getMarketOfferPhaseExpression();
			isMarketOfferPhaseAction.parse(message);
			MarketBuyPhaseExpression isMarketBuyPhaseAction = getMarketBuyPhaseExpression();
			isMarketBuyPhaseAction.parse(message);
			SuperBonusExpression isSuperBonusAction = getSuperBonusExpression();
			isSuperBonusAction.parse(message);
			EndGameExpression isEndGameAction = getEndGameExpression();
			isEndGameAction.parse(message);
		}
	}

}
