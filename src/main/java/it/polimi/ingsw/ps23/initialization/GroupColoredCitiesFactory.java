package it.polimi.ingsw.ps23.initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.bonus.Bonus;
import it.polimi.ingsw.ps23.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.model.map.InvalidCityException;
import it.polimi.ingsw.ps23.model.map.Region;
import it.polimi.ingsw.ps23.model.map.board.GroupColoredCity;
import it.polimi.ingsw.ps23.model.map.regions.City;

public class GroupColoredCitiesFactory {
	
	private static final int COLOR_POSITION = 0;
	private static final int BONUS_VALUE_POSITION = 1;
	private static final int BONUS_NAME_POSITION = 0;

	private List<Region> coloredGroupCities;
	
	public GroupColoredCitiesFactory() {
		coloredGroupCities = new ArrayList<>();
	}
	
	private void addCities(List<City> cities, String colorName, Region coloredGroup) {
		for(City city : cities) {
			if(city.getColor().equals(colorName)) {
				try {
					coloredGroup.addCity(city);
				} catch (InvalidCityException e) {
					Logger logger = Logger.getLogger(this.getClass().getName());
					logger.log(Level.SEVERE, "Cannot initializate GroupColoredCities.", e);
				}
			}
		}
	}
	
	public List<Region> makeGroup(List<String[]> rawColoredCities, List<City> cities) {
		String[] fields = rawColoredCities.remove(rawColoredCities.size() - 1);
		for(String[] rawColoredCity : rawColoredCities) {
			String colorName = rawColoredCity[COLOR_POSITION];
			Bonus bonus = new VictoryPointBonus(fields[BONUS_NAME_POSITION]);
			bonus.setValue(Integer.parseInt(rawColoredCity[BONUS_VALUE_POSITION]));
			Region coloredGroup = new GroupColoredCity(colorName, bonus);
			addCities(cities, colorName, coloredGroup);
			coloredGroupCities.add(coloredGroup);
		}
		return coloredGroupCities;
	}
	
}
