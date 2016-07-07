package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;
/**
 * Provides methods to show the state of the game to users and to create the actions selected.
 * @author Alessandro Erba, Giuseppe Mascellaro, Mirco Manzoni
 *
 */
public class StartTurnState extends MapUpdateState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6709781155533826821L;
	
	private TurnHandler turnHandler;	
	private StateCache stateCache;
	
	/**
	 * Constructs the objects setting the turn handler of the current game.
	 * @param turnHandler - the current turn handler
	 */
	public StartTurnState(TurnHandler turnHandler) {
		this.turnHandler = turnHandler;
	}
	
	/**
	 * Returns a CLI print to show the current player available actions.
	 * @return a print to be shown to the current player in order to understand which actions
	 * he can perform.
	 */
	public String getAvailableAction() {
		String avaiableAction = new String();
		if(turnHandler.isAvailableMainAction()) {
			avaiableAction += "\n--Main Action--\nElect Councillor\nAcquire Business Permit Tile\nBuild Emporium Permit Tile\nBuild Emporium King";
		}
		if(turnHandler.isAvailableQuickAction()) {
			avaiableAction += "\n--Quick Action--\nEngage Assistant\nChange Permit Tile\nAssistant To Elect Councillor\nAdditional Main Action";
		}
		return avaiableAction;
	}

	/**
	 * Returns the state cache in order to create the action taken by input.
	 * @return the state cache from state prototype pattern.
	 */
	public StateCache getStateCache() {
		return stateCache;
	}
	
	public boolean isAvailableMainAction() {
		return turnHandler.isAvailableMainAction();
	}
	
	public boolean isAvailableQuickAction() {
		return turnHandler.isAvailableQuickAction();
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		setParameters(game);
		stateCache = game.getStateCache();		
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
