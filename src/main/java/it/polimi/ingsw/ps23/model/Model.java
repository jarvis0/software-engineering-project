package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.commons.modelview.ModelObservable;
import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.market.Market;
import it.polimi.ingsw.ps23.model.market.MarketObject;
import it.polimi.ingsw.ps23.model.state.Context;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;
import it.polimi.ingsw.ps23.model.state.State;

public class Model extends ModelObservable implements Cloneable {
	
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
		changePlayer();
	}

	public void setUpModel(List<String> playersName) {
		currentPlayerIndex = 0;
		newGame(playersName);
		setGameStatusState();
	}
	
	private Player setCurrentPlayer() {
		Player currentPlayer = game.getGamePlayersSet().getPlayer(currentPlayerIndex);
		currentPlayer.pickCard(game.getPoliticDeck(), 1);
		if(currentPlayerIndex++ > game.getNumberOfPlayer()) {
			currentPlayerIndex = 0;
			//start market phase (creazione del current market nel game)-- c'è il problema che non deve essere pescata la carta e chenon deve sempre partire il market
		}
		return currentPlayer;
	}
	
	public void setPlayerTurn() {
		if(!(turnHandler.isAvailableMainAction() || (turnHandler.isAvailableQuickAction() && !(context.getState() instanceof StartTurnState)))) {
			changePlayer();
		}
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
		action.doAction(game, turnHandler);
		setGameStatusState();
	}
	
	private void setGameStatusState() {
		context = new Context();
		GameStatusState gameStatusState = new GameStatusState();
		gameStatusState.changeState(context, game);
		wakeUp(gameStatusState);
	}
	
	private void changePlayer() {
		turnHandler = new TurnHandler();
		game.setCurrentPlayer(setCurrentPlayer());
	}
	
	public void doMarket(MarketObject marketObject) {
		Market currentMarket = game.getMarket();
		currentMarket.addMarketObject(marketObject);
		if(currentMarket.sellObjects() == game.getNumberOfPlayer()) {
			// ritornare al gioco normale
		}
		else {
			Player currentPlayer = game.getGamePlayersSet().getPlayer(currentPlayerIndex++);
			game.setCurrentPlayer(currentPlayer);
			context = new Context();
			MarketOfferPhaseState marketFirstPhaseState = new MarketOfferPhaseState();
			marketFirstPhaseState.changeState(context, game);
		}
	}
}