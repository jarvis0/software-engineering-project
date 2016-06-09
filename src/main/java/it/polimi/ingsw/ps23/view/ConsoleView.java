package it.polimi.ingsw.ps23.view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.state.AcquireBusinessPermitTileState;
import it.polimi.ingsw.ps23.model.state.AdditionalMainActionState;
import it.polimi.ingsw.ps23.model.state.AssistantToElectCouncillorState;
import it.polimi.ingsw.ps23.model.state.BuildEmporiumKingState;
import it.polimi.ingsw.ps23.model.state.BuildEmporiumPermitTileState;
import it.polimi.ingsw.ps23.model.state.ChangePermitsTileState;
import it.polimi.ingsw.ps23.model.state.ElectCouncillorState;
import it.polimi.ingsw.ps23.model.state.EngageAnAssistantState;
import it.polimi.ingsw.ps23.model.state.GameStatusState;
import it.polimi.ingsw.ps23.model.state.MarketBuyPhaseState;
import it.polimi.ingsw.ps23.model.state.MarketOfferPhaseState;
import it.polimi.ingsw.ps23.model.state.StartTurnState;
import it.polimi.ingsw.ps23.model.state.State;
import it.polimi.ingsw.ps23.model.state.StateCache;
import it.polimi.ingsw.ps23.server.Connection;

public class ConsoleView extends View implements ViewVisitor {
	
	private static final String NO_INPUT = "NOINPUTNEEDED";
	
	private Connection connection;

	private String clientName;

	private List<ConsoleView> consoleViews;

	private State state;
	
	private PrintStream output;
	
	public ConsoleView(String clientName, Connection connection, PrintStream output) {
		this.connection = connection;
		this.output = output;
		this.clientName = clientName;
	}
	
	public void setOtherViews(List<ConsoleView> consoleViews) {
		this.consoleViews = consoleViews;
	}
	
	public void update(State state) {
		this.state = state;
	}

	@Override
	public synchronized void run() {
		while(true) {
			state.acceptView(this);
		}
	}
	
	private void sendNoInput(String message) {
		connection.send(NO_INPUT + message);
	}
	
	private void sendWithInput(String message) {
		connection.send(message);
	}
	
	private String receive() {
		return connection.receive();
	}
	
	private synchronized void pause() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void threadWakeUp() {
		notifyAll();
	}
	
	private synchronized void resume() {
		connection.getServer().resumeView(consoleViews, this);
	}
	
	@Override
	public void visit(GameStatusState currentState) {
		sendNoInput(currentState.getStatus());
		wakeUp();
	}

	@Override
	public void visit(StartTurnState currentState) {
		Player player = currentState.getCurrentPlayer();
		output.println("Current player: " + player.getName());
		if(player.getName().equals(clientName)) {
			sendWithInput("Current player: " + player.toString() + player.showSecretStatus() + "\nChoose an action to perform? " + currentState.getAvaiableAction());
			try {
				wakeUp(StateCache.getAction(receive().toLowerCase()));
			}
			catch(NullPointerException e) {
				wakeUp();
			}
		}
		else {
			sendNoInput("It's player " + player.getName() + " turn.");
			pause();
			wakeUp();
		}
	}

