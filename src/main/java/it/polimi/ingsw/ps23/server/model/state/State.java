package it.polimi.ingsw.ps23.server.model.state;

import java.io.Serializable;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public abstract class State implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3883706184904910069L;
	
	private String exceptionString = new String();
	
	public abstract void changeState(Context context, Game game);
	
	public abstract void acceptView(ViewVisitor view);
	
	public void setExceptionString(String exceptionString) {
		this.exceptionString = exceptionString;
	}
	
	public String getExceptionString() {
		return exceptionString;
	}
	
	public boolean arePresentException() {
		return exceptionString.length() != 0;
	}
	
}
