package it.polimi.ingsw.ps23.server.model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.commons.modelview.ModelObservable;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.Market;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransation;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.state.Context;
import it.polimi.ingsw.ps23.server.model.state.EndGameState;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.State;
import it.polimi.ingsw.ps23.server.model.state.SuperBonusState;

public class Model extends ModelObservable {

	private Game game;
	private Context context;
	private TurnHandler turnHandler;
	private int currentPlayerIndex;
	private PlayersResumeHandler playersResumeHandler;
	
	private void newGame(List<String> playersName) {
		game = new Game(playersName);
		currentPlayerIndex++;
		changePlayer();
	}

	public void setUpModel(List<String> playersName, PlayersResumeHandler playersResumeHandler) {
		setStartingPlayerIndex();
		this.playersResumeHandler = playersResumeHandler;
		newGame(playersName);
		setStartTurnState();
	}
	
	private Player setCurrentGamePlayer() {
		Player currentPlayer = game.getGamePlayersSet().getPlayer(currentPlayerIndex);
		currentPlayer.pickCard(game.getPoliticDeck(), 1);
		return currentPlayer;
	}

	private void changePlayer() {
		turnHandler = new TurnHandler();
		game.setCurrentPlayer(setCurrentGamePlayer());
	}
	
	private void setStartTurnState() {
		context = new Context();
		StartTurnState startTurnState = new StartTurnState(turnHandler);		
		startTurnState.changeState(context, game);
		wakeUp(startTurnState);
		playersResumeHandler.resume();
	}

	private boolean nextPlayerIndex() {
		boolean endLoop = false;
		do {
			if(currentPlayerIndex < game.getPlayersNumber() - 1) {
				if(game.getGamePlayersSet().getPlayer(++currentPlayerIndex).isOnline()) {
					endLoop = true;
				}
			}
			else {
				setStartingPlayerIndex();
				return false;
			}
		} while(!endLoop);
		return true;
	}

	private void selectNextGameState() {
		if(nextPlayerIndex()) {
			if(new EndGame().isGameEnded(game, turnHandler)) {
				setEndGameState();
			}
			else {
				changePlayer();
				setStartTurnState();
			}
		}
		else {
			setUpMarket();
		}
	}
	
	public void setPlayerTurn() {
		if(game.getCurrentPlayer().isOnline()) {
			if(!(turnHandler.isAvailableMainAction() || (turnHandler.isAvailableQuickAction() && !(context.getState() instanceof StartTurnState)))) {
				selectNextGameState();
			}
			else {
				setStartTurnState();
			}
		}
		else {
			selectNextGameState();
		}
	}
	
	private void setEndGameState() {
		context = new Context();
		EndGameState endGameState = new EndGameState();	
		endGameState.changeState(context, game);
		wakeUp(endGameState);
	}

	private void setUpMarket() {
		game.createNewMarket();
		launchOfferMarket();
	}

	public void setActionState(State state) {
		context = new Context();
		state.changeState(context, game);
		wakeUp(state);
	}
	
	public void doAction(Action action) throws InvalidCardException, InsufficientResourcesException, AlreadyBuiltHereException, InvalidCouncillorException, InvalidCouncilException, InvalidRegionException, InvalidCityException {
		int initialNobilityTrackPoints = game.getCurrentPlayer().getNobilityTrackPoints();
		action.doAction(game, turnHandler);
		int finalNobilityTrackPoints = game.getCurrentPlayer().getNobilityTrackPoints();
		if(initialNobilityTrackPoints != finalNobilityTrackPoints) {
			updateNobliltyTrackPoints(initialNobilityTrackPoints, finalNobilityTrackPoints, game, turnHandler);	
			if(turnHandler.isStartSuperTurnState()) {
				setSuperBonusState();
				return;
			}
		}
		setPlayerTurn();
	}
	
	private void setSuperBonusState() {
		context = new Context();
		SuperBonusState superBonusState = new SuperBonusState(turnHandler); 
		superBonusState.changeState(context, game);
		wakeUp(superBonusState);
	}

