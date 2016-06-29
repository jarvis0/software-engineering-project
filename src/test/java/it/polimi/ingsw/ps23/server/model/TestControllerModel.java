package it.polimi.ingsw.ps23.server.model;

import static org.junit.Assert.*;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.modelview.ViewObserver;
import it.polimi.ingsw.ps23.server.commons.viewcontroller.ViewObservable;
import it.polimi.ingsw.ps23.server.controller.Controller;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrackStep;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.server.model.state.EndGameState;
import it.polimi.ingsw.ps23.server.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.State;

public class TestControllerModel extends ViewObservable implements ViewObserver {

	private Model model;
	private State state;
	
	@Test
	public void test() throws IOException {
		model = new Model();
		Controller controller = new Controller(model);
		model.attach(this);
		this.attach(controller);
		List<String> playerNames = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			playerNames.add(String.valueOf(i));
		}
		PlayersResumeHandler playersResumeHandler = new PlayersResumeHandler(new ArrayList<>());
		model.setUpModel(playerNames, playersResumeHandler);
		model.startGame();
		checkState();
	}
	
	private void checkState() throws IOException {
		assertTrue(state instanceof StartTurnState);
		wakeUp();
		assertTrue(state instanceof StartTurnState);
		assertTrue(((StartTurnState)state).getCurrentPlayer().getName().equals(String.valueOf(0)));
		State newState = ((StartTurnState)state).getStateCache().getAction("elect councillor");
		String chosenCouncillor = ((StartTurnState)state).getFreeCouncillors().getFreeCouncillors().get(0).toString();
		String chosenBalcony = ((StartTurnState)state).getGameMap().getGroupRegionalCity().get(0).getName();
		wakeUp(newState);
		assertTrue(state instanceof ElectCouncillorState);
		wakeUp(((ElectCouncillorState)state).createAction(chosenCouncillor, chosenBalcony));
		assertTrue(state instanceof StartTurnState);
		((StartTurnState)state).getCurrentPlayer().updateNobilityPoints(((StartTurnState)state).getNobilityTrack().getSteps().size());
		boolean found = false;
		for(NobilityTrackStep nobilityTrackStep : ((StartTurnState)state).getNobilityTrack().getSteps()) {
			for(Bonus bonus : nobilityTrackStep.getBonuses()) {
				if(bonus.getName().equals("recycleRewardToken") || bonus.getName().equals("recycleRewardToken") || bonus.getName().equals("recycleRewardToken")) {
					found = true;
				}
			}
		}
		if(found) {
			//TODOwakeUp();//superbonus giver + update con startturnstate
		}
		newState = ((StartTurnState)state).getStateCache().getAction("engage assistant");
		wakeUp(newState);
		assertTrue(state instanceof EngageAnAssistantState);
		wakeUp(((EngageAnAssistantState)state).createAction());
		assertTrue(state instanceof StartTurnState);
		assertTrue(((StartTurnState)state).getCurrentPlayer().getName().equals(String.valueOf(1)));
		model.setCurrentPlayerOffline();
		assertTrue(((StartTurnState)state).getCurrentPlayer().getName().equals(String.valueOf(2)));
		assertTrue(model.isOnline(String.valueOf(0)));
		assertFalse(model.isOnline(String.valueOf(1)));
		model.setCurrentPlayerOffline();
		model.setOnlinePlayer(String.valueOf(1));
		model.setCurrentPlayerOffline();
		assertTrue(state instanceof MarketOfferPhaseState);
		assertTrue(((MarketOfferPhaseState)state).getPlayerName().equals(String.valueOf(0)));
		assertTrue(model.isOnline(String.valueOf(1)));
		assertTrue(state instanceof MarketOfferPhaseState);
		assertTrue(((MarketOfferPhaseState)state).getPlayerName().equals(String.valueOf(0)));
		MarketObject marketObject = ((MarketOfferPhaseState)state).createMarketObject(new ArrayList<>(), new ArrayList<>(), 0, 1);
		wakeUp(marketObject);
		assertTrue(state instanceof MarketOfferPhaseState);
		assertTrue(((MarketOfferPhaseState)state).getPlayerName().equals(String.valueOf(1)));
		wakeUp(marketObject);
		assertTrue(state instanceof MarketBuyPhaseState);
		model.setCurrentPlayerOffline();
		wakeUp(((MarketBuyPhaseState)state).createTransation());
		assertTrue(state instanceof StartTurnState);
		model.rollBack(new Exception());
		assertTrue(state instanceof StartTurnState);
		model.setCurrentPlayerOffline();
		assertTrue(state instanceof EndGameState);
		wakeUp(new Exception());
		assertTrue(state instanceof StartTurnState);	
	}
	
	@Override
	public void update(State state) {
		this.state = state;
	}
	
}
