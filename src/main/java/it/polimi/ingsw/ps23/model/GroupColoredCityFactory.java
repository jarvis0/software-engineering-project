package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.GroupColoredCity;
import it.polimi.ingsw.ps23.model.map.InvalidCityException;
import it.polimi.ingsw.ps23.model.map.Region;

public class GroupColoredCityFactory {
	
	List<Region> coloredGroupCitiesList;
	
	public GroupColoredCityFactory() {
		coloredGroupCitiesList = new ArrayList<>();
	}
	
	public List<Region> makeGroup(List<String[]> rawIds, List<City> cities){
		String[] fields = rawIds.remove(rawIds.size() - 1);
		for(String[] rawId : rawIds) {
			String colorName = rawId[0];
			Bonus bonus = new VictoryPointBonus(fields[1]);
			bonus.setValue(Integer.parseInt(rawId[1]));
			Region coloredGroup = new GroupColoredCity(colorName, (VictoryPointBonus) bonus);
			for(City city : cities){
				if(city.getColor().equals(colorName)){
					   try {
						coloredGroup.addCity(city);
					} catch (InvalidCityException e) {
						e.printStackTrace();
					}
				}
			coloredGroupCitiesList.add(coloredGroup);
			}
		}
		return coloredGroupCitiesList;
	}
}
