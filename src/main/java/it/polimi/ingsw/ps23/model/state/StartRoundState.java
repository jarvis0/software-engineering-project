package it.polimi.ingsw.ps23.model.state;

public class StartRoundState implements State {

	private static final int START_PLAYER = 0;
	
	@Override
	public void changeState(Context context) {
		context.setState(this);
		context.setCurrentPlayer(START_PLAYER);
	}

}
