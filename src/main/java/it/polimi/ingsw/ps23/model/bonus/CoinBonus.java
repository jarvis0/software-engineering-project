package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class CoinBonus extends Bonus {

	public CoinBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Player player, TurnHandler turnHandler) throws InsufficientResourcesException {
		player.updateCoins(getValue());
	}

	@Override
	public void updateBonusReward(Player player) throws InsufficientResourcesException {
		player.updateCoins(getValue());
	}
	
}