	@Override
	public void visit(ElectCouncillorState currentState) {
		sendWithInput("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = receive().toLowerCase();
		sendWithInput("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = receive().toLowerCase();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));
		resume();
	}

	@Override
	public void visit(AcquireBusinessPermitTileState currentState) {
		List<String> removedCards = new ArrayList<>();
		sendWithInput("Choose a council to satisfy: " + currentState.getCouncilsMap());
		String chosenCouncil = receive().toLowerCase();
		sendWithInput("How many cards to you want to use ( min 1 - max " + currentState.getAvailablePoliticCardsNumber(chosenCouncil) + " )");
		int numberOfCards = Integer.parseInt(receive());
		boolean finished = false;
		for(int i = 0; i < numberOfCards && !finished; i++) {
			sendWithInput("Choose a politic card you want to use from this list: " + currentState.getPoliticHandDeck());
			String chosenCard = receive().toLowerCase();
			removedCards.add(chosenCard);
			//aggiungere un metodo per rimuovere le carte già scelte
		}
		sendWithInput("Choose a permission card (press 1 or 2): " + currentState.getAvailablePermitTile(chosenCouncil));
		int chosenCard = Integer.parseInt(receive()) - 1;
		wakeUp(currentState.createAction(chosenCouncil, removedCards, chosenCard));
		resume();
		
	}

	@Override
	public void visit(AssistantToElectCouncillorState currentState) {
		sendWithInput("Choose a free councillor from this list: " + currentState.getFreeCouncillors());
		String chosenCouncillor = receive().toLowerCase();
		sendWithInput("Choose a balcony where to put the councillor: " + currentState.getCouncilsMap());
		String chosenBalcony = receive().toLowerCase();
		wakeUp(currentState.createAction(chosenCouncillor, chosenBalcony));		
	}

	@Override
	public void visit(AdditionalMainActionState currentState) {
		wakeUp(currentState.createAction());
	}

	@Override
	public void visit(EngageAnAssistantState currentState) {
		wakeUp(currentState.createAction());
	}

	@Override
	public void visit(ChangePermitsTileState currentState) {
		sendWithInput("Choose a region:" + currentState.getPermitsMap());
		String chosenRegion = receive();
		wakeUp(currentState.createAction(chosenRegion));

	}

	@Override
	public void visit(BuildEmporiumKingState currentState) {
		List<String> removedCards = new ArrayList<>();
		sendWithInput("Choose the number of cards you want for satisfy the King Council: "+ currentState.getAvailableCardsNumber());
		int numberOfCards = Integer.parseInt(receive());
		sendWithInput("player hand deck:" + currentState.getDeck());
		for (int i=0; i<numberOfCards; i++) {
			sendWithInput("Choose a politic card you want to use from this list: " + currentState.getAvailableCards());
			String chosenCard = receive().toLowerCase();
			removedCards.add(chosenCard);
		}
		sendWithInput("please insert the route for the king.[king's initial position: " + currentState.getKingPosition()+"] insert the arrival city: ");
		String arrivalCity = receive().toUpperCase();
		wakeUp(currentState.createAction(removedCards, arrivalCity));
	}
	
	@Override
	public void visit(BuildEmporiumPermitTileState currentState) {
		sendWithInput("Choose the permit tile that you want to use for build an Emporium: (numerical input) " + currentState.getAvaibleCards());
		int chosenCard = Integer.parseInt(receive()) - 1;
		sendWithInput("Choose the city where you what to build an emporium: " + currentState.getChosenCard(chosenCard));
		String chosenCity = receive().toUpperCase();
		wakeUp(currentState.createAction(chosenCity, chosenCard));
	}

	@Override
	public void visit(MarketOfferPhaseState currentState) {
		List<String> chosenPoliticCards = new ArrayList<>();
		List<Integer> chosenPermissionCards = new ArrayList<>();
		sendNoInput("It's " + currentState.getCurrentPlayer() + " market phase turn.");
		if(currentState.canSellPoliticCards()) {
			sendWithInput("How many politic cards do you want to use? ");
			int numberOfCards = Integer.parseInt(receive());
			for(int i = 0; i < numberOfCards; i++) {
				sendWithInput("Select a card from this list: " + currentState.getPoliticHandDeck());
				chosenPoliticCards.add(receive());
			}
		}
		if(currentState.canSellPoliticCards()) {
			sendWithInput("How many permission cards do you want to use? ");
			int numberOfCards = Integer.parseInt(receive());
			for(int i = 0; i < numberOfCards; i++) {
				sendWithInput("Select a card from this list: " + currentState.getPermissionHandDeck());
				chosenPermissionCards.add(Integer.parseInt(receive()));
			}
		}
		int chosenAssistants = 0;
		if(currentState.canSellAssistants()) {
			sendWithInput("Select the number of assistants " + currentState.getAssistants());
			chosenAssistants = Integer.parseInt(receive());
		}
		sendWithInput("Choose the price for your offer: ");
		int cost = Integer.parseInt(receive());
		wakeUp(currentState.createMarketObject(chosenPoliticCards, chosenPermissionCards, chosenAssistants, cost));
	}

	@Override
	public void visit(MarketBuyPhaseState currentState) {
		sendNoInput("Market turn, current Player: " + currentState.getCurrentPlayer());
		if(currentState.canBuy()) {
			sendWithInput("Avaible offers: " + currentState.getAvaiableOffers());
			wakeUp(currentState.createTransation(Integer.parseInt(receive())));
		}
		else {
			sendNoInput("You can buy nothing");
			wakeUp(currentState.createTransation());
		}
	}
	
}
