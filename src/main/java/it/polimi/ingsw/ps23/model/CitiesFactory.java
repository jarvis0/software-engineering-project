package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.map.RewardToken;
import it.polimi.ingsw.ps23.model.map.RewardTokenFactory;
import it.polimi.ingsw.ps23.model.map.CapitalCity;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.NormalCity;

public class CitiesFactory {
	
	private static final String CAPITAL = "capital";
	private List<City> cities;
	
	public CitiesFactory() {
		cities = new ArrayList<>();
	}
	
	public void makeCities(List<String[]> rawCities, List<String[]> rawRewardTokens) {
		ArrayList<RewardToken> rewardTokens = (ArrayList<RewardToken>) new RewardTokenFactory().makeRewardTokens(rawRewardTokens);
		Collections.shuffle(rewardTokens);
		for(String[] rawCity : rawCities) {
			if(!rawCity[3].equals(CAPITAL)) {
				cities.add(new NormalCity(rawCity[0], GameColorFactory.makeColor(rawCity[2], rawCity[1]), rewardTokens.remove(rewardTokens.size() - 1)));
			}
			else {
				cities.add(new CapitalCity(rawCity[0], GameColorFactory.makeColor(rawCity[2], rawCity[1])));
			}
		}
	}
	
	public List<City> getCities() {
		return cities;
	}

	public Map<String, City> getHashMap() {
		Map<String, City> citiesMap = new HashMap<>();
		for(City city : cities) {
			citiesMap.put(city.getName(), city);
		}
		return citiesMap;
	}

}
