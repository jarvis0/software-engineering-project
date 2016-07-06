package it.polimi.ingsw.ps23.server.model.map.regions;

import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.Region;
/**
 * Provides methods to manage a group of {@link City}, grouped by regions.
 * @author Alessandro Erba, Giuseppe Mascellaro, Mirco Manzoni
 *
 */
public class GroupRegionalCity extends Region {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -937978927421641632L;

	private static final int PERMIT_CARDS_UP_NUMBER = 2;
	
	private Council council;
	private Deck permitDeckDown;
	private Deck permitDeckUp;
	private Map<String, List<String>> citiesConnections;
	/**
	 * Constructs a regional city with the name of the region, the bonus of the region and the list of connection of
	 * cities in the region.
	 * @param name
	 * @param bonus
	 * @param citiesConnections
	 */
	public GroupRegionalCity(String name, Bonus bonus, Map<String, List<String>> citiesConnections) {
		super(name, bonus);
		this.citiesConnections = citiesConnections;
	}
	
	public void setCouncil(Council council) {
		this.council = council;
	}
	
	public void setPermitTiles(Deck permitTiles) {
		permitDeckDown = permitTiles;
		permitDeckUp = new Deck(permitDeckDown.pickCards(PERMIT_CARDS_UP_NUMBER));
	}
	
	public Deck getPermitTilesUp() {
		return permitDeckUp;
	}
	
	public Council getCouncil() {
		return council;
	}
	/**
	 * Removes a {@link BusinessPermitCard} from the visible {@link PermitDeck} and add another one card from
	 * the not visible {@link PermitDeck} to the visible.
	 * @param index - the index of the selected card
	 * @return removed card.
	 */
	public Card pickPermitTile(int index) {
		Card card = permitDeckUp.getCards().get(index);
		permitDeckUp.getCards().set(index, permitDeckDown.pickCard());
		return card;
	}
	/**
	 * Moves the visible {@link PermitDeck} to the back of the not visible {@link PermitDeck}
	 * and show two {@link BusinessPermitCard} from the top of not visible and move these on the visible
	 * {@link PermitDeck}.
	 */
	public void changePermitTiles() {
		int i = 0;
		for(Card card : permitDeckUp.getCards()) {
			permitDeckDown.getCards().add(0, card);
			permitDeckUp.getCards().set(i, permitDeckDown.pickCard());
			i++;
		}
	}

	@Override
	public String toString() {
		String print = "> " + getName() + ":\n";
		print += "\t- CITIES:\n";
		StringBuilder loopPrint = new StringBuilder();
		for(City city : getCitiesList()) {
			loopPrint.append("\t\t» " + city + " / connections with: " + citiesConnections.get(city.getName()) + "\n");
		}
		print += loopPrint;
		print += "\t- REGIONAL BONUS TILE: " + getBonusTile();
		if(alreadyUsedBonusTile()) {
			print += " (Already acquired)";
		}
		print += "\n\t- REGIONAL COUNCIL: " + council + "\n\t" + "- BUSINESS PERMIT TILES UP:";
		loopPrint = new StringBuilder();
		for(int i = 0; i < permitDeckUp.getCards().size(); i++) {
			loopPrint.append("\n\t\t» " + permitDeckUp.getCards().get(i).toString());
		}
		return print + loopPrint;
	}

}
