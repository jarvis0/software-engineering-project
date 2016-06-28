package it.polimi.ingsw.ps23.server.model.state;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class TestStartTurnState implements ViewVisitor {

	private Game game;
	private TurnHandler turnHandler;
	
	@Test
	public void test() {
		turnHandler = new TurnHandler();
		StartTurnState state = new StartTurnState(turnHandler);
		List<String> playerNames = new ArrayList<>();
		playerNames.add("a");
		playerNames.add("b");
		game = new Game(playerNames);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayer(0));
		setContext(state);
	}
	
	 private void setContext(State state) {
		Context context = new Context();
		state.changeState(context, game);
		context.getState().acceptView(this);
	}

	@Override
	public void visit(StartTurnState currentState) {
		assertTrue(game.getCurrentPlayer().equals(currentState.getCurrentPlayer()));
		assertTrue(game.getGameMap().equals(currentState.getGameMap()));
		assertTrue(game.getGamePlayersSet().equals(currentState.getPlayersSet()));
		assertTrue(game.getNobilityTrack().equals(currentState.getNobilityTrack()));
		assertTrue(game.getKing().equals(currentState.getKing()));
		assertTrue(game.getKingTilesSet().getCurrentTile().equals(currentState.getCurrentKingTile()));
		assertTrue(game.getFreeCouncillors().equals(currentState.getFreeCouncillors()));
		assertTrue(game.getStateCache().equals(currentState.getStateCache()));
		if(turnHandler.isAvailableMainAction()) {
			
		}
		
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ChangePermitsTileState currenState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MarketOfferPhaseState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SuperBonusState currentState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EndGameState currentState) {
		// TODO Auto-generated method stub
		
	}

}
