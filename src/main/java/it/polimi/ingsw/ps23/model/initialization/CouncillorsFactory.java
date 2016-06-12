package it.polimi.ingsw.ps23.model.initialization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.model.map.regions.Councillor;

public class CouncillorsFactory {

	private static final int SAME_COLORED_COUNCILLORS_POSITION = 0;
	private static final int COUNCILLOR_COLOR_HEX_POSITION = 1;
	private static final int COUNCILLOR_COLOR_NAME_POSITION = 2;
	
	public FreeCouncillorsSet makeCouncillors(List<String[]> rawCouncillors) {
		List<Councillor> councillors = new ArrayList<>();
		for(String[] rawCouncillor : rawCouncillors) {
			int sameColorCouncillorNumber = Integer.parseInt(rawCouncillor[SAME_COLORED_COUNCILLORS_POSITION]);
			for(int i = 0; i < sameColorCouncillorNumber; i++) {
				councillors.add(new Councillor(GameColorFactory.makeColor(rawCouncillor[COUNCILLOR_COLOR_NAME_POSITION], rawCouncillor[COUNCILLOR_COLOR_HEX_POSITION])));
			}
		}
		Collections.shuffle(councillors);
		return new FreeCouncillorsSet(councillors);
	}
}



