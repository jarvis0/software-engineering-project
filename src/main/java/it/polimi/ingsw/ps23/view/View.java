package it.polimi.ingsw.ps23.view;

import it.polimi.ingsw.ps23.server.commons.modelview.ViewObserver;
import it.polimi.ingsw.ps23.server.commons.viewcontroller.ViewObservable;

public abstract class View extends ViewObservable implements ViewObserver, Runnable {

	public abstract void threadWakeUp();

}
