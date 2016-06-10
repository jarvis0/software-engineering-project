package it.polimi.ingsw.ps23.model.map;

import java.util.Deque;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class KingTileSet {
	
	private Deque<Bonus> kingTiles;
	
	public KingTileSet(Deque<Bonus> tilesStack) {
		this.kingTiles = tilesStack;
	}
	
	public void pop() {
		kingTiles.pop();
	}
	
	@Override
	public String toString() {
		return kingTiles.toString();
	}
}
