package it.polimi.ingsw.ps23.client.rmi;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCostException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidNumberOfAssistantException;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.state.AcquireBusinessPermitTileState;
import it.polimi.ingsw.ps23.server.model.state.AdditionalMainActionState;
import it.polimi.ingsw.ps23.server.model.state.AssistantToElectCouncillorState;
import it.polimi.ingsw.ps23.server.model.state.BuildEmporiumKingState;
import it.polimi.ingsw.ps23.server.model.state.BuildEmporiumPermitTileState;
import it.polimi.ingsw.ps23.server.model.state.ChangePermitsTileState;
import it.polimi.ingsw.ps23.server.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.server.model.state.EndGameState;
import it.polimi.ingsw.ps23.server.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.server.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.server.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import it.polimi.ingsw.ps23.server.model.state.State;
import it.polimi.ingsw.ps23.server.model.state.SuperBonusState;

public class RMIGUIView extends RMIView {
	
	private static final String CANNOT_REACH_SERVER_PRINT = "Cannot reach remote server.";
	
	private RMISwingUI rmiSwingUI;
	private PrintStream output;
	private State state;
	private boolean endGame;
	private boolean waiting;
	private boolean firstUIRefresh;
	
	RMIGUIView(String playerName, PrintStream output) {
		super(playerName);
		firstUIRefresh = true;
		this.output = output;
	}

	public State getCurrentState() {
		return state;
	}

	@Override
	void setMapType(String mapType) {
		output.print("\nMap type: " + mapType + ".");
		rmiSwingUI = new RMISwingUI(mapType, getClientName());
	}

	@Override
	public void visit(StartTurnState currentState) {
		if(firstUIRefresh) {
			rmiSwingUI.loadStaticContents(currentState);
			firstUIRefresh = false;
		}
		rmiSwingUI.refreshDynamicContents(currentState);
		Player player = currentState.getCurrentPlayer();
		if(player.getName().equals(getClientName())) {
			rmiSwingUI.appendConsoleText("\nIt's your turn, please select an action from the pool displayed above.");
			rmiSwingUI.showAvailableActions(currentState.isAvailableMainAction(), currentState.isAvailableQuickAction(), this);
			pause();
			try {
				getControllerInterface().wakeUpServer(currentState.getStateCache().getAction(rmiSwingUI.getChosenAction()));
			} catch (RemoteException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
			}
		} else {
			rmiSwingUI.setConsoleText("\nIt's " + currentState.getCurrentPlayer().getName() + "'s turn.");
			rmiSwingUI.showAvailableActions(false, false, this);
			waiting = true;
			pause();
		}
		
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		rmiSwingUI.clearRMISwingUI();
		rmiSwingUI.showAvailableActions(false, false, this);
		rmiSwingUI.enableFreeCouncillorsButtons(true);
		rmiSwingUI.appendConsoleText("\n\nYou are performing a Elect councillor main action,\npress on a free councillor to select it.");
		pause();
		String chosenCouncillor = rmiSwingUI.getChosenCouncillor();
		rmiSwingUI.appendConsoleText("\nYou have chosen a " + chosenCouncillor + " Councillor,\nPress on the region where you want to put it ");
		rmiSwingUI.enableFreeCouncillorsButtons(false);
		rmiSwingUI.enableRegionButtons(true);
		rmiSwingUI.enableKingButton(true);
		pause();
		String chosenBalcony = rmiSwingUI.getChosenRegion();
		rmiSwingUI.appendConsoleText("\nYou have just elected a " + chosenCouncillor + " councillor in " + chosenBalcony + "'s balcony");
		sendAction(currentState.createAction(chosenCouncillor, chosenBalcony));

	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		rmiSwingUI.appendConsoleText("\n\nYou are performing a Engage An Assistant quick action");
		sendAction(currentState.createAction());

	}

