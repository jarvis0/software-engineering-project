package it.polimi.ingsw.ps23.server.model.map.board;

import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
/**
 * Provide methods to recognize the different politic cards in the {@link Deck}.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class PoliticCard implements Card {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5455561632780600930L;

	private static final String MULTI = "multi";
	
	private final GameColor color;
	/**
	 * Construct a politic card with a defined color.
	 * @param color - the color of the card
	 */
	public PoliticCard(GameColor color) {
		this.color = color;
	}
	
	public GameColor getColor() {
		return color;
	}
	
	/**
	 * @return true if this politic card is a multi color card.
	 */
	public boolean isJoker() {
		return color.toString().equals(MULTI);
	}
	
	@Override
	public String toString() {
		return color.toString();
	}

}
