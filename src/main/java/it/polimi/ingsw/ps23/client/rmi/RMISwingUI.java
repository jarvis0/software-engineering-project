package it.polimi.ingsw.ps23.client.rmi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import it.polimi.ingsw.ps23.client.SwingUI;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrackStep;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.PermissionCard;
import it.polimi.ingsw.ps23.server.model.player.HandDeck;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;

class RMISwingUI extends SwingUI {

	private String chosenCard;
	private int chosenTile;
	private List<JLabel> cardsList;
	private boolean finish;
	
	RMISwingUI(String mapType, String playerName) {
		super(mapType, playerName);
		cardsList = new ArrayList<>();
		finish = false;
	}
	
	String getChosenActionUI() {
		return getChosenAction();
	}

	private void refreshPlayersTable(List<Player> playersList) {
		for (int i = getTableModel().getRowCount() - 1; i >= 0; i--) {
			getTableModel().removeRow(i);
		}
		for (Player player : playersList) {
			Vector<Object> vector = new Vector<>();
			vector.add(0, player.getName());
			vector.add(1, player.getVictoryPoints());
			vector.add(2, player.getCoins());
			vector.add(3, player.getAssistants());
			vector.add(4, player.getNobilityTrackPoints());
			getTableModel().addRow(vector);
		}
	}

	private void refreshCouncils(List<Region> regions, Council kingCouncil) {
		for (Region region : regions) {
			Point point = getCouncilPoint(region.getName());
			int x = point.x;
			int y = point.y;
			drawCouncil(((GroupRegionalCity) region).getCouncil().getCouncil(), x, y);
		}
		Point point = getCouncilPoint("kingdom");
		drawCouncil(kingCouncil.getCouncil(), point.x, point.y);
	}

	private void drawCouncil(Queue<Councillor> council, int xCoord, int yCoord) {
		int x = xCoord;
		int y = yCoord;
		for (Councillor councillor : council) {
			x -= 16;
			drawCouncillor(councillor.getColor().toString(), x, y);
		}
	}

	private void drawCouncillor(String color, int x, int y) {
		BufferedImage councillorImage = readImage(SwingUI.getImagesPath()+ color + SwingUI.getCouncillorPath());
		Image resizedCouncillorImage = councillorImage.getScaledInstance(14, 39, Image.SCALE_SMOOTH);
		JLabel councillorLabel = new JLabel(new ImageIcon(resizedCouncillorImage));
		councillorLabel.setBounds(0, 0, 15, 39);
		councillorLabel.setLocation(x, y);
		getMapPanel().add(councillorLabel, 0);
	}

	private void refreshPermitTiles(List<Region> regions) {
		for (Region region : regions) {
			Point point = getCouncilPoint(region.getName());
			int x = point.x - 120;
			int y = point.y - 12;
			drawPermissionCards(((GroupRegionalCity) region).getPermissionDeckUp(), x, y);
		}
	}

