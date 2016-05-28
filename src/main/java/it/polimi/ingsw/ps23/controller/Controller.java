package it.polimi.ingsw.ps23.controller;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps23.model.Model;
import it.polimi.ingsw.ps23.view.View;

public class Controller implements Observer {
	private  Action action;
	private final Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void update(Observable o, Integer choice) {
		if(o != view) {
			throw new IllegalArgumentException();
		}
		
		switch(choice){
		case 1:
			  action = new ElectCouncillor();
			break;
		default:
			break;
		}
		model.setAction(action);
		
	}
	
	@Override
	public void update() {
		if(o != view) {
			throw new IllegalArgumentException();
		}
		else {
			if(arg instanceof Integer) {
				if((int)arg < 2) {
					throw new IllegalArgumentException();
				}
				return;
			}
			else {
				if(arg instanceof List<?>) {
					model.setModel((List<String>) arg);
				}
				else {
					throw new IllegalArgumentException();
				}
			}
		}
	}

}
