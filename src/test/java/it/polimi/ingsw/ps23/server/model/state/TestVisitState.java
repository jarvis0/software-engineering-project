package it.polimi.ingsw.ps23.server.model.state;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.AcquireBusinessPermitTile;
import it.polimi.ingsw.ps23.server.model.actions.AdditionalMainAction;
import it.polimi.ingsw.ps23.server.model.actions.AssistantToElectCouncillor;
import it.polimi.ingsw.ps23.server.model.actions.ChangePermitsTile;
import it.polimi.ingsw.ps23.server.model.actions.ElectCouncillor;
import it.polimi.ingsw.ps23.server.model.actions.EngageAnAssistant;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class TestVisitState implements ViewVisitor {

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
		assertTrue(game.getNobilityTrack().equals(currentState.getNobilityTrack()));
		assertTrue(game.getKingTilesSet().getCurrentTile().equals(currentState.getCurrentKingTile()));
		assertTrue(game.getFreeCouncillors().getFreeCouncillorsList().equals(currentState.getFreeCouncillors()));
		assertTrue(game.getStateCache().equals(currentState.getStateCache()));
		assertTrue(game.getGamePlayersSet().getPlayers().equals(currentState.getPlayersList()));
		assertTrue(game.getKing().getPosition().getName().equals(currentState.getKingPosition()));
		assertTrue(game.getGameMap().getGroupRegionalCity().equals(currentState.getGroupRegionalCity()));
		assertTrue(game.getKing().getCouncil().equals(currentState.getKingCouncil()));
		assertTrue(game.getGameMap().getGroupColoredCity().equals(currentState.getGroupColoredCity()));
		assertTrue(turnHandler.isAvailableMainAction() == currentState.isAvailableMainAction());
		assertTrue(turnHandler.isAvailableQuickAction() == currentState.isAvailableQuickAction());
		setContext(new ElectCouncillorState("elect councillor"));
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		assertTrue(game.getFreeCouncillors().toString().equals(currentState.getFreeCouncillors()));
		assertTrue(currentState.createAction("white", "king") instanceof ElectCouncillor);
		setContext(new EngageAnAssistantState("engage an assistant"));
	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		assertTrue(currentState.createAction() instanceof EngageAnAssistant);
		setContext(new ChangePermitsTileState("change permit tile"));
	}

	@Override
	public void visit(ChangePermitsTileState currentState) {
		assertTrue(currentState.createAction(game.getGameMap().getGroupColoredCity().get(0).getName()) instanceof ChangePermitsTile);
		setContext(new AcquireBusinessPermitTileState("acquire business permit tile"));
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		while(game.getCurrentPlayer().getNumberOfPoliticCards() != currentState.getPoliticHandSize()) {
			if(game.getCurrentPlayer().getNumberOfPoliticCards() >= 4) {
				assertTrue(currentState.getPoliticHandSize() == 4);
			}
			else {
				setContext(new AcquireBusinessPermitTileState("acquire business permit tile"));
			}
			game.getCurrentPlayer().getPoliticHandDeck().getCards().remove(game.getCurrentPlayer().getNumberOfPoliticCards() - 1);
		}
		List<String> removedPoliticCards = new ArrayList<>();
		for(Card card : game.getCurrentPlayer().getPoliticHandDeck().getCards()) {
			if(!((PoliticCard)card).getColor().equals("multi")) {
				((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getCouncil().getCouncillors().add(new Councillor((((PoliticCard)card).getColor())));
			}
			else {
				((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getCouncil().getCouncillors().add(game.getFreeCouncillors().getFreeCouncillorsList().get(0));
			}
			removedPoliticCards.add(((PoliticCard)card).getColor().toString());
		}
		removedPoliticCards.add("multi");
		try {
			assertTrue(currentState.getAvailablePoliticCardsNumber(((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getName()) == game.getCurrentPlayer().getNumberOfPoliticCards());
		} catch (InvalidCouncilException e) {}
		assertTrue(((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getPermitTilesUp().toString().equals(currentState.getAvailablePermitTile(((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getName())));
		try {
			assertTrue(currentState.createAction(((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getName(), removedPoliticCards, 0) instanceof AcquireBusinessPermitTile);
		} catch (InvalidCardException e) {}
		setContext(new AssistantToElectCouncillorState("assistant to elct councillor"));
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		try {
			currentState.canPerformThisAction(turnHandler);
		} catch (IllegalActionSelectedException e) {}
		assertTrue(currentState.createAction("white", "king")instanceof AssistantToElectCouncillor);
		setContext(new AdditionalMainActionState("additional main action"));
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		assertTrue(currentState.createAction() instanceof AdditionalMainAction);
		setContext(new BuildEmporiumKingState("building emporium king"));
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
				
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
