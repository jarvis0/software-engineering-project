package it.polimi.ingsw.ps23.server.commons.viewcontroller;

import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransation;
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
	
	public void wakeUp(MarketObject marketObject) {
		observer.update(marketObject);
	}
	
	public void wakeUp(MarketTransation marketTransation) {
		observer.update(marketTransation);
	}
	
	public void wakeUp(SuperBonusGiver superBonusGiver) {
		observer.update(superBonusGiver);
	}
	
	public void wakeUp(Exception e) {
		observer.update(e);
	}

}