package it.polimi.ingsw.ps23.server.model.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BuildingPermitBonus;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonus;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class SuperBonusState implements State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6033290786328005099L;
	private TurnHandler turnHandler;
	private List<Bonus> superBonus;
	private Player currentPlayer;
	
	 public SuperBonusState(TurnHandler turnHandler) {
		 this.turnHandler = turnHandler;
		 superBonus = new ArrayList<>();
	}

	public boolean hasNext() {
		return !superBonus.isEmpty();
	}
	 
	public String useBonus(Bonus bonus) {
		return ((SuperBonus) bonus).checkBonus(currentPlayer);
	}

	public Bonus getCurrentBonus() {
		return superBonus.remove(superBonus.size() - 1);
	}

	public SuperBonusGiver createSuperBonusesGiver(Map<Bonus, List<String>> selectedBonuses) {
		return new SuperBonusGiver(selectedBonuses);
	}

	public boolean isBuildingPemitTileBonus(Bonus currentBonus) {
		return currentBonus instanceof BuildingPermitBonus;
	}

	public void analyzeInput(String chosenRegion, Bonus currentBonus) {
		((BuildingPermitBonus) currentBonus).selectRegion(chosenRegion);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		superBonus.addAll(turnHandler.getSuperBonuses());
		currentPlayer = game.getCurrentPlayer();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
}	
	