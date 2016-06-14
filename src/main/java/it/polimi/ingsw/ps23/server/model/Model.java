package it.polimi.ingsw.ps23.server.model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.modelview.ModelObservable;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.Market;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransation;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayerResumeHandler;
import it.polimi.ingsw.ps23.server.model.state.Context;
import it.polimi.ingsw.ps23.server.model.state.EndGameState;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.State;
import it.polimi.ingsw.ps23.server.model.state.StateCache;
import it.polimi.ingsw.ps23.server.model.state.SuperBonusState;

public class Model extends ModelObservable {
	
	private Game game;
	private Context context;
	private TurnHandler turnHandler;
	private int currentPlayerIndex;
	private PlayerResumeHandler playerResumeHandler;
	
	private Logger logger;
	
	public Model() {
		logger = Logger.getLogger(this.getClass().getName());
	}

	private void newGame(List<String> playersName) {
		try {
			game = new Game(playersName);
		} catch (NoCapitalException e) {
			logger.log(Level.SEVERE, "Cannot initializate a new game.", e);
		}
		currentPlayerIndex++;
		changePlayer();
	}

	public void setUpModel(List<String> playersName, PlayerResumeHandler playerResumeHandler) {
		setStartingPlayerIndex();
		this.playerResumeHandler = playerResumeHandler;
		newGame(playersName);
		StateCache.loadCache();
		setStartTurnState();
	}
	
	private Player setCurrentGamePlayer() {
		Player currentPlayer = game.getGamePlayersSet().getPlayer(currentPlayerIndex);
		currentPlayer.pickCard(game.getPoliticDeck(), 1);
		return currentPlayer;
	}
	
	private void setStartTurnState() {
		context = new Context();
		StartTurnState startTurnState = new StartTurnState(turnHandler);		
		startTurnState.changeState(context, game);
		wakeUp(startTurnState);
		playerResumeHandler.resume();
	}
	
	public void setPlayerTurn() {
		if(game.getCurrentPlayer().isOnline() && !(turnHandler.isAvailableMainAction() || (turnHandler.isAvailableQuickAction() && !(context.getState() instanceof StartTurnState)))) {
			if(++currentPlayerIndex < game.getNumberOfPlayer()) {
				if(new EndGame().isGameEnded(game, turnHandler)) {
					setEndGameState();
					return;
				}
				else {
					changePlayer();
				}
			}
			else {
				setUpMarket();
				return;
			}
		}
		setStartTurnState();
	}
	
	private void setEndGameState() {
		context = new Context();
		EndGameState endGameState = new EndGameState();	
		endGameState.changeState(context, game);
		wakeUp(endGameState);
	}

	private void setUpMarket() {
		setStartingPlayerIndex();
		game.createNewMarket();
		launchOfferMarket();
	}

	public void setActionState(State state) {
		context = new Context();
		state.changeState(context, game);
		wakeUp(state);
	}
	
	public void doAction(Action action) {
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

	private void changePlayer() {
		turnHandler = new TurnHandler();
		game.setCurrentPlayer(setCurrentGamePlayer());
	}
	
	private void chooseNextOfferMarketStep(Market currentMarket) {
		if(currentMarket.sellObjects() == game.getNumberOfPlayer()) {
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
		Player currentPlayer = game.getGamePlayersSet().getPlayer(++currentPlayerIndex);
		game.setCurrentPlayer(currentPlayer);
		MarketOfferPhaseState marketOfferPhaseState = new MarketOfferPhaseState();
		marketOfferPhaseState.changeState(context, game);
		wakeUp(marketOfferPhaseState);
		playerResumeHandler.resume();
	}
	
	private void chooseNextBuyMarketStep() {
		if(game.getMarket().continueMarket()) {
			launchBuyMarket();
		}
		else {
			setStartingPlayerIndex();
			currentPlayerIndex++;
			changePlayer();
			setStartTurnState();
		}
	}
	
	public void doBuyMarket(MarketTransation marketTransation) {
		marketTransation.doTransation(game);
		
	}
	
	private void launchBuyMarket() {
		context = new Context();
		game.setCurrentPlayer(game.getMarket().selectPlayer());
		MarketBuyPhaseState marketBuyPhaseState = new MarketBuyPhaseState();
		marketBuyPhaseState.changeState(context, game);
		wakeUp(marketBuyPhaseState);
		playerResumeHandler.resume();
	}
	
	private void setStartingPlayerIndex() {
		currentPlayerIndex = -1;
	}
	
	public void updateNobliltyTrackPoints(int initialNobilityTrackPoints, int finalNobilityTrackPoints, Game game, TurnHandler turnHandler) {
		game.getNobilityTrack().walkOnNobilityTrack(initialNobilityTrackPoints, finalNobilityTrackPoints, game, turnHandler);
	}

	public void doSuperBonusesAcquisition(SuperBonusGiver superBonusGiver) {
		superBonusGiver.values(game, turnHandler);
		setPlayerTurn();
	}

	public void setOfflinePlayer(String offlinePlayer) {
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
				//detach(view);
			}
		}
	}
	
}