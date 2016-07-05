package it.polimi.ingsw.ps23.client;

import java.awt.Color;
import java.awt.Cursor;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

import it.polimi.ingsw.ps23.server.model.initialization.RawObject;
import javax.swing.JTextArea;

class GUILoad {
	
	private static final String IMAGES_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/images/";
	private static final String ICON_PATH = "victoryPoint.png";
	
	private static final String CITIES_POSITION_CSV = "citiesPosition.csv";
	private static final String CITIES_CONNECTION_CSV = "citiesConnection.csv";
	private static final String OBJECTS_POSITION_CSV = "objectsPosition.csv";
	private static final String BACKGROUND_PATH = "mapBackground.png";
	private static final String NOBILITY_TRACK_PATH = "nobilityTrack.png";
	private static final String ACTIVE = "Active";
	private static final String KING_PATH = "king.png";
	private static final String PNG_EXTENSION = ".png";

	private String mapPath;
	private Map<String, JLabel> cityLabels;
	private JLabel kingLabel;
	private Map<String, Point> councilPoints;
	private JFrame frame;
	private JPanel mapPanel;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private JScrollPane scrollTextPane;
	private JTable playersTable;
	private JTextArea textArea;
	private JSpinner marketSpinner;
	private JButton sendButton;
	private JDialog otherPlayersDialog;
	
	GUILoad(String mapPath) {
		this.mapPath = mapPath;
		cityLabels = new HashMap<>();
		councilPoints = new HashMap<>();
		frame = new JFrame();
		frame.setTitle("Council of Four");
		Dimension dimension = new Dimension(814, 569);
		frame.setMinimumSize(dimension);
		frame.setIconImage(readImage(IMAGES_PATH + ICON_PATH));
		BufferedImage backgroundImage = readImage(IMAGES_PATH + "woodBackground.png");
		Image resizedBackgroundImage = backgroundImage.getScaledInstance(1366, 768, Image.SCALE_SMOOTH);
		JLabel backgroundLabel = new JLabel(new ImageIcon(resizedBackgroundImage));
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(new GridLayout());
		mapPanel = new JPanel();
		mapPanel.setLayout(null);
		backgroundLabel.setBounds(0, 0, 1366, 768);
		loadComponents();
		frame.getContentPane().add(mapPanel);
		mapPanel.add(scrollPane);
		mapPanel.add(backgroundLabel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	Map<String, JLabel> getCityLabels() {
		return cityLabels;
	}
	
	Map<String, Point> getCouncilPoints() {
		return councilPoints;
	}
	
	JLabel getKingLabel() {
		return kingLabel;
	}
	
	JFrame getFrame() {
		return frame;
	}
	
	JPanel getMapPanel() {
		return mapPanel;
	}
	
	JButton getMarketSendButton() {
		return sendButton;
	}
	JSpinner getMarketSpinner() {
		return marketSpinner;
	}

	DefaultTableModel getTableModel() {
		return tableModel;
	}
	
	JDialog getOthersPlayersDialog() {
		return otherPlayersDialog;
	}

	BufferedImage readImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot find the image file.", e);
		}
		return null;
	}

	private void loadKing() {
		BufferedImage kingImage = readImage(IMAGES_PATH + KING_PATH);
		Image resizedKingImage = kingImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		kingLabel = new JLabel(new ImageIcon(resizedKingImage));
		kingLabel.setBounds(0, 0, 35, 35);
		mapPanel.add(kingLabel);
	}

	private void loadCouncilsPositions() {
		List<String[]> rawCouncilsPosition = new RawObject(mapPath + OBJECTS_POSITION_CSV).getRawObject();
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
			BufferedImage cityImage = readImage(IMAGES_PATH + rawCityPosition[5] + ACTIVE + PNG_EXTENSION);
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
			BufferedImage cityDisabledImage= readImage(IMAGES_PATH + rawCityPosition[5] + PNG_EXTENSION);
			Image resizedCityDisabledImage = cityDisabledImage.getScaledInstance(width - 8, height - 8, Image.SCALE_SMOOTH);
			cityLabel.setDisabledIcon(new ImageIcon(resizedCityDisabledImage));
			cityLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			cityLabel.setEnabled(false);
			mapPanel.add(cityName);
			mapPanel.add(cityLabel);
			cityLabels.put(rawCityPosition[0], cityLabel);
		}
	}

	private void loadStreets() {
		List<String[]> rawCitiesConnection = new RawObject(mapPath + CITIES_CONNECTION_CSV).getRawObject();
		for (String[] rawCityConnection : rawCitiesConnection) {
			BufferedImage connectionImage = readImage(IMAGES_PATH + rawCityConnection[0] + PNG_EXTENSION);
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

	private void loadNobiltyTrack() {
		BufferedImage mapImage = readImage(IMAGES_PATH + NOBILITY_TRACK_PATH);
		Image resizedMapImage = mapImage.getScaledInstance(799, 66, Image.SCALE_SMOOTH);
		JLabel mapLabel = new JLabel(new ImageIcon(resizedMapImage));
		mapLabel.setBounds(0, 0, 799, 66);
		mapLabel.setLocation(0, 464);
		mapPanel.add(mapLabel);
	}

	private void loadPlayersTable() {
		int numRows = 0;
		String[] columnNames = new String[] { "Name", "Coins", "Assistants", "Nobility Points", "Victory Points", "Online" };
		tableModel = new DefaultTableModel(numRows, columnNames.length) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1233748051669817201L;
			boolean[] columnEditables = new boolean[] {
					false, false , false, false , false
				};
				@Override
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		tableModel.setColumnIdentifiers(columnNames);
		playersTable = new JTable(tableModel);
		for(int i = 0; i < playersTable.getColumnCount(); i++){
			playersTable.getColumnModel().getColumn(i).setResizable(false);
		}
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(playersTable);
		scrollPane.setBounds(0, 0, 567, 110);
		scrollPane.setLocation(800, 0);
	}
	private void loadTextArea() {	
		textArea = new JTextArea();
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textArea.setBounds(897, 503, 440, 70);
		textArea.setEditable(false);
		scrollTextPane = new JScrollPane();
		scrollTextPane.setBounds(0, 0, 440, 70);
		scrollTextPane.setLocation(897, 503);
		scrollTextPane.setViewportView(textArea);
		mapPanel.add(scrollTextPane,0);	
		sendButton = new JButton("OK");
		sendButton.setBounds(935, 613, 89, 23);
		mapPanel.add(sendButton,0);
		sendButton.setVisible(false);
		marketSpinner = new JSpinner();
		marketSpinner.setBounds(897, 613, 35, 20);
		mapPanel.add(marketSpinner,0);
		marketSpinner.setVisible(false);
	}
	
	void loadComponents() {
		loadCouncilsPositions();
		loadKing();
		loadCities();
		loadStreets();
		loadMapBackground();
		loadNobiltyTrack();
		loadPlayersTable();
		loadTextArea();
		loadOthersPlayersDialog();
	}
	
	private void loadOthersPlayersDialog() {
		otherPlayersDialog = new JDialog(frame, "Other Players Permit Tiles Status");
		otherPlayersDialog.setBounds(300, 200, 600, 50);
		otherPlayersDialog.setVisible(false);
	}

	public void appendText(String string) {
		textArea.append(string);
	}
}
