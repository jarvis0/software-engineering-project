package it.polimi.ingsw.ps23.server.model.map.board;

import java.io.Serializable;
import java.util.Deque;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.NullBonus;
/**
 * Provides methods to manage king reward tiles.
 * @author Alessandro Erba
 *
 */
public class KingRewardTilesSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8347713800996719320L;
	private Deque<Bonus> kingTiles;
	/**
	 * Constructs the king reward tiles set starting from a list of bonuses.
	 * @param kingTiles - list of bonuses
	 */
	public KingRewardTilesSet(Deque<Bonus> kingTiles) {
		this.kingTiles = kingTiles;
	}
	
	/**
	 * @return the current bonus tile. If it is a null bonus because of there are no further bonus tiles in
	 * this set, it returns a null bonus instance.
	 */
	public Bonus getCurrentTile() {
		if(!kingTiles.isEmpty()) {
			return kingTiles.getFirst();
		}
		return new NullBonus("nullBonus");
	}
	/**
	 * Removes the first {@link Bonus} from the reward tile set
	 * @return the removed bonus.
	 */
	public Bonus pop() {
		return kingTiles.pop();
	}
	
	/**
	 * @return true if there is no further bonus tile in this set.
	 */
	public boolean isEmpty() {
		return kingTiles.isEmpty();
	}
	
	@Override
	public String toString() {
		return kingTiles.toString();
	}
	
}
