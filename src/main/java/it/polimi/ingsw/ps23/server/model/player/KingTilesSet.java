package it.polimi.ingsw.ps23.server.model.player;

import java.io.Serializable;
import java.util.Deque;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;

public class KingTilesSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8347713800996719320L;
	private Deque<Bonus> kingTiles;
	
	public KingTilesSet(Deque<Bonus> kingTiles) {
		this.kingTiles = kingTiles;
	}
	
	public Bonus getCurrentTile() {
		return kingTiles.getFirst();
	}
	
	Bonus pop() {
		return kingTiles.pop();
	}
	
	boolean isEmpty() {
		return kingTiles.isEmpty();
	}
	
	@Override
	public String toString() {
		return kingTiles.toString();
	}
	
}
