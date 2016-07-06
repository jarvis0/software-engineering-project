package it.polimi.ingsw.ps23.server.model;

import java.util.List;

import it.polimi.ingsw.ps23.server.view.SocketView;

/**
 * This class handles socket connection thread resume.
 * @author Giuseppe Mascellaro
 *
 */
public class PlayersResumeHandler {

	private List<SocketView> views;
	
	/**
	 * Saves the reference to list of sockets view to make it possible to
	 * notify socket views threads when a new game update comes.
	 * @param views
	 */
	public PlayersResumeHandler(List<SocketView> views) {
		this.views = views;
	}
	
	synchronized void resume() {
		for(SocketView view : views) {
			view.threadWakeUp();
		}
	}
	
}
