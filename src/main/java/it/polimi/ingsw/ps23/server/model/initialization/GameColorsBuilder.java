package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.GameColor;

final class GameColorsBuilder {
	
	private static final List<GameColor> COLORS = new ArrayList<>();

	private GameColorsBuilder() {
	}
	
	static final GameColor makeColor(String colorName) {
		for(GameColor color : COLORS) {
			if(color.toString().equals(colorName)) {
				return color;
			}
		}
		GameColor color = new GameColor(colorName);
		COLORS.add(color);
		return color;
	}
	
}
