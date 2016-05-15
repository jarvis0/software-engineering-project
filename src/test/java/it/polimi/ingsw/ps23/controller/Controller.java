package it.polimi.ingsw.ps23.controller;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps23.model.Model;
import it.polimi.ingsw.ps23.view.View;

public class Controller implements Observer {

	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
	}

}
