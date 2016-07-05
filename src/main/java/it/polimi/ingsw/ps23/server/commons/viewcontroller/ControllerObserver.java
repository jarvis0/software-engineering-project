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
	/**
	 * Invokes the model do offer market method. The market object 
	 * is saved and a new player is called to perform his action in market phase. 
	 * @param marketObject - the market object to be saved
	 */
	public void update(MarketObject marketObject);
	/**
	 * Invokes the model do buy market method. The market transaction
	 * is executed, updating the players involved. A new player is called
	 * to perform his action in market phase or the initial state is set
	 * if all players in game have already execute their actions.
	 * @param marketTransaction - the market transaction to be executed
	 */
	public void update(MarketTransaction marketTransaction);
	/**
	 * Invokes the model to do super bonus acquisition if it a valid
	 * super bonus. The model execute all the bonus giving advantage
	 * to the current player. After this a new state is called.
	 * Otherwise there will be a model roll-back to reset all game
	 * resources before this method call.
	 * @param superBonusGiver - the container of super bonus to be given
	 */
	public void update(SuperBonusGiver superBonusGiver);
	/**
	 * Invokes the model notifying it with an exception occurred in the
	 * view. The model will save this exception in the same state to notify
	 * the user.
	 * @param e - the exception occurred 
	 */
	public void update(Exception e);
	
}
