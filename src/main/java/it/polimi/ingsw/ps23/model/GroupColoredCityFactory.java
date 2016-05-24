package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.GroupColoredCity;
import it.polimi.ingsw.ps23.model.map.Region;

public class GroupColoredCityFactory {
	
	ArrayList<Region> coloredGroupCitiesList = new ArrayList<>();
	public ArrayList<Region> makeGroup(List<String[]> rawIds, ArrayList<City> cities){
		String[] fields = rawIds.remove(rawIds.size() - 1);
		for(String[] rawId : rawIds) {
			ArrayList<String> coloredGroupCities = new ArrayList<>();
			String colorName = rawId[0];
			Bonus bonus = new VictoryPointBonus(fields[1]);
			bonus.setValue(Integer.parseInt(rawId[1]));
			for(City city : cities){
				if(city.getColor().equals(colorName)){
					coloredGroupCities.add(city.getName());
				}
			}
			coloredGroupCitiesList.add(new GroupColoredCity(colorName, coloredGroupCities, (VictoryPointBonus) bonus));
		}
		return coloredGroupCitiesList;
	}

}
