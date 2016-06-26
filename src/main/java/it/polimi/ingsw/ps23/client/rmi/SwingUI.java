package it.polimi.ingsw.ps23.client.rmi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.CoinBonus;
import it.polimi.ingsw.ps23.server.model.initialization.RawObject;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.PermissionCard;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;

class SwingUI {

	private static final String CONFIGURATION_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/";
	private static final String IMAGES_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/images/";
	private static final String ICON_PATH = "victoryPoint.png";
	private static final String BACKGROUND_PATH = "mapBackground.png";
	private static final String KING_PATH = "king.png";
	private static final String CITIES_POSITION_CSV = "citiesPosition.csv";
	private static final String CITIES_CONNECTION_CSV = "citiesConnection.csv";
	private static final String COUNCILS_POSITION_CSV = "councilsPosition.csv";

	private String mapPath;
	private Map<String, Component> components;
	private Map<String, Point> councilPoints;
	private JFrame frame;
	private JPanel mapPanel;
	private JTable playersTable;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;

	SwingUI(String mapType) {
		mapPath = CONFIGURATION_PATH + mapType + "/";
		components = new HashMap<>();
		councilPoints = new HashMap<>();
		frame = new JFrame();
		frame.setTitle("Council of Four");
		Dimension dimension = new Dimension(814, 503);
		frame.setMinimumSize(dimension);
		frame.setIconImage(readImage(IMAGES_PATH + ICON_PATH));
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(new GridLayout());
		mapPanel = new JPanel();
		mapPanel.setLayout(null);
		loadCouncilsPositions();
		loadKing();
		loadCities();
		loadStreets();
		loadMapBackground();
		loadPlayersTable();
		frame.getContentPane().add(mapPanel);
		mapPanel.add(scrollPane);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private BufferedImage readImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot find the image file.", e);
		}
		return null;
	}

	Component getComponents(String componentName) {
		return components.get(componentName);
	}

	Point getCouncilPoint(String region) {
		return councilPoints.get(region);
	}

	private void loadKing() {
		BufferedImage kingImage = readImage(IMAGES_PATH + KING_PATH);
		Image resizedKingImage = kingImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		JLabel kingLabel = new JLabel(new ImageIcon(resizedKingImage));
		kingLabel.setBounds(0, 0, 35, 35);
		mapPanel.add(kingLabel);
		components.put("king", kingLabel);
	}

	private void loadCouncilsPositions() {
		List<String[]> rawCouncilsPosition = new RawObject(mapPath + COUNCILS_POSITION_CSV).getRawObject();
		for (String[] rawCouncilPosition : rawCouncilsPosition) {
			Point councilPoint = new Point();
			councilPoint.x = Integer.parseInt(rawCouncilPosition[1]);
			councilPoint.y = Integer.parseInt(rawCouncilPosition[2]);
			councilPoints.put(rawCouncilPosition[0], councilPoint);
		}
	}

	private void loadCities() {
		List<String[]> rawCitiesPosition = new RawObject(mapPath + CITIES_POSITION_CSV).getRawObject();
		for (String[] rawCityPosition : rawCitiesPosition) {
			BufferedImage cityImage = readImage(IMAGES_PATH + rawCityPosition[5] + ".png");
			int x = Integer.parseInt(rawCityPosition[3]);
			int y = Integer.parseInt(rawCityPosition[4]);
			int width = Integer.parseInt(rawCityPosition[1]);
			int height = Integer.parseInt(rawCityPosition[2]);
			Image resizedCityImage = cityImage.getScaledInstance(width - 8, height - 8, Image.SCALE_SMOOTH);
			JLabel cityLabel = new JLabel(new ImageIcon(resizedCityImage));
			cityLabel.setBounds(0, 0, width - 8, height - 8);
			cityLabel.setLocation(x, y);
			JLabel cityName = new JLabel();
			cityName.setBounds(0, 0, width, height);
			cityName.setLocation(x - 18, y - 38);
			cityName.setFont(new Font("Algerian", Font.ROMAN_BASELINE, 14));
			cityName.setForeground(Color.decode(rawCityPosition[6]));
			cityName.setText(rawCityPosition[0]);
			mapPanel.add(cityName);
			mapPanel.add(cityLabel);
			components.put(rawCityPosition[0], cityLabel);
		}
	}

	private void loadStreets() {
		List<String[]> rawCitiesConnection = new RawObject(mapPath + CITIES_CONNECTION_CSV).getRawObject();
		for (String[] rawCityConnection : rawCitiesConnection) {
			BufferedImage connectionImage = readImage(IMAGES_PATH + rawCityConnection[0] + ".png");
			int x = Integer.parseInt(rawCityConnection[3]);
			int y = Integer.parseInt(rawCityConnection[4]);
			int width = Integer.parseInt(rawCityConnection[1]);
			int height = Integer.parseInt(rawCityConnection[2]);
			Image resizedConnectionImage = connectionImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			JLabel connectionLabel = new JLabel(new ImageIcon(resizedConnectionImage));
			connectionLabel.setBounds(0, 0, width, height);
			connectionLabel.setLocation(x, y);
			mapPanel.add(connectionLabel);
		}
	}

	private void loadMapBackground() {
		BufferedImage mapImage = readImage(IMAGES_PATH + BACKGROUND_PATH);
		Image resizedMapImage = mapImage.getScaledInstance(800, 464, Image.SCALE_SMOOTH);
		JLabel mapLabel = new JLabel(new ImageIcon(resizedMapImage));
		mapLabel.setBounds(0, 0, 800, 464);
		mapPanel.add(mapLabel);
	}

	private void loadPlayersTable() {
		int numRows = 0;
		String[] columnNames = new String[] { "Name", "Victory Points", "Coins", "Assistants", "Nobility Points" };
		tableModel = new DefaultTableModel(numRows, columnNames.length);
		tableModel.setColumnIdentifiers(columnNames);
		playersTable = new JTable(tableModel);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(playersTable);
		scrollPane.setBounds(0, 0, 567, 70);
		scrollPane.setLocation(800, 0);
	}

	private void drawBonus(Bonus bonus, int x, int y) {
		BufferedImage bonusImage = readImage(IMAGES_PATH + bonus.getName() + ".png");
		Image resizedBonusImage = bonusImage.getScaledInstance(23, 25, Image.SCALE_SMOOTH);
		JLabel bonusLabel = new JLabel(new ImageIcon(resizedBonusImage));
		bonusLabel.setBounds(0, 0, 23, 25);
		bonusLabel.setLocation(x + 50, y - 20);
		mapPanel.add(bonusLabel, 0);
		int bonusNumber = bonus.getValue();
		if(bonusNumber > 1 || "victoryPoint".equals(bonus.getName())) {
			JLabel bonusValue = new JLabel();
			bonusValue.setBounds(0, 0, 23, 25);
			bonusValue.setLocation(x + 50 + 8, y - 20);
			bonusValue.setFont(new Font("Sans serif", Font.BOLD, 9));
			if(bonus instanceof CoinBonus) {
				bonusValue.setForeground(Color.decode("0x000000"));
			} else {
				bonusValue.setForeground(Color.decode("0xFFFFFF"));
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
			if(!city.isCapital()) {
				List<Bonus> rewardTokenBonuses = ((NormalCity) city).getRewardToken().getBonuses();
				for(Bonus bonus : rewardTokenBonuses) {
					drawBonus(bonus, x, y);
					x += 22;
				}
			}
		}
	}

	private void refreshKingPosition(String city) {
		Point point = getComponents(city).getLocationOnScreen();
		getComponents("king").setLocation(point);
	}

	private void refreshPlayersTable(PlayersSet playerSet) {
		for(int i = tableModel.getRowCount(); i > 0; i--) {
			tableModel.removeRow(i);
		}
		for(Player player : playerSet.getPlayers()) {
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
		for(Region region : regions) {
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
		for(Councillor councillor : council) {
			x -= 16;
			drawCouncillor(councillor.getColor().toString(), x, y);
		}
	}

	private void drawCouncillor(String color, int x, int y) {
		BufferedImage councillorImage = readImage(IMAGES_PATH + color + "Councillor.png");
		Image resizedCouncillorImage = councillorImage.getScaledInstance(14, 39, Image.SCALE_SMOOTH);
		JLabel councillorLabel = new JLabel(new ImageIcon(resizedCouncillorImage));
		councillorLabel.setBounds(0, 0, 15, 39);
		councillorLabel.setLocation(x, y);
		mapPanel.add(councillorLabel, 0);
	}

	private void refreshPermitTiles(List<Region> regions) {
		for(Region region : regions) {
			Point point = getCouncilPoint(region.getName());
			int x = point.x - 120;
			int y = point.y - 10;
			drawPermitsTile(((GroupRegionalCity) region).getPermissionDeckUp(), x, y);
		}

	}

	private void drawPermitsTile(Deck permissionDeckUp, int x, int y) {
		List<Card> permissionCards = permissionDeckUp.getCards();
		int xCoord = x;
		for(Card permissionCard : permissionCards) {
			BufferedImage permissionTileImage = readImage(IMAGES_PATH + "permissionCard.png");
			Image resizedPermissionTile = permissionTileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			JLabel permissionTileLabel = new JLabel(new ImageIcon(resizedPermissionTile));
			permissionTileLabel.setBounds(0, 0, 50, 50);
			permissionTileLabel.setLocation(xCoord, y);
			mapPanel.add(permissionTileLabel, 0);
			List<Bonus> bonuses = ((PermissionCard) permissionCard).getBonuses();
			int bonusCoordX = xCoord - 47;
			int bonusCoordY = y + 40;
			for(Bonus bonus : bonuses) {
				drawBonus(bonus, bonusCoordX, bonusCoordY);
				bonusCoordX = bonusCoordX + 24;
			}
			List<City> cities = ((PermissionCard)permissionCard).getCities();
			int cityCoordX = xCoord + 15;
			int	cityCoordY = y;
			for(City city : cities) {
				JLabel cityInitial = new JLabel();
				cityInitial.setBounds(0, 0, 23, 25);
				cityInitial.setLocation(cityCoordX, cityCoordY);
				cityInitial.setFont(new Font("Sans serif", Font.BOLD, 12));
				cityInitial.setForeground(Color.decode("0x000000"));
				cityInitial.setText(Character.toString(city.getName().charAt(0)) + "  ");
				mapPanel.add(cityInitial, 0);
				cityCoordX += 10;
			}
			xCoord -= 52;
		}
	}

	void refreshUI(StartTurnState currentState) {
		addRewardTokens(currentState.getGameMap().getCities());
		refreshKingPosition(currentState.getKing().getPosition().getName());
		refreshPlayersTable(currentState.getPlayersSet());
		refreshCouncils(currentState.getGameMap().getGroupRegionalCity(), currentState.getKing().getCouncil());
		refreshPermitTiles(currentState.getGameMap().getGroupRegionalCity());
		frame.repaint();
	}

	public static void main(String[] args) {
		new SwingUI("hard");
	}

}
