package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;

public final class GameColorFactory {
	
	private static final ArrayList<GameColor> colors = new ArrayList<>();

	private GameColorFactory() {
	}
	
	public static final GameColor makeColor(String colorName, String colorHex) {
		for(GameColor color : colors) {
			if(color.toString().equals(colorName)) {
				return color;
			}
		}
		GameColor color = new GameColor(colorName, colorHex);
		colors.add(color);
		return color;
	}
}
