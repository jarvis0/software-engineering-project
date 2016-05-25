package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.InvalidCityException;
import it.polimi.ingsw.ps23.model.map.Region;

public class GroupRegionalCityFactory {

	public List<Region> makeRegions(List<String[]> rawRegion, Map<String, City> cities) {
		ArrayList<Region> groupRegionalCities = new ArrayList<>();
		String idBonus = rawRegion.remove(rawRegion.size() - 1)[1];
		for(String[] region : rawRegion) {
			VictoryPointBonus bonus = new VictoryPointBonus(idBonus);
			bonus.setValue(Integer.parseInt(region[1]));
			Region regionalCity = new GroupRegionalCity(region[0], bonus);
			for(int i = 2; i < region.length; i++){
				try {
					regionalCity.addCity(cities.get(region[i]));
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
