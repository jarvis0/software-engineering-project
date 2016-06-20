package it.polimi.ingsw.ps23.server.model.state;

import java.io.Serializable;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public interface State extends Serializable {
	
	public void changeState(Context context, Game game);
	
	public void acceptView(ViewVisitor view);
	
}
