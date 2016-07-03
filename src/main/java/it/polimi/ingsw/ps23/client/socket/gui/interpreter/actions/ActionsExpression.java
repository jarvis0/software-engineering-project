package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

public class ActionsExpression extends GUIParser {

	private static final String ACQUIRE_BUSINESS_PERMIT_TILE_TAG = "<AcquireBusinessPermitTile>";
	private static final String CHANGE_PERMIT_TILES = "<change_permit_tiles>";
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;

	private Expression expression;
	
	public ActionsExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}
	
	private AcquireBusinessPermitTileExpression getAcquireBusinessPermitTileExpression() {
		Expression acquireBusinessPermitTileExpression = new TerminalExpression(ACQUIRE_BUSINESS_PERMIT_TILE_TAG, "");
		return new AcquireBusinessPermitTileExpression(swingUI, guiView, acquireBusinessPermitTileExpression);
	}
	
	private ChangePermitTilesExpression getChangePermitTilesExpression() {
		Expression changePermitTilesExpression = new TerminalExpression(CHANGE_PERMIT_TILES, "");
		return new ChangePermitTilesExpression(swingUI, guiView, changePermitTilesExpression);
	}
	
	@Override
	public void parse(String message) {
		if(expression.interpret(message)) {
			AcquireBusinessPermitTileExpression isAcquireBusinessPermitTilesAction = getAcquireBusinessPermitTileExpression();
			isAcquireBusinessPermitTilesAction.parse(message);
			ChangePermitTilesExpression isChangePermitTilesAction = getChangePermitTilesExpression();
			isChangePermitTilesAction.parse(message);
		}
	}

}