	private void chooseNextOfferMarketStep(Market currentMarket) {
		if(currentMarket.sellObjects() == game.getMarketPlayersNumber()) {
			launchBuyMarket();
		}
		else {
			launchOfferMarket();
		}
	}
	
	public void doOfferMarket(MarketObject marketObject) {
		Market currentMarket = game.getMarket();
		currentMarket.addMarketObject(marketObject);
		chooseNextOfferMarketStep(currentMarket);
	}
	
	private void launchOfferMarket() {
		nextPlayerIndex();
		Player currentPlayer = game.getGamePlayersSet().getPlayer(currentPlayerIndex);
		game.setCurrentPlayer(currentPlayer);
		MarketOfferPhaseState marketOfferPhaseState = new MarketOfferPhaseState();
		marketOfferPhaseState.changeState(context, game);
		wakeUp(marketOfferPhaseState);
		playersResumeHandler.resume();
	}
	
	private void chooseNextBuyMarketStep() {
		if(game.getMarket().continueMarket()) {
			launchBuyMarket();
		}
		else {
			setStartingPlayerIndex();
			nextPlayerIndex();
			changePlayer();
			setStartTurnState();
		}
	}
	
	public void doBuyMarket(MarketTransation marketTransation) {
		try {
			marketTransation.doTransation(game);
		} catch (InvalidCardException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot execute the transation", e);
		}
		chooseNextBuyMarketStep();
	}
	
	private void launchBuyMarket() {
		context = new Context();
		game.setCurrentPlayer(game.getMarket().selectPlayer());
		MarketBuyPhaseState marketBuyPhaseState = new MarketBuyPhaseState();
		marketBuyPhaseState.changeState(context, game);
		wakeUp(marketBuyPhaseState);
		playersResumeHandler.resume();
	}
	
	private void setStartingPlayerIndex() {
		currentPlayerIndex = -1;
	}
	
	public void updateNobliltyTrackPoints(int initialNobilityTrackPoints, int finalNobilityTrackPoints, Game game, TurnHandler turnHandler) {
		game.getNobilityTrack().walkOnNobilityTrack(initialNobilityTrackPoints, finalNobilityTrackPoints, game, turnHandler);
	}

	public void doSuperBonusesAcquisition(SuperBonusGiver superBonusGiver) throws InvalidCardException {
		superBonusGiver.giveBonus(game, turnHandler);
		setPlayerTurn();
	}

	public void setCurrentPlayerOffline() {
		//if(game.getGamePlayersSet().isAnyoneOnline()) {
			State currentState = context.getState();
			if(!(currentState instanceof MarketOfferPhaseState || currentState instanceof MarketBuyPhaseState)) {
				game.getCurrentPlayer().setOnline(false);
				setPlayerTurn();
			}
			else {
				if(currentState instanceof MarketOfferPhaseState) {
					chooseNextOfferMarketStep(game.getMarket());
				}
				else {
					chooseNextBuyMarketStep();
				}
			}
		//}
		//else {
			//TODO endgame?
		//}
	}
	
	public void setOnlinePlayer(String player) {
		for(Player gamePlayer : game.getGamePlayersSet().getPlayers()) {
			if(gamePlayer.getName().equals(player)) {
				gamePlayer.setOnline(true);
				return;
			}
		}
	}

	public boolean isOnline(String player) {
		for(Player gamePlayer : game.getGamePlayersSet().getPlayers()) {
			if(gamePlayer.getName().equals(player)) {
				return gamePlayer.isOnline();
			}
		}
		return false;
	}

	public void rollBack(Exception e) {
		context.addExceptionText(e);
		wakeUp(context.getState());		
	}

	public void restartTurn(Exception e) {
		context = new Context();
		StartTurnState startTurnState = new StartTurnState(turnHandler);
		startTurnState.changeState(context, game);
		context.addExceptionText(e);
		wakeUp(startTurnState);		
	}
	
}