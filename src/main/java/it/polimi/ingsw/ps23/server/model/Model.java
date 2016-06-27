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

/**
 * This class is the game core class which contains all game data and
 * business logic for the application.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class Model extends ModelObservable {

	private Game game;
	private Context context;
	private TurnHandler turnHandler;
	private int currentPlayerIndex;
	private PlayersResumeHandler playersResumeHandler;
	
	private void newGame(List<String> playerNames) {
		game = new Game(playerNames);
		currentPlayerIndex++;
		changePlayer();
	}
	
	/**
	 * Sets up external from model parameters, such as: connected player names and
	 * a player resume handler useful to wake up socket connection threads.
	 * @param playerNames - connected clients who want to play a game
	 * @param playersResumeHandler - socket connection thread handler
	 */
	public void setUpModel(List<String> playerNames, PlayersResumeHandler playersResumeHandler) {
		setStartingPlayerIndex();
		this.playersResumeHandler = playersResumeHandler;
		newGame(playerNames);
	}
	
	/**
	 * Set the right start turn state.
	 * Called externally in order to make clients to receive the map type
	 * and then set up their view.
	 */
	public void startGame() {
		setStartTurnState();
	}
	
	public String getMapType() {
		return game.getMapType();
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
	
	/**
	 * Looks for the next player turn.
	 * It performs various checks in order to jump over players who are offline,
	 * start the market phase, start the last game round due to a player who has already built
	 * all his emporiums.
	 */
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

	/**
	 * Sets the current player offline due to his connection timeout and reset.
	 * A connection timeout occur when a remote client does not send any valid signal
	 * to the server within the default set timeout.
	 * <p>
	 * The specified player will jump the turns but his game resources remain
	 * effective until the end of the game.
	 * <p>
	 * A player can reconnect and resume his game status, but first he will wait his turn as
	 * set at the game startup.
	 */
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

	/**
	 * Checks whether the specified player name is online for the game.
	 * @param player - to be checked his online or offline status
	 * @return the specified player online or offline status (true or false)
	 * or false by default whether the specified player has not been found in the game
	 * which will be an inconsistent situation which will never occur.
	 */
	public boolean isOnline(String player) {
		for(Player gamePlayer : game.getGamePlayersSet().getPlayers()) {
			if(gamePlayer.getName().equals(player)) {
				return gamePlayer.isOnline();
			}
		}
		return false;
	}

	/**
	 * Adds the specified exception to the next game state
	 * in order to print it information to players and give the control to
	 * views.
	 * @param e - the exception information
	 */
	public void rollBack(Exception e) {
		context.addExceptionText(e);
		wakeUp(context.getState());		
	}

	/**
	 * Adds the specified exception to the next game state
	 * in order to print error information to players and give the control to
	 * views.
	 * <p>
	 * Unlike to {@link Model#rollBack(Exception)} this method handles
	 * a graver exception therefore it reinitializes the whole player turn.
	 * @param e - the exception information
	 */
	public void restartTurn(Exception e) {
		context = new Context();
		StartTurnState startTurnState = new StartTurnState(turnHandler);
		startTurnState.changeState(context, game);
		context.addExceptionText(e);
		wakeUp(startTurnState);		
	}
	
}