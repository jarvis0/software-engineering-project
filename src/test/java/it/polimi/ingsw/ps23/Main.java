package it.polimi.ingsw.ps23;

import it.polimi.ingsw.ps23.controller.Controller;
import it.polimi.ingsw.ps23.model.Model;
import it.polimi.ingsw.ps23.model.ModelView;
import it.polimi.ingsw.ps23.view.ConsoleView;
import it.polimi.ingsw.ps23.view.View;

public class Main {
	
	private Model model;
	private ModelView modelView;
	private View view;
	private Controller controller;
	
	public Main() {
		model = new Model();
		modelView = new ModelView();
		view = new ConsoleView(System.in, System.out);
		controller = new Controller(model, view);
		view.addObserver(controller);
		model.addObserver(modelView);
		modelView.addObserver(view);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	private void run() {
		view.run();
	}
}
