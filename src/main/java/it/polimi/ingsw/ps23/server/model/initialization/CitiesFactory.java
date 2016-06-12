package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.initialization.RewardTokenFactory;
import it.polimi.ingsw.ps23.server.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardTokenSet;

class CitiesFactory {
	
	private static final int CITY_NAME_POSITION = 0;
	private static final int CITY_COLOR_HEX_POSITION = 1;
	private static final int CITY_COLOR_NAME_POSITION = 2;
	private static final int CITY_TYPE_POSITION = 3;
	private static final String CAPITAL = "capital";
	
	private List<City> citiesList;
	private Map<String, City> citiesMap;
	
	CitiesFactory() {
		citiesList = new ArrayList<>();
		citiesMap = new HashMap<>();
	}
	
	void makeCities(List<String[]> rawCities, List<String[]> rawRewardTokens) {
		RewardTokenSet rewardTokens = new RewardTokenFactory().makeRewardTokenSet(rawRewardTokens);
		for(String[] rawCity : rawCities) {
			if(!rawCity[CITY_TYPE_POSITION].equals(CAPITAL)) {
				citiesList.add(new NormalCity(rawCity[CITY_NAME_POSITION], GameColorFactory.makeColor(rawCity[CITY_COLOR_NAME_POSITION], rawCity[CITY_COLOR_HEX_POSITION]), rewardTokens.removeRewardToken(rewardTokens.rewardTokenSize() - 1)));
				citiesMap.put(citiesList.get(citiesList.size() - 1).getName(), citiesList.get(citiesList.size() - 1));
			}
			else {
				citiesList.add(new CapitalCity(rawCity[CITY_NAME_POSITION], GameColorFactory.makeColor(rawCity[CITY_COLOR_NAME_POSITION], rawCity[CITY_COLOR_HEX_POSITION])));
				citiesMap.put(citiesList.get(citiesList.size() - 1).getName(), citiesList.get(citiesList.size() - 1));
			}
		}
	}
	
	List<City> getCities() {
		return citiesList;
	}

	Map<String, City> getHashMap() {
		return citiesMap;
	}

}