	private void drawPermissionCards(Deck permissionDeckUp, int xCoord, int yCoord) {
		List<Card> permissionCards = permissionDeckUp.getCards();
		int x = xCoord;
		int y = yCoord;
		int indexOfTile = 1;
		final int firstTile = 0;
		final int seconTile = 1;
		for (Card permissionCard : permissionCards) {
			BufferedImage permissionTileImage = readImage(SwingUI.getImagesPath() + SwingUI.getPermissionCardPath());
			Image resizedPermissionTile = permissionTileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			JLabel permissionTileLabel = new JLabel(new ImageIcon(resizedPermissionTile));
			permissionTileLabel.setBounds(0, 0, 50, 50);
			permissionTileLabel.setLocation(x, y);
			if(indexOfTile == 1){
			permissionTileLabel.addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
					chosenTile = firstTile;
	            	getRmiGUIView().resume();
                }
				}); 
			}
			if(indexOfTile == 2){
				permissionTileLabel.addMouseListener(new MouseAdapter() {
					@Override
	                public void mouseClicked(MouseEvent e) {
						chosenTile = seconTile;
		            	getRmiGUIView().resume();
	                }
					}); 
				}
			getMapPanel().add(permissionTileLabel, 0);
			List<Bonus> bonuses = ((PermissionCard) permissionCard).getBonuses();
			int bonusCoordX = x - 47;
			int bonusCoordY = y + 40;
			for (Bonus bonus : bonuses) {
				//drawBonus(bonus, bonusCoordX + 50, bonusCoordY - 20, 23, 25, 0);TODO
				bonusCoordX = bonusCoordX + 24;
			}
			List<City> cities = ((PermissionCard) permissionCard).getCities();
			int cityCoordX = x + 5;
			int cityCoordY = y;
			for (int i = 0; i < cities.size(); i++) {
				City city = cities.get(i);
				JLabel cityInitial = new JLabel();
				cityInitial.setBounds(0, 0, 23, 25);
				cityInitial.setLocation(cityCoordX, cityCoordY);
				cityInitial.setFont(new Font(SwingUI.getSansSerifFont(), Font.BOLD, 12));
				cityInitial.setForeground(Color.black);
				String slash = new String();
				if (i != cities.size() - 1) {
					slash = " / ";
				}
				cityInitial.setText(Character.toString(city.getName().charAt(0)) + slash);
				getMapPanel().add(cityInitial, 0);
				cityCoordX += 17;
			}
			x -= 52;
			indexOfTile++;
		}
	}

	private void refreshFreeCouncillors(List<Councillor> freeCouncillors) {
		Point freeCouncillorsPoint = getCouncilPoints().get("free");
		int x = freeCouncillorsPoint.x;
		int y = freeCouncillorsPoint.y;
		Map<String, Integer> freeCouncillorsMap = new HashMap<>();
		for (Councillor freeCouncillor : freeCouncillors) {
			String councillorColor = freeCouncillor.getColor().toString();
			if (freeCouncillorsMap.containsKey(councillorColor)) {
				freeCouncillorsMap.put(councillorColor, freeCouncillorsMap.get(councillorColor) + 1);
			} else {
				freeCouncillorsMap.put(councillorColor, 1);
			}
		}
		for (Entry<String, Integer> entry : freeCouncillorsMap.entrySet()) {
			String color = entry.getKey();
			BufferedImage councillorImage = readImage(SwingUI.getImagesPath() + color + SwingUI.getCouncillorPath());
			Image resizedCouncillorImage = councillorImage.getScaledInstance(18, 39, Image.SCALE_SMOOTH);
			JLabel councillorLabel = new JLabel(new ImageIcon(resizedCouncillorImage));
			councillorLabel.setBounds(0, 0, 28, 52);
			councillorLabel.setLocation(x, y);
			getMapPanel().add(councillorLabel, 0);
			JLabel councillorsValue = new JLabel();
			councillorsValue.setBounds(0, 0, 23, 25);
			councillorsValue.setLocation(x + 11, y + 16);
			councillorsValue.setFont(new Font(SwingUI.getSansSerifFont(), Font.BOLD, 12));
			if ("black".equals(color) || "purple".equals(color) || "blue".equals(color)) {
				councillorsValue.setForeground(Color.white);
			} else {
				councillorsValue.setForeground(Color.black);
			}
			councillorsValue.setText(String.valueOf(entry.getValue()));
			getMapPanel().add(councillorsValue, 0);
			y += 41;
		}

	}

	private void refreshPoliticCards(HandDeck politicHandDeck) {
		int x = 0;
		int y = 535;
		for (Card card : ((PoliticHandDeck) politicHandDeck).getCards()) {
			BufferedImage cardImage = readImage(SwingUI.getImagesPath() + card.toString() + SwingUI.getPoliticCardPath());
			Image resizedCardImage = cardImage.getScaledInstance(42, 66, Image.SCALE_SMOOTH);
			JLabel cardLabel = new JLabel(new ImageIcon(resizedCardImage));
			cardsList.add(cardLabel);
			cardLabel.addMouseListener(new MouseAdapter() {
					@Override
	                public void mouseClicked(MouseEvent e) {
						chosenCard = card.toString();
		            	getRmiGUIView().resume();
	                }
	        });      
			cardLabel.setBounds(0, 0, 42, 66);
			cardLabel.setLocation(x, y);
			x += 44;
			getMapPanel().add(cardLabel, 0);
			cardLabel.setEnabled(false);
		}
		JButton finished = new JButton("finished");
		finished.addActionListener(new ActionListener() {
			@Override 
            public void actionPerformed(ActionEvent e)
            {
				finish = true;
				resumeRMIGUIView();
            }
        });
		finished.setBounds(x, y, 70, 30);
		getMapPanel().add(finished, 0);
	}

	private int searchPlayer(List<Player> playersList) {
		for (Player player : playersList) {
			if (getPlayerName().equals(player.getName())) {
				return playersList.indexOf(player);
			}
		}
		return -1;
	}

	private void refreshBonusTiles(List<Region> groupRegionalCity, List<Region> groupColoredCity, Bonus currentKingTile) {
		List<Region> allRegions = new ArrayList<>();
		allRegions.addAll(groupRegionalCity);
		allRegions.addAll(groupColoredCity);
		for (Region region : allRegions) {
			if (!(region.alreadyUsedBonusTile())) {
				drawBonusTile(region.getName(), region.getBonusTile());
			}
		}
		if (currentKingTile != null) {
			drawBonusTile("kingdom", currentKingTile);
		}
	}

	void refreshUI(StartTurnState currentState) {
		refreshKingPosition(currentState.getKingPosition());
		/*refreshPlayersTable(currentState.getPlayersList());//TODO
		refreshCouncils(currentState.getGroupRegionalCity(), currentState.getKingCouncil());
		refreshFreeCouncillors(currentState.getFreeCouncillors());
		refreshPermitTiles(currentState.getGroupRegionalCity());
		refreshBonusTiles(currentState.getGroupRegionalCity(), currentState.getGroupColoredCity(), currentState.getCurrentKingTile());
		int playerIndex = searchPlayer(currentState.getPlayersList());
		refreshPoliticCards((currentState.getPlayersList().get(playerIndex)).getPoliticHandDeck());*/
		getFrame().repaint();
	}

	private void bonusesToStrings(List<Bonus> bonuses, List<String> bonusesName, List<String> bonusesValue) {
		for(int i = 0; i < bonuses.size(); i++)  {
			bonusesName.add(bonuses.get(i).getName());
			bonusesValue.add(String.valueOf(bonuses.get(i).getValue()));
		}
	}
	
	public void loadStaticContents(StartTurnState currentState) {
		Map<String, City> cityMap = currentState.getGameMap().getCities();
		Set<Entry<String, City>> citiesEntries = cityMap.entrySet();
		List<String> citiesName = new ArrayList<>();
		List<List<String>> citiesBonusesName = new ArrayList<>();
		List<List<String>> citiesBonusesValue = new ArrayList<>();
		for(Entry<String, City> cityEntry : citiesEntries) {
			City city = cityEntry.getValue();
			if(!city.isCapital()) {
				citiesName.add(city.getName());
				List<Bonus> bonuses = ((NormalCity) city).getRewardToken().getBonuses();
				List<String> bonusesName = new ArrayList<>();
				List<String> bonusesValue = new ArrayList<>();
				bonusesToStrings(bonuses, bonusesName, bonusesValue);
				citiesBonusesName.add(bonusesName);
				citiesBonusesValue.add(bonusesValue);
			}
		}
		addRewardTokens(citiesName, citiesBonusesName, citiesBonusesValue);
		List<NobilityTrackStep> steps = currentState.getNobilityTrack().getSteps();
		List<List<String>> stepsBonusesName = new ArrayList<>();
		List<List<String>> stepsBonusesValue = new ArrayList<>();
		for(NobilityTrackStep step : steps) {
			List<Bonus> bonuses = step.getBonuses();		
			List<String> bonusesName = new ArrayList<>();
			List<String> bonusesValue = new ArrayList<>();
			bonusesToStrings(bonuses, bonusesName, bonusesValue);
			stepsBonusesName.add(bonusesName);
			stepsBonusesValue.add(bonusesValue);
		}
		addNobilityTrackBonuses(stepsBonusesName, stepsBonusesValue);
	}
	
	
	public static void main(String[] args) {
		new RMISwingUI("hard", "ale");//TODO remove this method
	}
	
	public void showAvailableActions(boolean isAvailableMainAction, boolean isAvailableQuickAction, RMIGUIView rmiguiView) {
		setRmiguiView(rmiguiView);
		if(isAvailableMainAction && isAvailableQuickAction) {
			enableCards();
		}
		getMainActionPanel().setVisible(isAvailableMainAction);
		getQuickActionPanel().setVisible(isAvailableQuickAction);
	}

	public String getChosenCard() {
		return chosenCard;
	}
	
	public int getChosenTile() {
		return chosenTile;
	}

	private void enableCards() {
		for (JLabel jLabel : cardsList) {
			jLabel.setEnabled(true);
		}
	}
	public void enableButtons() {
		enableRegionButtons();
	}

	public boolean hasFinished() {
		return finish;
	}
	
}
