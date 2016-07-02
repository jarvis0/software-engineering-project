package it.polimi.ingsw.ps23.server.model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
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
import it.polimi.ingsw.ps23.server.model.market.MarketTransaction;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.state.ActionState;
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
			if(new EndGame(game, turnHandler).isGameEnded()) {
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

	public void setActionState(State state) throws IllegalActionSelectedException {
		((ActionState)state).canPerformThisAction(turnHandler);
		context = new Context();
		state.changeState(context, game);
		wakeUp(state);
	}
	/** 
	 * Makes the selected action and check if the current {@link Player} takes nobility track 
	 * points during the performed action. If it is, the methods start walking on the {@link NobilityTrack}
	 * and if a {@link SuperBonus} was encountered, start the {@link StartSuperBonusState}.
	 * <p>
	 * If there is no variation on nobility track points or the walk didn't encounter some {@link SuperBonus}, 
	 * starts the {@link StartTrunState}
	 * @param action - the action to perform
	 * @throws InvalidCardException if an invalid card was selected
	 * @throws InsufficientResourcesException if the current player don't have enough resource to perform the selected action
	 * @throws AlreadyBuiltHereException if the current player have already built in the selected city
	 * @throws InvalidCouncillorException if an invalid councillor was selected
	 * @throws InvalidCouncilException if an invalid council was selected
	 * @throws InvalidRegionException if an invalid region was selected
	 * @throws InvalidCityException if an invalid city was selected
	 */
	public void doAction(Action action) throws InvalidCardException, InsufficientResourcesException, AlreadyBuiltHereException, InvalidCouncillorException, InvalidCouncilException, InvalidRegionException, InvalidCityException {
		int initialNobilityTrackPoints = game.getCurrentPlayer().getNobilityTrackPoints();
		action.doAction(game, turnHandler);
		game.setLastActionPerformed(action);
		int finalNobilityTrackPoints = game.getCurrentPlayer().getNobilityTrackPoints();
		if(initialNobilityTrackPoints != finalNobilityTrackPoints) {
			updateNobilityTrackPoints(initialNobilityTrackPoints, finalNobilityTrackPoints, game, turnHandler);	
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
		if(currentMarket.forSaleObjectsSize() == game.getMarketPlayersNumber()) {
			launchBuyMarket();
		}
		else {
			launchOfferMarket();
		}
	}
	/**
	 * Add the selected {@link MarketObject} into the {@link Market}. After this it sets the next market step.
	 * If there are other players that haven't already done their offer in market phase, it 
	 * sets {@link MarketOfferPhaseState} and the next current player.
	 * Otherwise it sets {@link MarketBuyPhaseState}, shuffling the player list and setting the current player.
	 * @param marketObject - the offered object of the current player
	 */
	public void doOfferMarket(MarketObject marketObject) {
		Market currentMarket = game.getMarket();
		currentMarket.addMarketObject(marketObject);
		chooseNextOfferMarketStep(currentMarket);
	}
	
	private void launchOfferMarket() {
		nextPlayerIndex();
		if(currentPlayerIndex < 0) {
			currentPlayerIndex++;
		}
		Player currentPlayer = game.getGamePlayersSet().getPlayer(currentPlayerIndex);
		game.setCurrentPlayer(currentPlayer);
		MarketOfferPhaseState marketOfferPhaseState = new MarketOfferPhaseState();
		marketOfferPhaseState.changeState(context, game);
		wakeUp(marketOfferPhaseState);
		playersResumeHandler.resume();
	}
	
	private void chooseNextBuyMarketStep() {
		if(game.getMarket().canContinueMarket()) {
			launchBuyMarket();
		}
		else {
			setStartingPlayerIndex();
			nextPlayerIndex();
			changePlayer();
			setStartTurnState();
		}
	}
	/**
	 * Make the selected transaction of the market phase. It will call the method to update all 
	 * the parameters present in {@link MarketObject} between the current player and the player set
	 * in the MarketObject. Catch an {@link InvalidCardException} if an error occurs during transaction.
	 * <p>
	 * After this, the next phase of market is selected: if there are other players that haven't already done
	 * their market phase, it sets another {@link MarketBuyPhaseState} with the next player. Otherwise it sets
	 * {@link StartTurnState} and sets the first player of list as currentPlayer.
	 * @param marketTransaction - the transation object
	 */
	public void doBuyMarket(MarketTransaction marketTransaction) {
		try {
			marketTransaction.doTransation(game);
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
	
	private void updateNobilityTrackPoints(int initialNobilityTrackPoints, int finalNobilityTrackPoints, Game game, TurnHandler turnHandler) {
		game.getNobilityTrack().walkOnNobilityTrack(initialNobilityTrackPoints, finalNobilityTrackPoints, game, turnHandler);
	}
	/**
	 * Permits to the current {@link Player} to take all the {@link SuperBonus} available.
	 * @param superBonusGiver - the container of superbonus
	 * @throws InvalidCardException if an invalid card was selected
	 * @throws InvalidCityException if an invalid city was selected
	 */
	public void doSuperBonusesAcquisition(SuperBonusGiver superBonusGiver) throws InvalidCardException, InvalidCityException {
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
		game.getCurrentPlayer().setOnline(false);
		if(game.getGamePlayersSet().canContinue()) {
			State currentState = context.getState();			
			if(!(currentState instanceof MarketOfferPhaseState || currentState instanceof MarketBuyPhaseState)) {
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
		}
		else {
			(new EndGame(game, turnHandler)).applyFinalBonus();
			setEndGameState();
			playersResumeHandler.resume();
		}
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
		game.refreshLastActionPerformed();
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
		game.refreshLastActionPerformed();
		context = new Context();
		StartTurnState startTurnState = new StartTurnState(turnHandler);
		startTurnState.changeState(context, game);
		context.addExceptionText(e);
		wakeUp(startTurnState);		
	}
	
}