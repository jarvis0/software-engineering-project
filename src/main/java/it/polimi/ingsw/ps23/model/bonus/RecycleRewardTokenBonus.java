package it.polimi.ingsw.ps23.model.bonus;

import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.NormalCity;

public class RecycleRewardTokenBonus extends Bonus implements SuperBonus {
	
	private static final int  VALUE_POSITION = 0;
	
	public RecycleRewardTokenBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) throws InsufficientResourcesException {
		turnHandler.addSuperBonus(this);		
	}

	@Override
	public String checkBonus(Player currentPlayer) {
		return "choose the city for recycle the reward a token: (0 for skip)" +currentPlayer.getEmporiumForRecycleRewardToken().toString();
	
	}

	@Override
	public void acquireSuperBonus(List<String >input, Game game, TurnHandler turnHandler) {
		if(!(Integer.parseInt(input.get(VALUE_POSITION)) == 0)) {
			((NormalCity)game.getCurrentPlayer().getEmporiumForRecycleRewardToken().get(input.get(VALUE_POSITION).toUpperCase())).useRewardToken(game, turnHandler); 
		}
		
	}



}
