package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public interface State {
	
	public abstract void changeState(Context context, Game game);
	public abstract void acceptView(ViewVisitor view);
}
