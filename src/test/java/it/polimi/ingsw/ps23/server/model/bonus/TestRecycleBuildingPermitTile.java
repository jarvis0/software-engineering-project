package it.polimi.ingsw.ps23.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.regions.BusinessPermitTile;

public class TestRecycleBuildingPermitTile {

	@Test
	public void test() throws InvalidCardException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		TurnHandler turnHandler = new TurnHandler();
		RecycleBuildingPermitBonus bonus = new RecycleBuildingPermitBonus("Recycle Building Permit Bonus");
		bonus.updateBonus(game, turnHandler);
		assertTrue(turnHandler.getSuperBonuses().contains(bonus));
		List<String> input = new ArrayList<>();
		input.add("0");
		BusinessPermitTile card = new BusinessPermitTile();
		Bonus additionalBonus = new CoinBonus("Coin Bonus");
		additionalBonus.setValue(1);
		card.addBonus(additionalBonus);
		List<Card> cards = new ArrayList<>();
		cards.add(card);
		game.getCurrentPlayer().buyPermitCards(cards);
		int initialCoins = game.getCurrentPlayer().getCoins();
		bonus.acquireSuperBonus(input, game, turnHandler);
		assertTrue(game.getCurrentPlayer().getCoins() == initialCoins);
	}

}
