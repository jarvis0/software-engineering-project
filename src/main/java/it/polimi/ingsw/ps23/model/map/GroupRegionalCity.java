package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;

public class GroupRegionalCity extends Region{
	
	private Council council;
	private ArrayList<PermissionCard> permissionCard;
	
	public GroupRegionalCity(String id, VictoryPointBonus victoryPointBonus) {
		super(id,victoryPointBonus);
		permissionCard = new ArrayList<>();
	}
	
	public void addCouncil(Council council) {
		this.council = council;
	}
	
	public void addPermissionCard(ArrayList<PermissionCard> permissionCards) {
		this.permissionCard.addAll(permissionCards);
	}

	@Override
	public void takeBonus(Player player) {
		//for(ogni bonus in bonustile lo devo aggiungere al giocatore)
	}
	
	@Override
	public String toString() {
		return super.toString() + " [Council: " + council + "]";
	}

	
	
	
	

}
