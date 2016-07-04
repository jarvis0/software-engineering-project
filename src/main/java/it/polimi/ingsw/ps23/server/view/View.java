package it.polimi.ingsw.ps23.server.view;

import it.polimi.ingsw.ps23.server.commons.modelview.ViewObserver;
import it.polimi.ingsw.ps23.server.commons.viewcontroller.ViewObservable;
/**
 * Class abstraction useful to implements all the feature that a
 * view needed to be contacted by the {@link Model} and to be observer by the {@link Controller}
 * @author Giuseppe Mascellaro
 *
 */
public abstract class View extends ViewObservable implements Runnable, ViewObserver, ViewVisitor {

}
