package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;

public class GameColorFactory {
	
	private static ArrayList<GameColor> colors = new ArrayList<>();
	
	public static GameColor makeColor(String colorName, String colorHex) {
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