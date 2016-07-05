package it.polimi.ingsw.ps23.client.rmi;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.client.GUIView;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCostException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidNumberOfAssistantException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.model.actions.Action;
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

public class RMIGUIView extends RMIView implements GUIView {
	
	private static final String CANNOT_REACH_SERVER_PRINT = "Cannot reach remote server.";
	private static final String SKIP = "skip";
	private static final String ITS_PRINT = "\nIt's ";
	
	private RMISwingUI swingUI;
	private PrintStream output;
	private boolean endGame;
	private boolean firstUIRefresh;
	
	RMIGUIView(String playerName, PrintStream output) {
		super(playerName);
		firstUIRefresh = true;
		this.output = output;
	}

	private void sendAction(Action action) {
		try {
			getControllerInterface().wakeUpServer(action);
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
		}
	}

	@Override
	void setMapType(String mapType) {
		output.print("\nMap type: " + mapType + ".");
		swingUI = new RMISwingUI(this, mapType, getClientName());
	}

	@Override
	public void visit(StartTurnState currentState) {
		if(firstUIRefresh) {
			swingUI.loadStaticContents(currentState);
			firstUIRefresh = false;
		}
		swingUI.refreshDynamicContents(currentState);
		Player player = currentState.getCurrentPlayer();
		if(player.getName().equals(getClientName())) {
			swingUI.appendConsoleText("\nIt's your turn, please select an action from the pool displayed above.");
			swingUI.showAvailableActions(currentState.isAvailableMainAction(), currentState.isAvailableQuickAction());
			pause();
			try {
				if(!(swingUI.getChosenAction()).equals(SKIP)) {
					getControllerInterface().wakeUpServer(currentState.getStateCache().getAction(swingUI.getChosenAction()));
				}
				else {
					getControllerInterface().wakeUpServer();
				}
				
			} catch (RemoteException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
			}
		} else {
			swingUI.appendConsoleText(ITS_PRINT + currentState.getCurrentPlayer().getName() + "'s turn.");
			swingUI.showAvailableActions(false, false);
			setWaiting(true);
			pause();
		}
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		swingUI.clearSwingUI();
		swingUI.showAvailableActions(false, false);
		swingUI.enableFreeCouncillorsButtons(true);
		swingUI.appendConsoleText("\n\nYou are performing a Elect councillor main action,\npress on a free councillor to select it.");
		pause();
		String chosenCouncillor = swingUI.getChosenCouncillor();
		swingUI.appendConsoleText("\nYou have chosen a " + chosenCouncillor + " Councillor,\nPress on the region where you want to put it ");
		swingUI.enableFreeCouncillorsButtons(false);
		swingUI.enableRegionButtons(true);
		swingUI.enableKingButton(true);
		pause();
		String balcony = swingUI.getChosenRegion();
		swingUI.enableRegionButtons(false);
		swingUI.enableKingButton(false);
		swingUI.appendConsoleText("\nYou have just elected a " + chosenCouncillor + " councillor in " + balcony + "'s balcony");
		sendAction(currentState.createAction(chosenCouncillor, balcony));

	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		swingUI.appendConsoleText("\n\nYou are performing a Engage An Assistant quick action");
		sendAction(currentState.createAction());
	}

