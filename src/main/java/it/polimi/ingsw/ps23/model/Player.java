package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.map.City;

public class Player {
	
	private String id;
	private int coin; //esiste un limite massimo? nel gioco è 20
	private int assistant;
	private BuiltEmporiums builtEmporiums;
	private int victoryPoints;
	private int nobilityTrackPoints;
	private HandDeck permissionHandDeck;
	private HandDeck politicHandDeck;	

	public Player(String id, int coin, int assistant, HandDeck politicHandDeck) {
		this.id = id;
		this.coin = coin;
		this.assistant = assistant;
		this.politicHandDeck = politicHandDeck;
		victoryPoints = 0;
		nobilityTrackPoints = 0;
		builtEmporiums = new BuiltEmporiums();
		permissionHandDeck = new PermissionHandDeck();
		
	}
	/*
	public void showPlayerStatus(){
		
	}
	
	public void pickCard(){
		
	}
	*/
	public void updateVictoryPoints(int value){
		victoryPoints += value;
	}
	
	public void updateNobilityPoints(int value){
		nobilityTrackPoints += value;
	}
	
	public void updateCoins(int value) throws InsufficientResourcesException{
		if(coin + value >= 0){
			coin += value;
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
}
