package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;

public class CoinBonus extends Bonus {

	public CoinBonus(String id) {
		super(id);
	}

	@Override
	public void updateBonus(Player player) throws InsufficientResourcesException {
		player.updateCoins(getValue());
	}
}
