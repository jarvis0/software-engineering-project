package it.polimi.ingsw.ps23.view;

import it.polimi.ingsw.ps23.commons.modelview.ViewObserver;
import it.polimi.ingsw.ps23.commons.viewcontroller.ViewObservable;

public abstract class View extends ViewObservable implements ViewObserver, Runnable {
	
	@Override
	public void update() {
		//modelView richiama questo metodo
	}
	
}