	@Override
	public void visit(ChangePermitTilesState currentState) {
		swingUI.showAvailableActions(false, false);
		swingUI.enableRegionButtons(true);
		swingUI.appendConsoleText("\n\nYou are performing a Change Permits Tile quick action,\n please select the region where you what to change tiles.");
		pause();
		String chosenRegion = swingUI.getChosenRegion();
		swingUI.enableRegionButtons(false);
		swingUI.appendConsoleText("\nYou have just changed the " + chosenRegion + "'s Permit Tiles");
		sendAction(currentState.createAction(chosenRegion));
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		try {
			swingUI.clearSwingUI();
			swingUI.showAvailableActions(false, false);
			swingUI.enableRegionButtons(true);
			swingUI.enableFinish(false);
			swingUI.appendConsoleText("\n\nYou are performing a Acquire Business Permit Tile main action,\npress on the region whose council you whant to satisfy");
			List<String> removedCards = new ArrayList<>();
			pause();
			swingUI.enableRegionButtons(false);
			String chosenCouncil = swingUI.getChosenRegion();
			swingUI.appendConsoleText("\nYou have selected the " + chosenCouncil + " council,\npress on the politic cards that you what to use for satisfy this council");
			swingUI.enablePoliticCards(true);
			int numberOfCards = 4;
			boolean finish = false;
			int i = 0;
			while (!finish && i < numberOfCards && i < currentState.getPoliticHandSize()) {
				pause();
				finish = swingUI.hasFinished();
				swingUI.enableFinish(true);
				if(!finish) {
					removedCards.add(swingUI.getChosenCard());
				}
				i++;
			}
			swingUI.appendConsoleText("\nYou have selected these politic cards:\n" + removedCards.toString() + "\nyou can now press on the permit tile that you want acquire.");
			swingUI.enablePoliticCards(false);
			swingUI.enablePermitTilesPanel(chosenCouncil, true);
			pause();
			swingUI.enablePermitTilesPanel(chosenCouncil, false);
			int chosenTile = swingUI.getChosenTile();
			sendAction(currentState.createAction(chosenCouncil, removedCards, chosenTile));
		} catch (InvalidCardException | NumberFormatException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			getState().setExceptionString(e.toString());
		}

	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		swingUI.clearSwingUI();
		swingUI.appendConsoleText("\n\nYou are performing an Assistant To Elect Councillor quick action,\npress on a free councillor to select it.");
		swingUI.showAvailableActions(false, false);
		swingUI.enableFreeCouncillorsButtons(true);
		pause();
		String chosenCouncillor = swingUI.getChosenCouncillor();
		swingUI.appendConsoleText("\nYou have chosen a " + chosenCouncillor + " Councillor,\npress on the region where you want to put it.");
		swingUI.enableFreeCouncillorsButtons(false);
		swingUI.enableRegionButtons(true);
		swingUI.enableKingButton(true);
		pause();
		String chosenBalcony = swingUI.getChosenRegion();
		swingUI.enableRegionButtons(false);
		swingUI.enableKingButton(false);
		swingUI.appendConsoleText("\nYou have just elected a " + chosenCouncillor + "councillor in " + chosenBalcony + "'s balcony");
		sendAction(currentState.createAction(chosenCouncillor, chosenBalcony));
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		swingUI.appendConsoleText("\n\nYou are performing an Additional Main Action quick action");
		sendAction(currentState.createAction());
	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		swingUI.clearSwingUI();
		List<String> removedCards = new ArrayList<>();
		swingUI.showAvailableActions(false, false);
		swingUI.enablePoliticCards(true);
		swingUI.enableFinish(false);
		swingUI.appendConsoleText("\n\nYou are performing a Build Emporium King Main Action,\npress on the politic cards thet you want to use for satisfy the King's council.");
		int numberOfCards = 4;
		boolean finish = false;
		int i = 0;
		while (i < numberOfCards && i < currentState.getPoliticHandSize() && !finish) {
			pause();
			finish = swingUI.hasFinished();
			swingUI.enableFinish(true);
			if(!finish) {
				removedCards.add(swingUI.getChosenCard());
			}
			i++;
		}
		swingUI.appendConsoleText("\nYou have selected these politic cards:\n" + removedCards.toString() + "\nplease press on the city where you want to move the King.");
		swingUI.enablePoliticCards(false);
		swingUI.enableCities(true);
		pause();
		swingUI.enableCities(false);
		String arrivalCity = swingUI.getChosenCity();
		try {
			sendAction(currentState.createAction(removedCards, arrivalCity));
		} catch (InvalidCardException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			getState().setExceptionString(e.toString());
		}	
	}

	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		swingUI.clearSwingUI();
		swingUI.showAvailableActions(false, false);
		swingUI.enablePermitTileDeck(true);
		swingUI.appendConsoleText("\n\nYou are performing a Build Emporium Permit Tile Main Action,\npress on the permit tile that you want use.");
		pause();
		int chosenCard = swingUI.getChosenTile();
		swingUI.enablePermitTileDeck(false);
		swingUI.appendConsoleText("\nYou have chose tile number: " + chosenCard + "\npress on the city want to build.");
		swingUI.enableCities(true);
		pause();
		swingUI.enableCities(false);
		String chosenCity = swingUI.getChosenCity();
		sendAction(currentState.createAction(chosenCity, chosenCard));
	}
	
	private List<String> sellPoliticCard(MarketOfferPhaseState currentState) throws NumberFormatException {
		List<String> chosenPoliticCards = new ArrayList<>();
		if (currentState.canSellPoliticCards()) {
			swingUI.appendConsoleText("\nHow many politic cards do you want to sell? ");
			swingUI.enableMarketInputArea(true);
			pause();
			int numberOfCards = swingUI.getChosenValue();
			swingUI.enableMarketInputArea(false);
			swingUI.enablePoliticCards(true);
			swingUI.appendConsoleText("\nplease press on the cards that you want to sell");
			for (int i = 0; i < numberOfCards && i < currentState.getPoliticHandSize(); i++) {
				pause();
				chosenPoliticCards.add(swingUI.getChosenCard());
			}
		}
		return chosenPoliticCards;
	}
	
	private List<Integer> sellPermissionCard(MarketOfferPhaseState currentState) throws NumberFormatException {
		List<Integer> chosenPermissionCards = new ArrayList<>();
		if (currentState.canSellPermissionCards()) {
			swingUI.appendConsoleText("\nHow many permission cards do you want to use? (numerical input > 0)");
			swingUI.enableMarketInputArea(true);
			pause();
			int numberOfCards = swingUI.getChosenValue();
			swingUI.enableMarketInputArea(false);
			swingUI.enablePermitTileDeck(true);
			swingUI.appendConsoleText("\nplease press on the cards that you whant to sell");
			for (int i = 0; i < numberOfCards && i < currentState.getPermissionHandSize(); i++) {
				pause();
				chosenPermissionCards.add(swingUI.getChosenTile());
			}
		}
		return chosenPermissionCards;
	}
	
	private int sellAssistant(MarketOfferPhaseState currentState) throws NumberFormatException {
		int chosenAssistants = 0;
		if (currentState.canSellAssistants()) {
			swingUI.appendConsoleText("\nSelect the number of assistants " + currentState.getAssistants());
			swingUI.enableMarketInputArea(true);
			pause(); 
			chosenAssistants = swingUI.getChosenValue();
		}
		return chosenAssistants;
	}
	
	@Override
	public void visit(MarketOfferPhaseState currentState) {
		swingUI.refreshDynamicContents(currentState);
		String player = currentState.getPlayerName();
		swingUI.appendConsoleText(ITS_PRINT + player + " market phase turn.");
		if (player.equals(getClientName())) {
			List<String> chosenPoliticCards = sellPoliticCard(currentState);
			List<Integer> chosenPermissionCards = sellPermissionCard(currentState);
			int chosenAssistants = sellAssistant(currentState);
			swingUI.appendConsoleText("\nChoose the price for your offer: ");
			swingUI.enableMarketInputArea(true);
			pause();
			int cost = swingUI.getChosenValue();
			swingUI.enableMarketInputArea(false);
			try {
				getControllerInterface().wakeUpServer(currentState.createMarketObject(chosenPoliticCards,
							chosenPermissionCards, chosenAssistants, cost));
			} catch (InvalidCardException | InvalidNumberOfAssistantException | InvalidCostException | NumberFormatException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
				getState().setExceptionString(e.toString());
			} catch (RemoteException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
			}
		} else {
			setWaiting(true);
			pause();
		}
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {
		String player = currentState.getPlayerName();
		swingUI.appendConsoleText(ITS_PRINT + player + " market phase turn.");
		if(player.equals(getClientName())) {
			try {
				if (currentState.canBuy()) {
					swingUI.appendConsoleText("\nChoose the offert that you want to buy: \n" + currentState.getAvaiableOffers());
					swingUI.enableMarketInputArea(true);
					pause();
					swingUI.enableMarketInputArea(false);
					getControllerInterface().wakeUpServer(currentState.createTransation(swingUI.getChosenValue() - 1));
				} else {
					swingUI.appendConsoleText("You can buy nothing.");
					getControllerInterface().wakeUpServer(currentState.createTransation());
				}
			} catch (RemoteException | NumberFormatException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
			}
		} else {
			setWaiting(true);
			pause();
		}
	}
	
	private void additionalOutput(SuperBonusState currentState) throws InvalidRegionException {
		if (currentState.isBuildingPemitTileBonus()) {
			swingUI.appendConsoleText("\n\n You have encountred a Building Permit Bonus on Nobility Track.\n Press the Region where to pick a permission card.");
			swingUI.enableRegionButtons(true);
			pause();
			swingUI.enableRegionButtons(false);
			String chosenRegion = swingUI.getChosenRegion();
			currentState.analyzeInput(chosenRegion);
		}
	}
	
	private String performSuperBonus(SuperBonusState currentState){
		String selectedItem = new String();
		swingUI.appendConsoleText("\n\n" + currentState.useBonus());
		if(currentState.isRecycleBuildingPermitBonus()) {
			swingUI.enableTotalHandDeck(true);
			pause();
			swingUI.enableTotalHandDeck(false);
			selectedItem = String.valueOf(swingUI.getChosenTile() + 1);
		}
		if(currentState.isRecycleRewardTokenBonus()) {
			swingUI.enableCities(true);
			pause();
			swingUI.enableCities(false);
			selectedItem = swingUI.getChosenCity();
		}
		if(currentState.isBuildingPemitTileBonus()) { 
			swingUI.enablePermitTilesPanel(swingUI.getChosenRegion(), true);
			pause();
			swingUI.enablePermitTilesPanel(swingUI.getChosenRegion(), false);
			selectedItem = String.valueOf(swingUI.getChosenTile());
		}
		return selectedItem;
	}

	@Override
	public void visit(SuperBonusState currentState) {
		swingUI.refreshDynamicContents(currentState);
		while (currentState.hasNext()) {
			int numberOfCurrentBonus = currentState.getCurrentBonusValue();
			try {
				for (int numberOfBonuses = 0; numberOfBonuses < numberOfCurrentBonus; numberOfBonuses++) {		
					currentState.checkKey();
					additionalOutput(currentState);
					String selectedItem = performSuperBonus(currentState);
					currentState.addValue(selectedItem);
				}
			} catch (InvalidCityException | InvalidCardException | InvalidRegionException e) {
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString(), e);
					getState().setExceptionString(e.toString());
			}
		}
		currentState.confirmChange();
		try {
			getControllerInterface().wakeUpServer(currentState.createSuperBonusesGiver());
		} catch (RemoteException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, CANNOT_REACH_SERVER_PRINT, e);
		}
		
	}

	@Override
	public void visit(EndGameState currentState) {
		swingUI.appendConsoleText(currentState.getWinner());
		endGame = true;
	}

	@Override
	public synchronized void run() {
		setWaiting(true);
		pause();
		setWaiting(false);
		do {
			getState().acceptView(this);
		} while(!endGame);
	}

	@Override
	void infoMessage(String message) {
		if(!firstUIRefresh){
			swingUI.appendConsoleText("\n" + message);
		} 
		else {
			output.println(message);
		}
	}
	
}
