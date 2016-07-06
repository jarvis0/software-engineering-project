package it.polimi.ingsw.ps23.server.model.map.board;

import static org.junit.Assert.*;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.VictoryPointBonus;
/**
 * Tests the {@link KingRewardTileSet}'s methods gaining the bonus and checking the queue when is empty.
 * @author mirma
 *
 */
public class TestKingRewardTilesSet {

	@Test
	public void test() {
		Bonus bonus = new VictoryPointBonus("victoryPoints");
		Deque<Bonus> tilesStack = new LinkedList<>();
		tilesStack.add(bonus);
		KingRewardTilesSet kingRewardTilesSet = new KingRewardTilesSet(tilesStack);
		assertTrue(kingRewardTilesSet.getCurrentTile().equals(bonus));
		kingRewardTilesSet.pop();
		assertTrue(kingRewardTilesSet.isEmpty());
		
	}

}