	@Override
	public void visit(ChangePermitsTileState currentState) {
		rmiSwingUI.enableRegionButtons(true);
		rmiSwingUI.appendConsoleText("\n\nYou are performing a Change Permits Tile quick action,\n please select the region where you what to change tiles.");
		pause();
		String chosenRegion = rmiSwingUI.getChosenRegion();
		rmiSwingUI.enableRegionButtons(false);
		rmiSwingUI.appendConsoleText("\nYou have just changed the " + chosenRegion + "'s Permit Tiles");
		sendAction(currentState.createAction(chosenRegion));
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		try {
			rmiSwingUI.clearRMISwingUI();
			rmiSwingUI.showAvailableActions(false, false, this);
			rmiSwingUI.enableRegionButtons(true);
			rmiSwingUI.enableFinish(false);
			rmiSwingUI.appendConsoleText("\n\nYou are performing a Acquire Business Permit Tile main action,\npress on the region whose council you whant to satisfy");
			List<String> removedCards = new ArrayList<>();
			pause();
			rmiSwingUI.enableRegionButtons(false);
			String chosenCouncil = rmiSwingUI.getChosenRegion();
			rmiSwingUI.appendConsoleText("\nYou have selected the " + chosenCouncil + " council,\npress on the politic cards that you what to use for satisfy this council");
			rmiSwingUI.enablePoliticCards(true);
			int numberOfCards = 4;
			boolean finish = false;
			int i = 0;
			while (i < numberOfCards && i < currentState.getPoliticHandSize() && !finish) {
				pause();
				finish = rmiSwingUI.hasFinished();
				rmiSwingUI.enableFinish(true);
				if(!finish) {
					removedCards.add(rmiSwingUI.getChosenCard());
				}
				i++;
			}
			rmiSwingUI.appendConsoleText("\nYou have selected these politic cards:\n" + removedCards.toString() + "\nyou can now press on the permit tile that you want acquire.");
			rmiSwingUI.enablePoliticCards(false);
			rmiSwingUI.enablePermissonTilePanel(chosenCouncil);
			pause();
			int chosenTile = rmiSwingUI.getChosenTile();
			sendAction(currentState.createAction(chosenCouncil, removedCards, chosenTile));
		} catch (InvalidCardException | NumberFormatException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			state.setExceptionString(e.toString());
		}

	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		rmiSwingUI.clearRMISwingUI();
		rmiSwingUI.appendConsoleText("\n\nYou are performing an Assistant To Elect Councillor quick action,\npress on a free councillor to select it.");
		rmiSwingUI.showAvailableActions(false, false, this);
		rmiSwingUI.enableFreeCouncillorsButtons(true);
		pause();
		String chosenCouncillor = rmiSwingUI.getChosenCouncillor();
		rmiSwingUI.appendConsoleText("\nYou have chosen a " + chosenCouncillor + " Councillor,\npress on the region where you want to put it.");
		rmiSwingUI.enableFreeCouncillorsButtons(false);
		rmiSwingUI.enableRegionButtons(true);
		rmiSwingUI.enableKingButton(true);
		pause();
		String chosenBalcony = rmiSwingUI.getChosenRegion();
		rmiSwingUI.appendConsoleText("\nYou have just elected a " + chosenCouncillor + "councillor in " + chosenBalcony + "'s balcony");
		sendAction(currentState.createAction(chosenCouncillor, chosenBalcony));
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		rmiSwingUI.appendConsoleText("\n\nYou are performing an Additional Main Action quick action");
		sendAction(currentState.createAction());
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		List<String> removedCards = new ArrayList<>();
		rmiSwingUI.showAvailableActions(false, false, this);
		rmiSwingUI.enablePoliticCards(true);
		rmiSwingUI.enableFinish(false);
		rmiSwingUI.appendConsoleText("\n\nYou are performing a Build Emporium King Main Action,\npress on the politic cards thet you want to use for satisfy the King's council.");
		int numberOfCards = 4;
		boolean finish = false;
		int i = 0;
		while (i < numberOfCards && i < currentState.getPoliticHandSize() && !finish) {
			pause();
			finish = rmiSwingUI.hasFinished();
			rmiSwingUI.enableFinish(true);
			if(!finish) {
				removedCards.add(rmiSwingUI.getChosenCard());
			}
			i++;
		}
		rmiSwingUI.appendConsoleText("\nYou have selected these politic cards:\n" + removedCards.toString() + "\nplease press on the city where you want to move the King.");
		rmiSwingUI.enablePoliticCards(false);
		rmiSwingUI.enableCities(true);
		pause();
		rmiSwingUI.enableCities(false);
		String arrivalCity = rmiSwingUI.getChosenCity();
		try {
			sendAction(currentState.createAction(removedCards, arrivalCity));
		} catch (InvalidCardException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			state.setExceptionString(e.toString());
		}	
	}


	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		rmiSwingUI.clearRMISwingUI();
		rmiSwingUI.showAvailableActions(false, false, this);
		rmiSwingUI.enablePermitTileDeck(true);
		rmiSwingUI.appendConsoleText("\n\nYou are performing a Build Emporium Permit Tile Main Action,\npress on the permit tile that you want use.");
		pause();
		int chosenCard = rmiSwingUI.getChosenTile();
		rmiSwingUI.appendConsoleText("\nYou have chose tile number: " + chosenCard + "\npress on the city want to build.");
		rmiSwingUI.enableCities(true);
		pause();
		rmiSwingUI.enableCities(false);
		String chosenCity = rmiSwingUI.getChosenCity();
		sendAction(currentState.createAction(chosenCity, chosenCard));
	}
	
	private List<String> sellPoliticCard(MarketOfferPhaseState currentState) throws NumberFormatException {
		List<String> chosenPoliticCards = new ArrayList<>();
		if (currentState.canSellPoliticCards()) {
			rmiSwingUI.setConsoleText("\nHow many politic cards do you whant to sell? ");
			rmiSwingUI.enableMarketInputArea(true);
			pause();
			int numberOfCards = rmiSwingUI.getChosenValue();
			rmiSwingUI.enableMarketInputArea(false);
			rmiSwingUI.enablePoliticCards(true);
			rmiSwingUI.setConsoleText("\nplease press on the cards that you whant to sell");
			for (int i = 0; i < numberOfCards && i < currentState.getPoliticHandSize(); i++) {
				pause();
				chosenPoliticCards.add(rmiSwingUI.getChosenCard());
			}
		}
		return chosenPoliticCards;
	}
	
	private List<Integer> sellPermissionCard(MarketOfferPhaseState currentState) throws NumberFormatException {
		List<Integer> chosenPermissionCards = new ArrayList<>();
		if (currentState.canSellPermissionCards()) {
			rmiSwingUI.setConsoleText("\nHow many permission cards do you want to use? (numerical input >0)");
			rmiSwingUI.enableMarketInputArea(true);
			pause();
			int numberOfCards = rmiSwingUI.getChosenValue();
			rmiSwingUI.enableMarketInputArea(false);
			rmiSwingUI.enablePermitTileDeck(true);
			rmiSwingUI.setConsoleText("\nplease press on the cards that you whant to sell");
			for (int i = 0; i < numberOfCards && i < currentState.getPermissionHandSize(); i++) {
				pause();
				chosenPermissionCards.add(rmiSwingUI.getChosenTile());
			}
		}
		return chosenPermissionCards;
	}
	
	private int sellAssistant(MarketOfferPhaseState currentState) throws NumberFormatException {
		int chosenAssistants = 0;
		if (currentState.canSellAssistants()) {
			rmiSwingUI.setConsoleText("\nSelect the number of assistants " + currentState.getAssistants());
			rmiSwingUI.enableMarketInputArea(true);
			pause(); 
			chosenAssistants = rmiSwingUI.getChosenValue();
		}
		return chosenAssistants;
	}
	
	@Override
	public void visit(MarketOfferPhaseState currentState) {
		String player = currentState.getPlayerName();
		rmiSwingUI.setConsoleText("\nIt's " + player + " market phase turn.");
		if (player.equals(getClientName())) {
			List<String> chosenPoliticCards = sellPoliticCard(currentState);
			List<Integer> chosenPermissionCards = sellPermissionCard(currentState);
			int chosenAssistants = sellAssistant(currentState);
			rmiSwingUI.setConsoleText("\nChoose the price for your offer: ");
			rmiSwingUI.enableMarketInputArea(true);
			pause(); 
			int cost = rmiSwingUI.getChosenValue();
			rmiSwingUI.enableMarketInputArea(false);
			try {
				getControllerInterface().wakeUpServer(currentState.createMarketObject(chosenPoliticCards,
							chosenPermissionCards, chosenAssistants, cost));
			} catch (RemoteException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
			} catch (InvalidCardException | InvalidNumberOfAssistantException | InvalidCostException | NumberFormatException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
				state.setExceptionString(e.toString());
			}
		} else {
			waiting = true;
			pause();
		}
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {
		String player = currentState.getPlayerName();
		rmiSwingUI.setConsoleText("\nIt's " + player + " market phase turn.");
		if(player.equals(getClientName())) {
			try {
				if (currentState.canBuy()) {
					rmiSwingUI.setConsoleText("\nChoose the offert that you want to buy: \n" + currentState.getAvaiableOffers());
					rmiSwingUI.enableMarketInputArea(true);
					pause();
					int chosenOffert = rmiSwingUI.getChosenValue();
					rmiSwingUI.enableMarketInputArea(false);
					getControllerInterface().wakeUpServer(currentState.createTransation(chosenOffert - 1));
				} else {
					output.println("You can buy nothing.");
					getControllerInterface().wakeUpServer(currentState.createTransation());
				}
			} catch (RemoteException | NumberFormatException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			}
		} else {
			waiting = true;
			pause();
		}

	}
	
	/*private void additionalOutput(SuperBonusState currentState) throws InvalidRegionException {
		if (currentState.isBuildingPemitTileBonus()) {
			rmiSwingUI.setConsoleText("\n\n" + currentState.useBonus());
			rmiSwingUI.enableRegionButtons(true);
			pause();
			String chosenRegion = rmiSwingUI.getChosenRegion();
			currentState.analyzeInput(chosenRegion);
		}
	}*/

	@Override
	public void visit(SuperBonusState currentState) {
		/*try {
			while (currentState.hasNext()) {				
				int numberOfCurrentBonus = currentState.getCurrentBonusValue();
				for (int numberOfBonuses = 0; numberOfBonuses < numberOfCurrentBonus; numberOfBonuses++) {
					additionalOutput(currentState);
					rmiSwingUI.setConsoleText("\n\n" + currentState.useBonus());
					currentState.checkKey();
					currentState.addValue(scanner.nextLine());
					currentState.confirmChange();
				}
			}
		} catch (InvalidRegionException | InvalidCityException | InvalidCardException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			state.setExceptionString(e.toString());
		}
		try {
			getControllerInterface().wakeUpServer(currentState.createSuperBonusesGiver());
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
		}
	*/
	}

	@Override
	public void visit(EndGameState currentState) {
		rmiSwingUI.setConsoleText(currentState.getWinner());
		endGame = true;

	}
	
	private void sendAction(Action action) {
		try {
			getControllerInterface().wakeUpServer(action);
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
		}
	}

	protected boolean waitResumeCondition() {
		return state instanceof StartTurnState || state instanceof MarketBuyPhaseState || state instanceof MarketOfferPhaseState;
	}

	@Override
	public void update(State state) {
		this.state = state;
		if(waitResumeCondition() && waiting) {
			resume();
			waiting = false;
		}
	}

	@Override
	public synchronized void run() {
		waiting = true;
		pause();
		waiting = false;
		do {
			state.acceptView(this);
		} while(!endGame);
	}
	
}
