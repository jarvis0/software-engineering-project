package it.polimi.ingsw.ps23;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.PermissionCard;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.player.PermissionHandDeck;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;

@SuppressWarnings("unused")
public class MarketTransationTest {
	
	@Ignore
	public void test() {
		/*List<Card> politicCards = new ArrayList<>();
		List<Card> permissionCards = new ArrayList<>();
		List<String> playersName = new ArrayList<>();
		playersName.add("Player 1");
		playersName.add("Player 2");
		GameColor color = new GameColor("Green", "0xf0ffff");
		politicCards.add(new PoliticCard(color));
		color = new GameColor("Purple", "0x7fffff");
		politicCards.add(new PoliticCard(color));
		PoliticHandDeck politicHandDeck = new PoliticHandDeck(politicCards);
		Player player1 = new Player(playersName.get(0), 10, 10, politicHandDeck);
		Player player2 = new Player(playersName.get(1), 0, 0, new PoliticHandDeck(new ArrayList<>()));
		PermissionCard permissionCard = new PermissionCard();
		PermissionHandDeck permissionHandDeck = new PermissionHandDeck();
		permissionCards.add(permissionCard);
		permissionHandDeck.addCard(permissionCard);
		MarketObject marketObject = new MarketObject(player1, permissionCards, politicCards, 10, 1);
		try {
			Game game = new Game(playersName);
		} catch (NoCapitalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
