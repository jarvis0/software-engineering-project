package it.polimi.ingsw.ps23.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.model.map.board.KingTileSet;

//generalizzare i bonus? - nome?
public class KingTileFactory {
	
	private static final int BONUS_VALUE_POSITION = 0;
	private static final int BONUS_NAME_POSITION = 0;
	
	public KingTileSet makeTiles(List<String[]> rawKingTiles) {
		Deque<Bonus> tilesStack = new LinkedList<>();
		String[] fields = rawKingTiles.remove(rawKingTiles.size() - 1);
		for(String[] rawTile: rawKingTiles) {
			Bonus bonus = new VictoryPointBonus(fields[BONUS_NAME_POSITION]);
			bonus.setValue(Integer.parseInt(rawTile[BONUS_VALUE_POSITION]));
			tilesStack.push(bonus);
		}
		return new KingTileSet(tilesStack);
	}

}
