package it.polimi.ingsw.ps23.server.model.map.board;

import java.io.Serializable;
import java.util.Deque;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
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
	
	public Bonus getCurrentTile() {
		return kingTiles.getFirst();
	}
	/**
	 * Removes the first {@link Bonus} from the reward tile set
	 * @return the removed bonus.
	 */
	public Bonus pop() {
		return kingTiles.pop();
	}
	
	public boolean isEmpty() {
		return kingTiles.isEmpty();
	}
	
	@Override
	public String toString() {
		return kingTiles.toString();
	}
	
}
