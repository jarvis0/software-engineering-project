package it.polimi.ingsw.ps23.client.rmi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.CoinBonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
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

public class RMISwingUI extends SwingUI {

	private Map<String, Point> councilPoints;
	private JFrame frame;
	private JPanel mapPanel;
	private DefaultTableModel tableModel;
	private String playerName;

	RMISwingUI(String mapType, String playerName) {//TODO refactor this class
		super(mapType, playerName);
		councilPoints = getCouncilPoints();
		frame = getFrame();
		mapPanel = getMapPanel();
		tableModel = getTableModel();
		this.playerName = getPlayerName();
	}
	
	public String getChosenActionUI() {
		return getChosenAction();
	}

	private void drawBonus(Bonus bonus, int x, int y, int width, int height, int yOffset) {
		BufferedImage bonusImage = readImage(SwingUI.getImagesPath()+ bonus.getName() + SwingUI.getPngExtension());
		Image resizedBonusImage = bonusImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		JLabel bonusLabel = new JLabel(new ImageIcon(resizedBonusImage));
		bonusLabel.setBounds(0, 0, width, height);
		bonusLabel.setLocation(x, y + yOffset);
		mapPanel.add(bonusLabel, 0);
		int bonusNumber = bonus.getValue();
		if (bonusNumber > 1 || "victoryPoint".equals(bonus.getName())) {
			JLabel bonusValue = new JLabel();
			bonusValue.setBounds(0, 0, width, height);
			bonusValue.setLocation(x + 8, y + yOffset);
			bonusValue.setFont(new Font(SwingUI.getSansSerifFont(), Font.BOLD, 9));
			if (bonus instanceof CoinBonus) {
				bonusValue.setForeground(Color.black);
			} else {
				bonusValue.setForeground(Color.white);
			}
			bonusValue.setText(String.valueOf(bonusNumber));
			mapPanel.add(bonusValue, 0);
		}
	}

	private void addRewardTokens(Map<String, City> cities) {
		Set<Entry<String, City>> cityEntries = cities.entrySet();
		for (Entry<String, City> cityEntry : cityEntries) {
			Component cityComponent = getComponents(cityEntry.getKey());
			Point point = cityComponent.getLocationOnScreen();
			int x = point.x;
			int y = point.y;
			City city = cityEntry.getValue();
			if (!city.isCapital()) {
				List<Bonus> rewardTokenBonuses = ((NormalCity) city).getRewardToken().getBonuses();
				for (Bonus bonus : rewardTokenBonuses) {
					drawBonus(bonus, x + 50, y - 20, 23, 25, 0);
					x += 22;
				}
			}
		}
	}

	private void addNobilityTrackBonuses(NobilityTrack nobilityTrack) {
		int stepNumber = 0;
		for (NobilityTrackStep step : nobilityTrack.getSteps()) {
			int yOffset = 0;
			int x = (int) 38.1 * stepNumber + 8;
			int y = 495;
			for (Bonus bonus : step.getBonuses()) {
				if (!("nullBonus").equals(bonus.getName())) {
					int width = 23;
					int height = 25;
					if (step.getBonuses().size() == 1) {
						y = 490;
					}
					if (("recycleRewardToken").equals(bonus.getName())) {
						y = 476;
						height = 40;
					}
					drawBonus(bonus, x, y, width, height, yOffset);
					yOffset -= 25;
				}
			}
			stepNumber++;
		}

	}

