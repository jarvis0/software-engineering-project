package it.polimi.ingsw.ps23.server.model.bonus;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestNullBonus {

	@Test
	public void test() {
		NullBonus bonus = new NullBonus("Null Bonus");
		assertTrue(bonus.isNull());
		assertTrue(bonus.toString().contains("-"));		
	}

}
