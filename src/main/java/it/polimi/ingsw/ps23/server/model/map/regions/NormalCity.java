package it.polimi.ingsw.ps23.server.model.map.regions;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
/**
 * Provides methods to manage a normal city and to use {@link Reward Token}.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class NormalCity extends City {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1228258945749359973L;
	private RewardToken rewardToken;
	/**
	 * Constructs a normal city with its name, color and {@link Reward Token}.
	 * @param name
	 * @param color
	 * @param rewardToken
	 */
	public NormalCity(String name, GameColor color, RewardToken rewardToken) { 
		super(name, color, false);
		this.rewardToken = rewardToken;
	}
	/**
	 * Gives to a {@link Player} bonuses.
	 * @param game - current game to update bonuses
	 * @param turnHandler - current turn handler to update bonuses
	 */
	public void useRewardToken(Game game, TurnHandler turnHandler) {
		rewardToken.useBonus(game, turnHandler);
	}
	
	public RewardToken getRewardToken() {
		return rewardToken;
	}
	/**
	 * Calculate if the {@link RewardToken} in this city has {@link NobilityTrackPoints}.
	 * @return true if there is, false otherwise.
	 */
	public boolean hasNobilityTrackBonus() {
		return rewardToken.hasNobilityTrackBonus();
	}
	
	@Override
	public String toString() {
		return getName() + " " + getColor() + " " + rewardToken + " " + getEmporiumsPlayersList();
	}
	
}
