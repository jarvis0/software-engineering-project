package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.RealBonus;
import it.polimi.ingsw.ps23.server.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.server.model.map.board.KingRewardTilesSet;

class KingTilesBuilder {
	
	private static final int BONUS_VALUE_POSITION = 0;
	private static final int BONUS_NAME_POSITION = 0;
	
	KingRewardTilesSet makeTiles(List<String[]> rawKingTiles) {
		Deque<Bonus> tilesStack = new LinkedList<>();
		String[] fields = rawKingTiles.remove(rawKingTiles.size() - 1);
		for(String[] rawTile: rawKingTiles) {
			Bonus bonus = new VictoryPointBonus(fields[BONUS_NAME_POSITION]);
			((RealBonus)bonus).setValue(Integer.parseInt(rawTile[BONUS_VALUE_POSITION]));
			tilesStack.push(bonus);
		}
		return new KingRewardTilesSet(tilesStack);
	}

}
