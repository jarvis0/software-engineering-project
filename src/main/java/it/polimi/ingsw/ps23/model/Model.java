package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.commons.modelview.ModelObservable;
import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.model.market.Market;
import it.polimi.ingsw.ps23.model.market.MarketObject;
import it.polimi.ingsw.ps23.model.market.MarketTransation;
import it.polimi.ingsw.ps23.model.state.Context;
import it.polimi.ingsw.ps23.model.state.EndGameState;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;
import it.polimi.ingsw.ps23.model.state.State;
import it.polimi.ingsw.ps23.model.state.SuperBonusState;

public class Model extends ModelObservable {
	
	private int currentPlayerIndex;
	private Game game;
	private Context context;
	private TurnHandler turnHandler;
	
	public Game getGame() {
		return game;
	}
	
	private void newGame(List<String> playersName) {
		try {
			game = new Game(playersName);
		} catch (NoCapitalException e) {
			e.printStackTrace();
		}
		currentPlayerIndex++;
		changePlayer();
	}

	public void setUpModel(List<String> playersName) {
		setStartingPlayerIndex();
		newGame(playersName);
		setGameStatusState();
	}
	
	private Player setCurrentGamePlayer() {
		Player currentPlayer = game.getGamePlayersSet().getPlayer(currentPlayerIndex);
		currentPlayer.pickCard(game.getPoliticDeck(), 1);
		return currentPlayer;
	}
	
	public void setPlayerTurn() {
		if(!(turnHandler.isAvailableMainAction() || (turnHandler.isAvailableQuickAction() && !(context.getState() instanceof StartTurnState)))) {
			if(++currentPlayerIndex < game.getNumberOfPlayer()) {
				if(new EndGame().isGameEnded(game, turnHandler)) {
					setEndGameState();
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
	
	private void setStartTurnState() {
		context = new Context();
		StartTurnState startTurnState = new StartTurnState(turnHandler);		
		startTurnState.changeState(context, game);
		//clonare startTurnState
		wakeUp(startTurnState);
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
			if(turnHandler.startSuperTurnState()) {
				setSuperBonusState();
				return;
			}
		}
		setGameStatusState();
	}
	
	private void setSuperBonusState() {
		context = new Context();
		SuperBonusState superBonusState = new SuperBonusState(turnHandler); 
		superBonusState.changeState(context, game);
		wakeUp(superBonusState);
	}

	private void setGameStatusState() {
		context = new Context();
		GameStatusState gameStatusState = new GameStatusState();
		gameStatusState.changeState(context, game);
		wakeUp(gameStatusState);
	}
	
	private void changePlayer() {
		turnHandler = new TurnHandler();
		game.setCurrentPlayer(setCurrentGamePlayer());
	}
	
	public void doOfferMarket(MarketObject marketObject) {
		Market currentMarket = game.getMarket();
		currentMarket.addMarketObject(marketObject);
		if(currentMarket.sellObjects() == game.getNumberOfPlayer()) {
			launchBuyMarket();
		}
		else {
			launchOfferMarket();
		}
	}
	
	private void launchOfferMarket() {
		Player currentPlayer = game.getGamePlayersSet().getPlayer(++currentPlayerIndex);
		game.setCurrentPlayer(currentPlayer);
		MarketOfferPhaseState marketOfferPhaseState = new MarketOfferPhaseState();
		marketOfferPhaseState.changeState(context, game);
		wakeUp(marketOfferPhaseState);
	}
	
	public void doBuyMarket(MarketTransation marketTransation) {
		marketTransation.doTransation(game);
		if(game.getMarket().continueMarket()) {
			launchBuyMarket();
		}
		else {
			setStartingPlayerIndex();
			currentPlayerIndex++;
			changePlayer();
			setGameStatusState();
		}		
	}
	
	private void launchBuyMarket() {
		context = new Context();
		game.setCurrentPlayer(game.getMarket().selectPlayer());
		MarketBuyPhaseState marketBuyPhaseState = new MarketBuyPhaseState();
		marketBuyPhaseState.changeState(context, game);
		wakeUp(marketBuyPhaseState);
	}
	
	private void setStartingPlayerIndex() {
		currentPlayerIndex = -1;
	}
	
	public void updateNobliltyTrackPoints(int initialNobilityTrackPoints, int finalNobilityTrackPoints, Game game, TurnHandler turnHandler) {
		game.getNobilityTrack().walkOnNobilityTrack(initialNobilityTrackPoints, finalNobilityTrackPoints, game, turnHandler);
	}

	public void doSuperBonusesAcquisition(SuperBonusGiver superBonusGiver) {
		superBonusGiver.values(game, turnHandler);
		setGameStatusState();
	}
	
}