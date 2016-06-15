package it.polimi.ingsw.ps23.server.view;

import it.polimi.ingsw.ps23.server.commons.modelview.ViewObserver;
import it.polimi.ingsw.ps23.server.commons.viewcontroller.ViewObservable;

public abstract class View extends ViewObservable implements Runnable, ViewObserver, ViewVisitor {

}
