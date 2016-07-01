package it.polimi.ingsw.ps23.server.commons.viewcontroller;

import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransaction;
import it.polimi.ingsw.ps23.server.model.state.State;

/**
 * This interface is part of MVC - Observer/Observable pattern.
 * In particular, it defines the update methods that will be
 * called after a notify observers method in the relative 
 * observable class.
 * @author Mirco Manzoni
 *
 */
public interface ControllerObserver {

	/**
	 * Invokes the model change player turn method.
	 * This causes a search for the next player turn for the game.
	 * In this search is checked the end game,
	 * the market phase, the regular player change turn condition.
	 * If a player is set offline, he will skip his turn.
	 */
	public void update();
	
	/**
	 * Invokes the model set action state method if it is a valid state
	 * request. Then, it sets a new game state provided from the view and
	 * selected by the player.
	 * @param state - the future game state
	 */
	public void update(State state);
	
	/**
	 * Invokes the model do action method if it is a valid
	 * game action for the related player. If this control is positive
	 * the action can be performed and both player and game map
	 * will be updated. Otherwise there will be a model roll-back
	 * to reset all game resources before this method call.
	 * @param action - the game action to be performed.
	 */
	public void update(Action action);
	
	public void update(MarketObject marketObject);
	
	public void update(MarketTransaction marketTransaction);
	
	public void update(SuperBonusGiver superBonusGiver);
	
	public void update(Exception e);
	
}
