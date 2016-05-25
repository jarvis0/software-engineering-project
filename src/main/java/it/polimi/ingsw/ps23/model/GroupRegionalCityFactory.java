package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.InvalidCityException;
import it.polimi.ingsw.ps23.model.map.Region;

//generalizzare i bonus? - nome?
public class GroupRegionalCityFactory {

	private static final int REGION_NAME_POSITION = 0;
	private static final int BONUS_VALUE_POSITION = 1;
	private static final int BONUS_NAME_POSITION = 0;
	
	public List<Region> makeRegions(List<String[]> rawRegions, Map<String, City> cities) {
		ArrayList<Region> groupRegionalCities = new ArrayList<>();
		String rawBonus = rawRegions.remove(rawRegions.size() - 1)[BONUS_NAME_POSITION];
		for(String[] rawRegion : rawRegions) {
			Bonus bonus = new VictoryPointBonus(rawBonus);
			bonus.setValue(Integer.parseInt(rawRegion[BONUS_VALUE_POSITION]));
			Region regionalCity = new GroupRegionalCity(rawRegion[REGION_NAME_POSITION], bonus);
			for(int i = BONUS_VALUE_POSITION + 1; i < rawRegion.length; i++){
				try {
					regionalCity.addCity(cities.get(rawRegion[i]));
				}
				catch(InvalidCityException e) {
					e.getStackTrace();
				}
			}			
			groupRegionalCities.add(regionalCity);
		}
		return groupRegionalCities;		
	}
}
