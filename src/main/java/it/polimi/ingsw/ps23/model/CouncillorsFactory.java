package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.Councillor;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;

public class CouncillorsFactory {

	public FreeCouncillors makeCouncillors(List<String[]> rawCouncillors) {
		List<Councillor> councillors = new ArrayList<>();
		for(String[] rawCouncillor : rawCouncillors) {
			int sameColorCouncillorNumber = Integer.parseInt(rawCouncillor[0]);
			for(int i = 0; i < sameColorCouncillorNumber; i++) {
				councillors.add(new Councillor(GameColorFactory.makeColor(rawCouncillor[2], rawCouncillor[1])));
			}
		}
		Collections.shuffle(councillors);
		return new FreeCouncillors(councillors);
	}
}



