package it.polimi.ingsw.ps23.client.rmi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import it.polimi.ingsw.ps23.server.model.initialization.RawObject;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;

public class SwingUI {

	private static final String CONFIGURATION_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/";
	private static final String CITIES_POSITION_CSV = "citiesPosition.csv";
	private static final String CITIES_CONNECTION_CSV = "citiesConnection.csv";
	private static final String KING_PATH = "images/king.png";

	private Map<String, Component> components;
	private JFrame frame;
	private JPanel mapPanel;
	private JTable playersTable;
	private DefaultTableModel tableModel; 

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
	
	private void loadKing() {
		BufferedImage kingImage = readImage(CONFIGURATION_PATH + KING_PATH);
		Image resizedKingImage = kingImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		JLabel kingLabel = new JLabel(new ImageIcon(resizedKingImage));
		kingLabel.setBounds(0, 0, 50, 50);
		mapPanel.add(kingLabel);
		components.put("king", kingLabel);
	}
	
	private void loadCities() {
		List<String[]> rawCitiesPosition = new RawObject(CONFIGURATION_PATH + CITIES_POSITION_CSV).getRawObject();
		for(String[] rawCityPosition : rawCitiesPosition) {
			BufferedImage cityImage = readImage(CONFIGURATION_PATH + rawCityPosition[5]);
			int x = Integer.parseInt(rawCityPosition[3]);
			int y = Integer.parseInt(rawCityPosition[4]);
			int width = Integer.parseInt(rawCityPosition[1]);
			int height = Integer.parseInt(rawCityPosition[2]);
			Image resizedCityImage = cityImage.getScaledInstance(width - 8, height - 8, Image.SCALE_SMOOTH);
			JLabel cityLabel = new JLabel(new ImageIcon(resizedCityImage));
			cityLabel.setBounds(x, y, width, height);
			JLabel cityName = new JLabel();
			cityName.setBounds(x, y - 38, width, height);
			cityName.setFont(new Font("Algerian", Font.BOLD, 24));
			cityName.setForeground(Color.decode(rawCityPosition[6]));
			cityName.setText(rawCityPosition[0]);
			mapPanel.add(cityName);
			mapPanel.add(cityLabel);
			components.put(rawCityPosition[0], cityLabel);
		}
	}
	
	private void loadStreets() {
		List<String[]> rawCitiesConnection = new RawObject(CONFIGURATION_PATH + CITIES_CONNECTION_CSV).getRawObject();
		for(String[] rawCityConnection : rawCitiesConnection) {
			BufferedImage connectionImage = readImage(CONFIGURATION_PATH + rawCityConnection[0]);
			int x = Integer.parseInt(rawCityConnection[3]);
			int y = Integer.parseInt(rawCityConnection[4]);
			int width = Integer.parseInt(rawCityConnection[1]);
			int height = Integer.parseInt(rawCityConnection[2]);
			Image resizedConnectionImage = connectionImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			JLabel connectionLabel = new JLabel(new ImageIcon(resizedConnectionImage));
			connectionLabel.setBounds(x, y, width, height);
			mapPanel.add(connectionLabel);
		}
	}
	
	private void loadMapBackground() {
		BufferedImage mapImage = readImage(CONFIGURATION_PATH + "images/mapBackground.png");
		Image resizedMapImage = mapImage.getScaledInstance(800, 464, Image.SCALE_SMOOTH);
		JLabel mapLabel = new JLabel(new ImageIcon(resizedMapImage));
		mapLabel.setBounds(0, 0, 800, 464);
		mapPanel.add(mapLabel);
	}
	
	private void loadPlayersTable() {
		int numRows = 0;
		String columnNames[] = new String[] {"Name", "VictoryPoints", "Coins", "Assistants", "Nobility Points"};
		tableModel = new DefaultTableModel(numRows, columnNames.length);
		tableModel.setColumnIdentifiers(columnNames);
		playersTable = new JTable(tableModel);
		mapPanel.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(playersTable);
		Dimension dimension = new Dimension(550, 90);
        scrollPane.setPreferredSize(dimension);
        mapPanel.add(scrollPane, BorderLayout.LINE_END);
	}

	SwingUI() {
		components = new HashMap<>();
		frame = new JFrame();
		frame.setTitle("Council of Four");
		Dimension dimension = new Dimension(800, 600);
		frame.setMinimumSize(dimension);
		frame.setIconImage(readImage(CONFIGURATION_PATH+"images/victoryPoint.png"));
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		mapPanel = new JPanel();
		mapPanel.setLayout(null);
		loadKing();
		loadCities();
		loadStreets();
		loadMapBackground();
		loadPlayersTable();
		frame.add(mapPanel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUI swingUI = new SwingUI();
	}

	private void refreshKingPosition(String city) {
		Point point = getComponents(city).getLocationOnScreen();
		getComponents("king").setLocation(point);
	}
	private void refreshPlayersTable(PlayersSet playerSet) {
		for(int i=tableModel.getRowCount(); i>0; i--) {
			tableModel.removeRow(i);
		}
		for (Player player : playerSet.getPlayers()) {
			Vector vector = new Vector<>();
			vector.add(0, player.getName());
			vector.add(1, player.getVictoryPoints());
			vector.add(2, player.getCoins());
			vector.add(3, player.getAssistants());
			vector.add(4, player.getNobilityTrackPoints());
			tableModel.addRow(vector);	
		}
		
	}
	
	void refreshUI(StartTurnState currentState) {
		refreshKingPosition(currentState.getKing().getPosition().getName());
		refreshPlayersTable(currentState.getPlayerSet());
	}

}
