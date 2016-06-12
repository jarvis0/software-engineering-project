package it.polimi.ingsw.ps23.model.initialization;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.GameColor;

public final class GameColorFactory {
	
	private static final List<GameColor> COLORS = new ArrayList<>();

	private GameColorFactory() {
	}
	
	public static final GameColor makeColor(String colorName, String colorHex) {
		for(GameColor color : COLORS) {
			if(color.toString().equals(colorName) && color.getHex().equals(Color.decode(colorHex))) {
				return color;
			}
		}
		GameColor color = new GameColor(colorName, colorHex);
		COLORS.add(color);
		return color;
	}
	
}
