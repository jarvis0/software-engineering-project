package it.polimi.ingsw.ps23.server.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.commons.viewcontroller.ControllerObserver;
import it.polimi.ingsw.ps23.server.model.Model;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.bonus.SuperBonusGiver;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransation;
import it.polimi.ingsw.ps23.server.model.state.State;

public class Controller implements ControllerObserver {

	private final Model model;
	
	public Controller(Model model) {
		this.model = model;
	}

	@Override
	public void update() {
		model.setPlayerTurn();
	}

	@Override
	public void update(State state) {
		model.setActionState(state);
	}

	@Override
	public void update(Action action) {
		try {
			model.doAction(action);
		} catch (InvalidCardException | AlreadyBuiltHereException | InvalidCouncillorException | InvalidCouncilException | InvalidRegionException | InvalidCityException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "IOException Occured", e);
			model.rollBack(e);
		} catch (InsufficientResourcesException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "IOException Occured", e);
			model.restartTurn(e);
		}
	}

	@Override
	public void update(MarketObject marketObject) {
		model.doOfferMarket(marketObject);
	}

	@Override
	public void update(MarketTransation marketTransation) {
		model.doBuyMarket(marketTransation);
	}

	@Override
	public void update(SuperBonusGiver superBonusGiver) {
		try {
			model.doSuperBonusesAcquisition(superBonusGiver);
		} catch (InvalidCardException e) {
			model.rollBack(e);
		}
	}

	@Override
	public void update(Exception e) {
		model.restartTurn(e);	
	}

}
