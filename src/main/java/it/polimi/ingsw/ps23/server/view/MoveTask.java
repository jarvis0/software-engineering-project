package it.polimi.ingsw.ps23.server.view;

import java.util.TimerTask;

class MoveTask extends TimerTask {

	private View view;
	
	MoveTask(View view) {
		this.view = view;
	}
		
	@Override
	public void run() {
		view.terminate();
	}
	
}
	

