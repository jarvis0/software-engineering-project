package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.InvalidCityException;

public class GroupRegionalCityFactory {

	public ArrayList<GroupRegionalCity> makeRegions(List<String[]> rawRegion, HashMap<String, City> cities) {
		ArrayList<GroupRegionalCity> groupRegionalCities = new ArrayList<>();
		String idBonus = rawRegion.remove(rawRegion.size() - 1)[1];
		for(String[] region : rawRegion) {
			VictoryPointBonus bonus = new VictoryPointBonus(idBonus);
			bonus.setValue(Integer.parseInt(region[1]));
			GroupRegionalCity regionalCity = new GroupRegionalCity(region[0], bonus);
			for(int i = 2; i < region.length; i++){
				try {
					regionalCity.addCity(cities.get(region[i]));
				}
				catch(InvalidCityException e){}
			}			
			groupRegionalCities.add(regionalCity);
		}
		return groupRegionalCities;		
	}
}
