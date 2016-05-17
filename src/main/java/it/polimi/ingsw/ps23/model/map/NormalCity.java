package it.polimi.ingsw.ps23.model.map;

import java.awt.Color;
import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.Player;

public class NormalCity extends City {
 //	RewardToken cityToken;
	
	public NormalCity(String name, Color color){ /* RewardToken cityToken)*/
		this.name = name;
		this.color = color;
		emporiumList = new ArrayList<Player>();
		//this.cityToken = cityToken;
	}
}
