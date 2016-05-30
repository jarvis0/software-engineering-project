package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.GroupColoredCity;
import it.polimi.ingsw.ps23.model.map.InvalidCityException;
import it.polimi.ingsw.ps23.model.map.Region;

public class GroupColoredCitiesFactory {
	
	private static final int COLOR_POSITION = 0;
	private static final int BONUS_VALUE_POSITION = 1;
	private static final int BONUS_NAME_POSITION = 0;

	public List<Region> makeGroup(List<String[]> rawColoredCities, List<City> cities) {
		List<Region> coloredGroupCities = new ArrayList<>();
		String[] fields = rawColoredCities.remove(rawColoredCities.size() - 1);
		for(String[] rawColoredCity : rawColoredCities) {
			String colorName = rawColoredCity[COLOR_POSITION];
			Bonus bonus = new VictoryPointBonus(fields[BONUS_NAME_POSITION]);
			bonus.setValue(Integer.parseInt(rawColoredCity[BONUS_VALUE_POSITION]));
			Region coloredGroup = new GroupColoredCity(colorName, bonus);
			for(City city : cities) {
				if(city.getColor().equals(colorName)) {
					try {
						coloredGroup.addCity(city);
					} catch (InvalidCityException e) {
						e.printStackTrace();
					}
				}
			}
			coloredGroupCities.add(coloredGroup);
		}
		return coloredGroupCities;
	}
	
}
