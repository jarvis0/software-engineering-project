package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

class GroupRegionalCitiesFactory {

	private static final int REGION_NAME_POSITION = 0;
	private static final int BONUS_VALUE_POSITION = 1;
	private static final int BONUS_NAME_POSITION = 0;
	
	List<Region> makeRegions(List<String[]> rawRegions, Map<String, City> cities, Map<String, List<String>> citiesConnections) {
		ArrayList<Region> groupRegionalCities = new ArrayList<>();
		String rawBonus = rawRegions.remove(rawRegions.size() - 1)[BONUS_NAME_POSITION];
		for(String[] rawRegion : rawRegions) {
			Bonus bonus = new VictoryPointBonus(rawBonus);
			bonus.setValue(Integer.parseInt(rawRegion[BONUS_VALUE_POSITION]));
			Region regionalCity = new GroupRegionalCity(rawRegion[REGION_NAME_POSITION], bonus, citiesConnections);
			for(int i = BONUS_VALUE_POSITION + 1; i < rawRegion.length; i++){
				try {
					regionalCity.addCity(cities.get(rawRegion[i]));
				}
				catch(InvalidCityException e) {
					Logger logger = Logger.getLogger(this.getClass().getName());
					logger.log(Level.SEVERE, "Cannot initializate GroupRegionalCities.", e);
				}
			}
			regionalCity.toCitiesList();
			groupRegionalCities.add(regionalCity);
		}
		return groupRegionalCities;		
	}
}
