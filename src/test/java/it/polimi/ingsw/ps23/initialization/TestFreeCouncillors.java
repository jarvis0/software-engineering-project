package it.polimi.ingsw.ps23.initialization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.Councillor;
import it.polimi.ingsw.ps23.model.map.FreeCouncillorsSet;

public class TestFreeCouncillors {

	@Test
	public void test() {
		
		Queue<Councillor> councilQueue = new LinkedList<>();
		
		GameColor color2 = new GameColor("Green", "0xf0ffff");
		Councillor councillor2 = new Councillor(color2);
		councilQueue.add(councillor2);
		
		GameColor color3 = new GameColor("Purple", "0x7fffff");
		Councillor councillor3 = new Councillor(color3);
		councilQueue.add(councillor3);
		
		GameColor color4 = new GameColor("White", "0xf90fff");
		Councillor councillor4 = new Councillor(color4);
		councilQueue.add(councillor4);
		
		Council council = new Council(councilQueue);
		
		List<Councillor> freeCouncillors = new ArrayList<>();
		GameColor color0 = new GameColor("White", "0xffffff");
		Councillor councillor0 = new Councillor(color0);
		freeCouncillors.add(councillor0);
		GameColor color1 = new GameColor("Black", "0x0fffff");
		Councillor councillor1 = new Councillor(color1);
		freeCouncillors.add(councillor1);
		
		FreeCouncillorsSet freeCouncillors1 = new FreeCouncillorsSet(freeCouncillors);
		
		assertTrue(freeCouncillors1.toString().equals("[White, Black]"));
		
		freeCouncillors1.remove(1);
		
		assertTrue(freeCouncillors1.toString().equals("[White]"));
		System.out.println(freeCouncillors1);
		System.out.println(council);
		
		freeCouncillors1.electCouncillor("White", council);
		System.out.println(freeCouncillors1);
		System.out.println(council);
		
		assertTrue(council.toString().equals("[Purple, White, White]"));
		
		assertTrue(freeCouncillors1.toString().equals("[Green]"));


		
	}

}