	private void refreshPlayersTable(List<Player> playersList) {
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		for (Player player : playersList) {
			Vector<Object> vector = new Vector<>();
			vector.add(0, player.getName());
			vector.add(1, player.getVictoryPoints());
			vector.add(2, player.getCoins());
			vector.add(3, player.getAssistants());
			vector.add(4, player.getNobilityTrackPoints());
			tableModel.addRow(vector);
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
		mapPanel.add(councillorLabel, 0);
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
		for (Card permissionCard : permissionCards) {
			BufferedImage permissionTileImage = readImage(SwingUI.getImagesPath() + SwingUI.getPermissionCardPath());
			Image resizedPermissionTile = permissionTileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			JLabel permissionTileLabel = new JLabel(new ImageIcon(resizedPermissionTile));
			permissionTileLabel.setBounds(0, 0, 50, 50);
			permissionTileLabel.setLocation(x, y);
			mapPanel.add(permissionTileLabel, 0);
			List<Bonus> bonuses = ((PermissionCard) permissionCard).getBonuses();
			int bonusCoordX = x - 47;
			int bonusCoordY = y + 40;
			for (Bonus bonus : bonuses) {
				drawBonus(bonus, bonusCoordX + 50, bonusCoordY - 20, 23, 25, 0);
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
				mapPanel.add(cityInitial, 0);
				cityCoordX += 17;
			}
			x -= 52;
		}
	}

	private void refreshFreeCouncillors(List<Councillor> freeCouncillors) {
		Point freeCouncillorsPoint = councilPoints.get("free");
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
		for (Map.Entry<String, Integer> entry : freeCouncillorsMap.entrySet()) {
			String color = entry.getKey();
			BufferedImage councillorImage = readImage(SwingUI.getImagesPath() + color + SwingUI.getCouncillorPath());
			Image resizedCouncillorImage = councillorImage.getScaledInstance(18, 39, Image.SCALE_SMOOTH);
			JLabel councillorLabel = new JLabel(new ImageIcon(resizedCouncillorImage));
			councillorLabel.setBounds(0, 0, 28, 52);
			councillorLabel.setLocation(x, y);
			mapPanel.add(councillorLabel, 0);
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
			mapPanel.add(councillorsValue, 0);
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
			cardLabel.setBounds(0, 0, 42, 66);
			cardLabel.setLocation(x, y);
			x += 44;
			mapPanel.add(cardLabel, 0);
		}

	}

	private int searchPlayer(List<Player> playersList) {
		for (Player player : playersList) {
			if (playerName.equals(player.getName())) {
				return playersList.indexOf(player);
			}
		}
		return -1;
	}

	private void refreshBonusTiles(List<Region> groupRegionalCity, List<Region> groupColoredCity,
			Bonus currentKingTile) {
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

	private void drawBonusTile(String name, Bonus bonusTile) {
		Point regionPoint = getCouncilPoint(name);
		int x = regionPoint.x;
		int y = regionPoint.y;
		if (("seaside").equals(name) || ("hill").equals(name) || ("mountain").equals(name)) {
			x += 7;
			y -= 8;
		}
		if (("kingdom").equals(name)) {
			x -= 63;
			y -= 40;
		}
		BufferedImage tileImage = readImage(SwingUI.getImagesPath() + name + SwingUI.getBonusTilePath());
		Image resizedTileImage = tileImage.getScaledInstance(50, 35, Image.SCALE_SMOOTH);
		JLabel tileLabel = new JLabel(new ImageIcon(resizedTileImage));
		tileLabel.setBounds(0, 0, 50, 35);
		tileLabel.setLocation(x, y);
		mapPanel.add(tileLabel, 0);
		drawBonus(bonusTile, x + 25, y + 10, 23, 25, -5);
	}

	void refreshUI(StartTurnState currentState) {
		refreshKingPosition(currentState.getKingPosition());
		refreshPlayersTable(currentState.getPlayersList());
		refreshCouncils(currentState.getGroupRegionalCity(), currentState.getKingCouncil());
		refreshFreeCouncillors(currentState.getFreeCouncillors());
		refreshPermitTiles(currentState.getGroupRegionalCity());
		refreshBonusTiles(currentState.getGroupRegionalCity(), currentState.getGroupColoredCity(), currentState.getCurrentKingTile());
		int playerIndex = searchPlayer(currentState.getPlayersList());
		refreshPoliticCards((currentState.getPlayersList().get(playerIndex)).getPoliticHandDeck());
		frame.repaint();
	}

	public void loadStaticContents(StartTurnState currentState) {
		addRewardTokens(currentState.getGameMap().getCities());
		addNobilityTrackBonuses(currentState.getNobilityTrack());
	}
	
	public static void main(String[] args) {
		new RMISwingUI("hard", "ale");//TODO remove this method
	}
	
	public void showAvailableActions(Boolean isAvailableMainAction, Boolean isAvailableQuickAction, RMIGUIView rmiguiView) {
		setRmiguiView(rmiguiView);
		getMainActionPanel().setVisible(isAvailableMainAction);
		getQuickActionPanel().setVisible(isAvailableQuickAction);
	}



	
}
