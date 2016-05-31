package it.polimi.ingsw.ps23.controller;

import java.util.List;

import it.polimi.ingsw.ps23.commons.viewcontroller.ControllerObserver;
import it.polimi.ingsw.ps23.model.Model;
import it.polimi.ingsw.ps23.model.state.Context;

public class Controller implements ControllerObserver {

	private final Model model;
	
	public Controller(Model model) {
		this.model = model;
	}

	@Override
	public void update(List<String> playersName) {
		model.setUpModel(playersName);
	}
	
	public void update() {
		model.setPlayerTurn();
	}

	@Override
	public void update(Context context) {

	}
	
	
}
