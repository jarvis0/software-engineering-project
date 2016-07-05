package it.polimi.ingsw.ps23.server.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.Connection;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCostException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidNumberOfAssistantException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.state.AcquireBusinessPermitTileState;
import it.polimi.ingsw.ps23.server.model.state.AdditionalMainActionState;
import it.polimi.ingsw.ps23.server.model.state.AssistantToElectCouncillorState;
import it.polimi.ingsw.ps23.server.model.state.BuildEmporiumKingState;
import it.polimi.ingsw.ps23.server.model.state.BuildEmporiumPermitTileState;
import it.polimi.ingsw.ps23.server.model.state.ChangePermitTilesState;
import it.polimi.ingsw.ps23.server.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.server.model.state.EndGameState;
import it.polimi.ingsw.ps23.server.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.SuperBonusState;

public class SocketGUIView extends SocketView {

	private SocketParametersCreator gameParameters;
	private boolean firstUIrefresh;
	
	public SocketGUIView(String clientName, Connection connection) {
		super(clientName, connection);
		gameParameters = new SocketParametersCreator();
		firstUIrefresh = true;
	}

	@Override
	public void visit(StartTurnState currentState) {
		if(firstUIrefresh) {
			getConnection().send(gameParameters.createUIStaticContents(currentState.getGameMap().getCities(), currentState.getNobilityTrack()));
			firstUIrefresh = false;
		}
		getConnection().send(gameParameters.createUIDynamicContents(currentState));
		Player player = currentState.getCurrentPlayer();
		if(player.getName().equals(getClientName())) {
			//getConnection().sendYesInput("Current player: " + player.toString() + " " + player.showSecretStatus() + "\n" + currentState.getAvaiableAction() + "\n\nChoose an action to perform? ");
			try {
				wakeUp(currentState.getStateCache().getAction(receive()));
			}
			catch(NullPointerException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot create the action.", e);
				wakeUp();
			}
		}
		else {
			//getConnection().sendNoInput("It's player " + player.getName() + " turn.");
			pause();
		}
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		getConnection().send(gameParameters.createElectCouncillor());
		String chosenCouncillor = receive();
		String chosenBalcony = receive();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));
	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		getConnection().send(gameParameters.createEngageAnAssistant());
		wakeUp(currentState.createAction());
	}

	@Override
	public void visit(ChangePermitTilesState currentState) {
		getConnection().send(gameParameters.createChangePermitTiles());
		wakeUp(currentState.createAction(receive()));
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		getConnection().send(gameParameters.createAcquireBusinessPermitTile());
		getConnection().send(String.valueOf(currentState.getPoliticHandSize()));
		String chosenCouncil = receive();
		List<String> removedCards = new ArrayList<>();
		int removedCardsNumber = Integer.parseInt(receive());
		for(int i = 0; i < removedCardsNumber; i++) {
			removedCards.add(receive());
		}
		int chosenTile = Integer.parseInt(receive());
		try {
			wakeUp(currentState.createAction(chosenCouncil, removedCards, chosenTile));
		} catch (InvalidCardException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			getState().setExceptionString(e.toString());
		}
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		getConnection().send(gameParameters.createAssistantToElectCouncillor());
		String chosenCouncillor = receive();
		String chosenBalcony = receive();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		getConnection().send(gameParameters.createAdditionalMainAction());
		wakeUp(currentState.createAction());
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		getConnection().send(gameParameters.createBuildKingEmpoium());
		getConnection().send(String.valueOf(currentState.getPoliticHandSize()));
		int removedCardsNumber = Integer.parseInt(getConnection().receive());
		List<String> removedCards = new ArrayList<>();
		for(int i = 0; i < removedCardsNumber; i++) {
			removedCards.add(getConnection().receive());
		}
		String arrivalCity = getConnection().receive();
		try {
			wakeUp(currentState.createAction(removedCards, arrivalCity));
		} catch (InvalidCardException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			getState().setExceptionString(e.toString());
		}
	}

	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		getConnection().send(gameParameters.createBuildPermitTile());
		String chosenCity = receive();
		int chosenCard = Integer.parseInt(receive());
		wakeUp(currentState.createAction(chosenCity, chosenCard));
	}

	private List<String> sellPoliticCard(MarketOfferPhaseState currentState) {
		List<String> chosenPoliticCards = new ArrayList<>();
		boolean canSellPoliticCards = currentState.canSellPoliticCards();
		getConnection().send(String.valueOf(canSellPoliticCards));
		if(canSellPoliticCards) {
			getConnection().send(String.valueOf(currentState.getPoliticHandSize()));
			int numberOfCards = Integer.parseInt(receive());
			for(int i = 0; i < numberOfCards && i < currentState.getPoliticHandSize(); i++) {
				chosenPoliticCards.add(receive());
			}
		}
		return chosenPoliticCards;
	}
	
	private List<Integer> sellPermitCards(MarketOfferPhaseState currentState) {
		List<Integer> chosenPermissionCards = new ArrayList<>();
		boolean canSellPermitTiles = currentState.canSellPermissionCards();
		getConnection().send(String.valueOf(canSellPermitTiles));
		if(canSellPermitTiles) {
			getConnection().send(String.valueOf(currentState.getPermissionHandSize()));
			int numberOfCards = Integer.parseInt(receive());
			for(int i = 0; i < numberOfCards && i < currentState.getPermissionHandSize(); i++) {
				chosenPermissionCards.add(Integer.parseInt(receive()) - 1);
			}
		}
		return chosenPermissionCards;
	}
	
	private int sellAssistant(MarketOfferPhaseState currentState) {
		int chosenAssistants = 0;
		boolean canSellAssistants = currentState.canSellAssistants();
		getConnection().send(String.valueOf(canSellAssistants));
		if(canSellAssistants) {
			getConnection().send(String.valueOf(currentState.getAssistants()));
			chosenAssistants = Integer.parseInt(receive());
		}
		return chosenAssistants;
	}

	@Override
	public void visit(MarketOfferPhaseState currentState) {
		getConnection().send(gameParameters.createMarketOfferPhase());
		String playerName = currentState.getPlayerName();
		getConnection().send(playerName);
		if(playerName.equals(getClientName())) {
			List<String> politicCards = sellPoliticCard(currentState);
			List<Integer> chosenPermissionCards = sellPermitCards(currentState);
			int chosenAssistants = sellAssistant(currentState);
			int cost = Integer.parseInt(receive());
			try {
				wakeUp(currentState.createMarketObject(politicCards, chosenPermissionCards, chosenAssistants, cost));
			} catch (InvalidCardException | InvalidNumberOfAssistantException | InvalidCostException | NumberFormatException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
				getState().setExceptionString(e.toString());
			}
		}
		else {
			pause();
		}
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {
		getConnection().send(gameParameters.createMarketBuyPhase());
		String playerName = currentState.getPlayerName();
		getConnection().send(playerName);
		if(playerName.equals(getClientName())) {
			try {
				boolean canBuy = currentState.canBuy();
				getConnection().send(String.valueOf(canBuy));
				if(canBuy) {
					getConnection().send("Available offers: " + currentState.getAvaiableOffers());
					wakeUp(currentState.createTransation(Integer.parseInt(receive())));
				}
				else {
					getConnection().sendNoInput("You can buy nothing.");
					wakeUp(currentState.createTransation());
				}
			} catch(NumberFormatException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			}
		}
		else {
			pause();
		}
	}
	
	private void additionalOutput(SuperBonusState currentState) throws InvalidRegionException {
		boolean isBuildingPermitTileBonus = currentState.isBuildingPemitTileBonus();
		getConnection().send(String.valueOf(isBuildingPermitTileBonus));
		if (isBuildingPermitTileBonus) {
			getConnection().send(currentState.useBonus());
			String chosenRegion = receive();
			currentState.analyzeInput(chosenRegion);
		}
	}

	@Override
	public void visit(SuperBonusState currentState) {
		getConnection().send(gameParameters.createSuperBonus());
		boolean otherBonus = currentState.hasNext();
		getConnection().send(String.valueOf(otherBonus));
		while(otherBonus) {
			String selectedItem;
			int numberOfCurrentBonus = currentState.getCurrentBonusValue();
			getConnection().send(String.valueOf(numberOfCurrentBonus));
			for (int numberOfBonuses = 0; numberOfBonuses < numberOfCurrentBonus; numberOfBonuses++) {
				try {
					currentState.checkKey();
					additionalOutput(currentState);
					getConnection().send(currentState.useBonus());
					boolean isRecycleBuildingPermitBonus = currentState.isRecycleBuildingPermitBonus();
					getConnection().send(String.valueOf(isRecycleBuildingPermitBonus));
					if(isRecycleBuildingPermitBonus) {
						selectedItem = getConnection().receive();
					}
					boolean isRecycleRewardTokenBonus = currentState.isRecycleRewardTokenBonus();
					getConnection().send(String.valueOf(isRecycleRewardTokenBonus));
					selectedItem = getConnection().receive();
					getConnection().send(currentState.useBonus());
					currentState.addValue(selectedItem);
				} catch (InvalidRegionException | InvalidCityException | InvalidCardException e) {
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
					currentState.setExceptionString(e.toString());//TODO state?
				}
			}
			otherBonus = currentState.hasNext();
			getConnection().send(String.valueOf(otherBonus));
		}
		currentState.confirmChange();
		wakeUp(currentState.createSuperBonusesGiver());
	}

	@Override
	public void visit(EndGameState currentState) {
		getConnection().send(gameParameters.createEndGame());
		getConnection().send(currentState.getWinner());
		setEndGame(true);
	}

}
