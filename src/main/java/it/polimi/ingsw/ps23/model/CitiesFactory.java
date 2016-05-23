package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.map.RewardToken;
import it.polimi.ingsw.ps23.model.map.BonusSlot;
import it.polimi.ingsw.ps23.model.map.CapitalCity;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.NormalCity;

public class CitiesFactory {
	
	private static final String CAPITAL = "Capital";
	
	public Map<String, City> makeCities(List<String[]> rawCities, List<String[]> rawRewardTokens) {
		ArrayList<RewardToken> rewardTokens = (ArrayList<RewardToken>) makeRewardTokens(rawRewardTokens);
		Collections.shuffle(rewardTokens);
		Random random = new Random();
		HashMap<String, City> cities = new HashMap<>();
		for(String[] rawCity : rawCities) {
			if(!rawCity[3].equals(CAPITAL)) {
				cities.put(rawCity[0], new NormalCity(GameColorFactory.makeColor(rawCity[2], rawCity[1]), rewardTokens.remove(random.nextInt(rewardTokens.size()))));
			}
			else {
				cities.put(rawCity[0], new CapitalCity(GameColorFactory.makeColor(rawCity[2], rawCity[1])));
			}
		}
		return cities;
	}
	
	private List<RewardToken> makeRewardTokens(List<String[]> rawRewardTokens) {
		ArrayList<RewardToken> rewardTokens = new ArrayList<>();
		BonusCache.loadCache();
		String[] fields = rawRewardTokens.remove(rawRewardTokens.size() - 1);
		for(String[] rawRewardToken : rawRewardTokens) {
			BonusSlot rewardToken = new RewardToken();
			rewardTokens.add((RewardToken) new BonusesFactory().makeBonuses(fields, rawRewardToken, rewardToken));
		}
		return rewardTokens;
	}
	
}
