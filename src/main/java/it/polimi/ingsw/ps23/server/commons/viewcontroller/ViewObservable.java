package it.polimi.ingsw.ps23.server.commons.viewcontroller;

import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransaction;
import it.polimi.ingsw.ps23.server.model.state.State;

/**
 * Provides all needed to make both MVC and Observer/Observable
 * pattern work.
 * @author Alessandro Erba & Mirco Manzoni
 *
 */
public class ViewObservable {
	
	private ControllerObserver observer;
	
	/**
	 * Adds to classic MVC pattern observers, the specified view.	
	 * <p>
	 * Permits to notify the view with model updates.
	 * @param observer - the controller you want to notify when a
	 * view update occur
	 */
	public void attach(ControllerObserver observer) {
		this.observer = observer;
	}
	
	/**
	 * Invokes the update method on the observer. This particular wake up
	 * will notify the controller in order to search for the next player turn for the game.
	 * In this search is checked the end game,
	 * the market phase, the regular player change turn condition.
	 * If a player is set offline, he will skip his turn.
	 */
	public void wakeUp() {
		observer.update();
	}
	
	/**
	 * Invokes update method on the observer. This particular wake up
	 * will set the player action request to the model, but first checked by the controller.
	 * @param state - the player chosen future game state
	 */
	public void wakeUp(State state) {
		observer.update(state);
	}
	
	/**
	 * Invokes update method on the observer. This wake up will
	 * set the player chosen action parameters to the model, but first checked
	 * by the controller.
	 * @param action - game action parameters
	 */
	public void wakeUp(Action action) {
		observer.update(action);
	}
	/**
	 * Invokes update method on the observer. This wake up will
	 * set the player chosen market objects to the mode, but first checked
	 * by the controller.
	 * @param marketObject - container of all objects to sell
	 */
	public void wakeUp(MarketObject marketObject) {
		observer.update(marketObject);
	}
	/**
	 * Invokes update method on the observer. This wake up will
	 * set the player chosen transaction to the model, but first checked
	 * by the controller.
	 * @param marketTransaction - the transaction selected
	 */
	public void wakeUp(MarketTransaction marketTransaction) {
		observer.update(marketTransaction);
	}
	/**
	 * Invokes update method on the observer. This wake up will
	 * set all the player chosen superbonuses to the model, but first checked
	 * by the controller.
	 * @param superBonusGiver - container of all suberbonuses considered
	 */
	public void wakeUp(SuperBonusGiver superBonusGiver) {
		observer.update(superBonusGiver);
	}
	/**
	 * Invokes update method on the observer. This wake up will
	 * set exception occured in the view to the model, but first checked
	 * by the controller.
	 * @param e - exception occured in the view
	 */
	public void wakeUp(Exception e) {
		observer.update(e);
	}

}