package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.RealBonus;
import it.polimi.ingsw.ps23.server.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

class GroupRegionalCitiesBuilder {

	private static final int REGION_NAME_POSITION = 0;
	private static final int BONUS_VALUE_POSITION = 1;
	private static final int BONUS_NAME_POSITION = 0;
	
	List<Region> makeRegions(List<String[]> rawRegions, Map<String, City> cities, Map<String, List<String>> citiesConnections) {
		ArrayList<Region> groupRegionalCities = new ArrayList<>();
		String rawBonus = rawRegions.remove(rawRegions.size() - 1)[BONUS_NAME_POSITION];
		for(String[] rawRegion : rawRegions) {
			Bonus bonus = new VictoryPointBonus(rawBonus);
			((RealBonus)bonus).setValue(Integer.parseInt(rawRegion[BONUS_VALUE_POSITION]));
			Region regionalCity = new GroupRegionalCity(rawRegion[REGION_NAME_POSITION], bonus, citiesConnections);
			for(int i = BONUS_VALUE_POSITION + 1; i < rawRegion.length; i++){
				regionalCity.addCity(cities.get(rawRegion[i]));
			}
			regionalCity.toCitiesList();
			groupRegionalCities.add(regionalCity);
		}
		return groupRegionalCities;		
	}
}
