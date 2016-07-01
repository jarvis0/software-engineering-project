package it.polimi.ingsw.ps23.server.model.map.board;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
/**
 * Provides methods to manage every steps of the nobility track
 * @author Alessandro Erba
 *
 */
public class NobilityTrack implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3661007432159236832L;
	private List<NobilityTrackStep> steps;
	/**
	 * Initializes the nobility track adding all the {@link NobilityTrackStep}.
	 * @param steps - list of steps of the nobility track
	 */
	public NobilityTrack(List<NobilityTrackStep> steps) {
		this.steps = steps;
	}
	/**
	 * Perform a walk on the {@link NobilityTrackStep} of the nobility track, checking the starting and the final nobility track points
	 * of a {@link Player}. If there are present some bonuses during this walk, it applies these.
	 * @param initialNobilityTrackPoints - starting position on the nobility track for the walk
	 * @param finalNobilityTrackPoints - final position on the nobility trakc for the walk
	 * @param game - current game to apply bonuses
	 * @param turnHandler - current turnHandler to apply bonuses
	 */
	public void walkOnNobilityTrack(int initialNobilityTrackPoints, int finalNobilityTrackPoints, Game game, TurnHandler turnHandler) {
		for(int position = initialNobilityTrackPoints + 1; position <= finalNobilityTrackPoints && position < steps.size(); position++) {
			steps.get(position).useBonus(game, turnHandler);
		}	
	}
	
	public List<NobilityTrackStep> getSteps() {
		return steps;		
	}
	
	@Override
	public String toString() {
		StringBuilder loopPrint = new StringBuilder();
		for(NobilityTrackStep step : steps) {
			loopPrint.append("\n\t- " + step.toString());
		}
		String print = new String();
		print += loopPrint;
		return print;
	}

}
