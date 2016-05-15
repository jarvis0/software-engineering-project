package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.map.City;

public class Player {
	
	private String id;
	private int victoryPoints;
	private int nobilityTrackPoints;
	private int gold;
	private int assistant;
	private ArrayList<City> buildedEmporium;
	//private ArrayList<PermissionHandDeck> permissionHand;
	//private ArrayList<PoliticHandDeck> politicHand;
	
	public Player(String id, int gold,int assistant) {
		this.id = id;
		victoryPoints = 0;
		nobilityTrackPoints = 0;
		this.gold = gold;
		this.assistant = assistant;
		buildedEmporium = new ArrayList<City>();
		//permissionHand = new ArrayList<PermissionHandDeck>();
		//politicHand = new ArrayList<PoliticHandDeck>();
	}
	
	public void showPlayerStatus(){
		
	}
	
	public void pickCard(){
		
	}
	
	public void updateVictoryPoints(int value){
		victoryPoints += value;
	}
	
	public void updateNobilityPoints(int value){
		nobilityTrackPoints += value;
	}
	
	public void updateGolds(int value) throws InsufficientResourcesException{
		if(gold + value >= 0){
			gold += value;
		}
		else{
			throw new InsufficientResourcesException();
		}
	}
	
	public void updateAssistants(int value) throws InsufficientResourcesException{
		if(assistant + value >= 0){
			assistant += value;
		}
		else{
			throw new InsufficientResourcesException();
		}
	}
	
	public int remaingEmporium() {
		return buildedEmporium.size();// - tutte le città della mappa
	}
	
	

}
