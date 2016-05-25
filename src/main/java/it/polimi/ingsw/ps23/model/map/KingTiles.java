package it.polimi.ingsw.ps23.model.map;

import java.util.Stack;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class KingTiles {
	
	private Stack<Bonus> kingTiles;
	
	public KingTiles(Stack<Bonus> tilesStack) {
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
