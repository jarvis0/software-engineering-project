package it.polimi.ingsw.ps23.model.state;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public interface State {
	
	public void changeState(Context context, Game game);
	
	public void acceptView(ViewVisitor view);
	
}
