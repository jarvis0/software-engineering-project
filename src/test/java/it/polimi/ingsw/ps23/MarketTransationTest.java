package it.polimi.ingsw.ps23;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.NoCapitalException;
import it.polimi.ingsw.ps23.model.PermissionHandDeck;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.PoliticHandDeck;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.model.map.regions.PermissionCard;
import it.polimi.ingsw.ps23.model.market.MarketObject;

@SuppressWarnings("unused")
public class MarketTransationTest {
	
	@Ignore
	public void test() {
		List<Card> politicCards = new ArrayList<>();
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
		}
	}

}
