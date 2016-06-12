package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public interface State {
	
	public void changeState(Context context, Game game);
	
	public void acceptView(ViewVisitor view);
	
}
