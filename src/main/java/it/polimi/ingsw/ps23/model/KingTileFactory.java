package it.polimi.ingsw.ps23.model;

import java.util.List;
import java.util.Stack;
import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.model.map.KingTiles;

public class KingTileFactory {
	
	KingTiles kingTiles;
	Stack<Bonus> tilesStack;
	
	public KingTileFactory() {
		tilesStack = new Stack<>();
	}
	
	public KingTiles makeTiles(List<String[]> rawTiles) {
		String[] fields = rawTiles.remove(rawTiles.size() - 1);
		for(String[] tile: rawTiles) {
			Bonus bonus = new VictoryPointBonus(fields[0]);
			bonus.setValue(Integer.parseInt(tile[0]));
			tilesStack.push(bonus);
		}
		return kingTiles = new KingTiles(tilesStack);
	}

}
