package it.polimi.ingsw.ps23;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.model.RawObject;

public class TestRawObject {

	private static final String TEST_PATH = "src/test/java/it/polimi/ingsw/ps23/csv/";
	private static final String TEST_CSV = "test.csv";
	
	@Test
	public void test() {
		List<String[]> rawObject0 = new RawObject(TEST_PATH + TEST_CSV).getRawObject();
		String[] firstRow = {"prova0", "prova1"};
		String[] secondRow = {"prova2", "prova3"};
		assertTrue(firstRow[0].equals(rawObject0.get(0)[0]));
		assertTrue(secondRow[0].equals(rawObject0.get(1)[0]));
		assertTrue(firstRow[1].equals(rawObject0.get(0)[1]));
		assertTrue(secondRow[1].equals(rawObject0.get(1)[1]));
		
	}

}
