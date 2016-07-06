package it.polimi.ingsw.ps23.server.model.bonus;

import static org.junit.Assert.*;

import org.junit.Test;
/**
  * Tests all the changes made by {@link NullBonus} to the {@link Player} status.
 * @author Mirco Manzoni
 *
 */
public class TestNullBonus {

	@Test
	public void test() {
		NullBonus bonus = new NullBonus("Null Bonus");
		assertTrue(bonus.isNull());
		assertTrue(bonus.toString().contains("-"));		
	}

}
