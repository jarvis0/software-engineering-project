package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

//flyweight
public final class GameColorFactory {
	
	private static final List<GameColor> COLORS = new ArrayList<>();

	private GameColorFactory() {
	}
	
	public static final GameColor makeColor(String colorName, String colorHex) {
		for(GameColor color : COLORS) {
			if(color.toString().equals(colorName)) {
				return color;
			}
		}
		GameColor color = new GameColor(colorName, colorHex);
		COLORS.add(color);
		return color;
	}
	
}
