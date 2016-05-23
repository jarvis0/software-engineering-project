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
	
	private static final String CAPITAL = "Capital";
	
	public Map<String, City> makeCities(List<String[]> rawCities, List<String[]> rawRewardTokens) {
		ArrayList<RewardToken> rewardTokens = (ArrayList<RewardToken>) new RewardTokenFactory().makeRewardTokens(rawRewardTokens);
		Collections.shuffle(rewardTokens);
		HashMap<String, City> cities = new HashMap<>();
		for(String[] rawCity : rawCities) {
			if(!rawCity[3].equals(CAPITAL)) {
				cities.put(rawCity[0], new NormalCity(rawCity[0], GameColorFactory.makeColor(rawCity[2], rawCity[1]), rewardTokens.remove(rewardTokens.size() - 1)));
			}
			else {
				cities.put(rawCity[0], new CapitalCity(rawCity[0], GameColorFactory.makeColor(rawCity[2], rawCity[1])));
			}
		}
		return cities;
	}

}
