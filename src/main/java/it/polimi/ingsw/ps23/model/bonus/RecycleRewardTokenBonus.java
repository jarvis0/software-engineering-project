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
		if(!currentPlayer.getEmporiumForRecycleRewardToken().getBuiltEmporiumSet().isEmpty()){
			return "You have encountred a Recycle Reward Token Bonus on Nobility Track \nchoose the city for recycle the reward a token: " +currentPlayer.getEmporiumForRecycleRewardToken().toString();
		}
		else {
			return "Impossible using Recycle Building Permit Bonus because your Built Emporium set is empty, or because all the Reward Tokens of the cities where you have built an emporium gives you Noblity Track Points (0 for skip)";
		}
	}
	@Override
	public void acquireSuperBonus(List<String >input, Game game, TurnHandler turnHandler) {
		if(!(Integer.parseInt(input.get(VALUE_POSITION)) == 0)) {
			((NormalCity)game.getCurrentPlayer().getEmporiumForRecycleRewardToken().get(input.get(VALUE_POSITION).toUpperCase())).useRewardToken(game, turnHandler); 
		}
		
	}



}
