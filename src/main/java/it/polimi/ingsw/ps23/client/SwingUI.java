package it.polimi.ingsw.ps23.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import javax.swing.JDialog;

public abstract class SwingUI {

	private static final String CONFIGURATION_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/";
	private static final String IMAGES_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/images/";
	private static final String COUNCILLOR_PATH = "Councillor.png";
	private static final String PERMIT_TILE_PATH = "permitTile.png";
	private static final String POLITIC_CARD_PATH = "Card.png";
	private static final String BONUS_TILE_PATH = "BonusTile.png";
	private static final String PNG_EXTENSION = ".png";
	
	private static final String KINGDOM = "kingdom";
	private static final String ALREADY_ACQUIRED_BONUS_TILE = "alreadyAcquired";
	private static final String NO_KING_TILE = "noKingTile";
	
	private static final String ELECT_COUNCILLOR = "elect councillor";
	private static final String ACQUIRE_BUSINESS_PERMIT_TILE = "acquire business permit tile";
	private static final String ASSISTANT_TO_ELECT_COUNCILLOR = "assistant to elect councillor";
	private static final String ADDITIONAL_MAIN_ACTION = "additional main action";
	private static final String ENGAGE_ASSITANT = "engage assistant";
	private static final String CHANGE_PERMIT_TILE= "change permit tile";
	private static final String BUILD_EMPORIUM_KING = "build emporium king";
	private static final String BUILD_EMPORIUM_TILE = "build emporium permit tile";

	private String mapPath;
	private String playerName;
	private GUIView guiView;
	private String chosenAction;
	private String chosenRegion;
	private String chosenCity;
	private String chosenCouncillor;
	private List<JButton> regionsButtons;
	private JButton btnKingdom;
	private GUILoad guiLoad;
	private JPanel mainActionPanel;
	private JPanel quickActionPanel;
	private JButton skipButton;
	private Map<String, JLabel> cityLabels;
	private Map<String, Point> councilPoints;
	private Map<String, Map<JLabel,JLabel>> freeCouncillorsLabels;
	private JFrame frame;
	private JPanel mapPanel;
	private Map<String, Map<JLabel, List<JLabel>>> permitTiles;
	private int chosenTile;
	private DefaultTableModel tableModel;
	private JButton finished;
	private List<JLabel> cardsList;
	private Map<JLabel, List<JLabel>> playerPermitTiles;
	private Map<JLabel, List<JLabel>> playerAllPermitTiles;
	boolean finish;
	private String chosenCard;
	private JSpinner marketSpinner;
	private JButton marketSendButton;
	private JDialog totalPermitsCardDialog;
	private int spinnerValue;

	protected SwingUI(GUIView guiView, String mapType, String playerName) {
		this.guiView = guiView;
		this.playerName = playerName;
		regionsButtons = new ArrayList<>();
		playerPermitTiles = new HashMap<>();
		mapPath = CONFIGURATION_PATH + mapType + "/";
		guiLoad = new GUILoad(mapPath);
		cityLabels = guiLoad.getCityLabels();
		marketSpinner = guiLoad.getMarketSpinner();
		marketSendButton = guiLoad.getMarketSendButton();
		freeCouncillorsLabels = new HashMap<>();
		loadCitiesButtons();
		councilPoints = guiLoad.getCouncilPoints();
		cardsList = new ArrayList<>();
		permitTiles = new HashMap<>();
		finish = false;
		frame = guiLoad.getFrame();
		mapPanel = guiLoad.getMapPanel();
		tableModel = guiLoad.getTableModel();
		playerAllPermitTiles = new HashMap<>();
		totalPermitsCardDialog = new JDialog(frame, "Your Permission Total HandDeck");
		totalPermitsCardDialog.setBounds(300, 200, 600, 50);
		loadMarketInputArea();
		loadRegionButtons();
		loadMainActionPanel();
		loadQuickActionPanel();
	}

	protected JFrame getFrame() {
		return frame;
	}
	
	public int getChosenValue() {
		return spinnerValue;
	}

	public String getChosenAction() {
		return chosenAction;
	}
	
	public String getChosenRegion() {
		return chosenRegion;
	}
	
	public String getChosenCity() {
		return chosenCity;
	}
	
	public String getChosenCouncillor() {
		return chosenCouncillor;
	}

	public String getChosenCard() {
		return chosenCard;
	}

	public int getChosenTile() {
		return chosenTile;
	}

	protected static String getKingdom() {
		return KINGDOM;
	}
	
	protected static String getAlreadyAcquiredBonusTile() {
		return ALREADY_ACQUIRED_BONUS_TILE;
	}
	
	protected static String getNoKingTile() {
		return NO_KING_TILE;
	}

	protected BufferedImage readImage(String path) {
		return guiLoad.readImage(path);
	}

	private JLabel getCityLabel(String componentName) {
		return cityLabels.get(componentName);
	}

	private Point getCouncilPoint(String region) {
		return councilPoints.get(region);
	}

	public void enableKingButton(boolean display) {
		btnKingdom.setEnabled(display);
	}

	private List<JLabel> drawBonus(Container container, String bonusName, String bonusValue, int x, int y, int width, int height, int yOffset) {
		List<JLabel> bonusList = new ArrayList<>();
		BufferedImage bonusImage = guiLoad.readImage(IMAGES_PATH + bonusName + PNG_EXTENSION);
		Image resizedBonusImage = bonusImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		JLabel bonusLabel = new JLabel(new ImageIcon(resizedBonusImage));
		bonusLabel.setBounds(0, 0, width, height);
		bonusLabel.setLocation(x, y + yOffset);
		bonusList.add(bonusLabel);
		container.add(bonusLabel, 0);
		int bonusNumber = Integer.parseInt(bonusValue);
		if (bonusNumber > 1 || "victoryPoint".equals(bonusName)) {
			JLabel bonusLabelValue = new JLabel();
			bonusLabelValue.setBounds(0, 0, width, height);
			bonusLabelValue.setLocation(x + 8, y + yOffset);
			bonusLabelValue.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
			if ("coin".equals(bonusName)) {
				bonusLabelValue.setForeground(Color.black);
			} else {
				bonusLabelValue.setForeground(Color.white);
			}
			bonusLabelValue.setText(String.valueOf(bonusNumber));
			bonusList.add(bonusLabelValue);
			container.add(bonusLabelValue, 0);
		}
		return bonusList;
	}

	protected void addRewardTokens(List<String> citiesName, List<List<String>> citiesBonusesName, List<List<String>> citiesBonusesValue) {
		for (int i = 0; i < citiesName.size(); i++) {
			Component cityComponent = getCityLabel(citiesName.get(i));
			Point point = cityComponent.getLocationOnScreen();
			int x = point.x;
			int y = point.y;
			for (int j = 0; j < citiesBonusesName.get(i).size(); j++) {
				drawBonus(mapPanel, citiesBonusesName.get(i).get(j), citiesBonusesValue.get(i).get(j), x + 50, y - 20, 23, 25, 0);
				x += 22;
			}
		}
	}

	protected void addNobilityTrackBonuses(List<List<String>> stepsBonusesName, List<List<String>> stepsBonusesValue) {
		int stepNumber = 0;
		for (int i = 0; i < stepsBonusesName.size(); i++) {
			int yOffset = 0;
			int x = (int) 38.1 * stepNumber + 8;
			int y = 495;
			for (int j = 0; j < stepsBonusesName.get(i).size(); j++) {
				if (!("nullBonus").equals(stepsBonusesName.get(i).get(j))) {
					int width = 23;
					int height = 25;
					if ("1".equals(stepsBonusesValue.get(i).get(j))) {
						y = 490;
					}
					if (("recycleRewardToken").equals(stepsBonusesName.get(i).get(j))) {
						y = 476;
						height = 40;
					}
					drawBonus(mapPanel, stepsBonusesName.get(i).get(j), stepsBonusesValue.get(i).get(j), x, y, width, height, yOffset);
					yOffset -= 25;
				}
			}
			stepNumber++;
		}
	}

	protected void refreshKingPosition(String city) {
		Point point = getCityLabel(city).getLocationOnScreen();
		guiLoad.getKingLabel().setLocation(point);
	}

	protected void refreshFreeCouncillors(List<String> freeCouncillors) {
		for (Entry<String, Map<JLabel, JLabel>> entry : freeCouncillorsLabels.entrySet()) {
			Map<JLabel, JLabel> freeCouncillor = entry.getValue();
			for(Entry<JLabel, JLabel> jlabel : freeCouncillor.entrySet()) {
				mapPanel.remove(jlabel.getValue());
				mapPanel.remove(jlabel.getKey());
			}
		}
		Point freeCouncillorsPoint = councilPoints.get("free");
		int x = freeCouncillorsPoint.x;
		int y = freeCouncillorsPoint.y;
		Map<String, Integer> freeCouncillorsMap = new HashMap<>();
		for (String freeCouncillor : freeCouncillors) {
			if (freeCouncillorsMap.containsKey(freeCouncillor)) {
				freeCouncillorsMap.put(freeCouncillor, freeCouncillorsMap.get(freeCouncillor) + 1);
			} else {
				freeCouncillorsMap.put(freeCouncillor, 1);
			}
		}
		for (Entry<String, Integer> entry : freeCouncillorsMap.entrySet()) {
			String color = entry.getKey();
			BufferedImage councillorImage = guiLoad.readImage(IMAGES_PATH + color + COUNCILLOR_PATH);
			Image resizedCouncillorImage = councillorImage.getScaledInstance(18, 39, Image.SCALE_SMOOTH);
			JLabel councillorLabel = new JLabel(new ImageIcon(resizedCouncillorImage));
			councillorLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			councillorLabel.setEnabled(false);
			councillorLabel.setDisabledIcon(new ImageIcon(resizedCouncillorImage));
			councillorLabel.addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
					chosenCouncillor = color;
					councillorLabel.setEnabled(false);
	            	guiView.resume();
                }
			});   
			councillorLabel.setBounds(0, 0, 28, 52);
			councillorLabel.setLocation(x, y);
			mapPanel.add(councillorLabel, 0);
			JLabel councillorsValue = new JLabel();
			councillorsValue.setBounds(0, 0, 10, 25);
			councillorsValue.setLocation(x + 11, y + 16);
			councillorsValue.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			if ("black".equals(color) || "purple".equals(color) || "blue".equals(color)) {
				councillorsValue.setForeground(Color.white);
			} else {
				councillorsValue.setForeground(Color.black);
			}
			councillorsValue.setText(String.valueOf(entry.getValue()));
			mapPanel.add(councillorsValue, 0);
			Map<JLabel, JLabel> freeCouncillor = new HashMap<>();
			freeCouncillor.put(councillorLabel, councillorsValue);
			freeCouncillorsLabels.put(color, freeCouncillor);
			y += 43;
		}
	}

	private void drawCouncillor(String color, int x, int y) {
		BufferedImage councillorImage = guiLoad.readImage(IMAGES_PATH + color + COUNCILLOR_PATH);
		Image resizedCouncillorImage = councillorImage.getScaledInstance(14, 39, Image.SCALE_SMOOTH);
		JLabel councillorLabel = new JLabel(new ImageIcon(resizedCouncillorImage));
		councillorLabel.setBounds(0, 0, 15, 39);
		councillorLabel.setLocation(x, y);
		mapPanel.add(councillorLabel, 0);
	}

	private void drawCouncil(List<String> council, int xCoord, int yCoord) {
		int x = xCoord;
		int y = yCoord;
		for (String councillor : council) {
			x -= 16;
			drawCouncillor(councillor, x, y);
		}
	}

	protected void refreshCouncils(List<String> councilsName, List<List<String>> councilsColor) {
		for(int i = 0; i < councilsName.size(); i++) {
			Point point = getCouncilPoint(councilsName.get(i));
			drawCouncil(councilsColor.get(i), point.x, point.y);
		}
	}

	private void drawPermitTile(Container container, Map<JLabel, List<JLabel>> permitLabels, List<String> permitTileCities, List<String> permitTileBonusesName, List<String> permitTileBonusesValue, int indexOfTile, int x, int y) {
		List<JLabel> listJlabel = new ArrayList<>();
		BufferedImage permissionTileImage = readImage(IMAGES_PATH + PERMIT_TILE_PATH);
		Image resizedPermissionTile = permissionTileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		JLabel permitTileLabel = new JLabel(new ImageIcon(resizedPermissionTile));
		permitTileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		permitTileLabel.setBounds(0, 0, 50, 50);
		permitTileLabel.setLocation(x, y);
		permitTileLabel.addMouseListener(new MouseAdapter() {
			@Override
	           public void mouseClicked(MouseEvent e) {
				chosenTile = indexOfTile;
				permitTileLabel.setEnabled(false);
				guiView.resume();
			}
		});
		container.add(permitTileLabel, 0);
		permitTileLabel.setEnabled(false);
		int bonusCoordX = x - 47;
		int bonusCoordY = y + 40;
		for (int i = 0; i < permitTileBonusesName.size(); i++) {
			listJlabel.addAll(drawBonus(container, permitTileBonusesName.get(i), permitTileBonusesValue.get(i), bonusCoordX + 50, bonusCoordY - 20, 23, 25, 0));
			bonusCoordX = bonusCoordX + 24;
		}
		int cityCoordX = x + 5;
		int cityCoordY = y;
		int citiesNumber = permitTileCities.size();
		for (int i = 0; i < citiesNumber; i++) {
			String cityName = permitTileCities.get(i);
			JLabel cityInitial = new JLabel();
			cityInitial.setBounds(0, 0, 23, 25);
			cityInitial.setLocation(cityCoordX, cityCoordY);
			cityInitial.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			cityInitial.setForeground(Color.black);
			String slash = new String();
			if (i != citiesNumber - 1) {
				slash = " / ";
			}
			cityInitial.setText(cityName + slash);
			container.add(cityInitial, 0);
			listJlabel.add(cityInitial);
			cityCoordX += 17;
		}
		permitLabels.put(permitTileLabel, listJlabel);
	}

	private void drawPermitTiles(Container container, Map<JLabel, List<JLabel>> permitLabels, List<List<String>> permitTilesCities, List<List<String>> permitTilesBonusesName, List<List<String>> permitTilesBonusesValue, int xCoord, int yCoord) {
		int x = xCoord;
		int y = yCoord;
		int indexOfTile = 0;
		for (int i = 0; i < permitTilesCities.size(); i++) {
			drawPermitTile(container, permitLabels, permitTilesCities.get(i), permitTilesBonusesName.get(i), permitTilesBonusesValue.get(i), indexOfTile, x, y);
			x -= 52;
			indexOfTile++;
		}
	}
	
	protected void refreshPermitTilesUp(List<String> regions, List<List<List<String>>> allPermitTilesCities, 
			List<List<List<String>>> allPermitTilesBonusesName, List<List<List<String>>> allPermitTilesBonusesValue) {
		for (int i = 0; i < regions.size(); i++) {
			Point point = getCouncilPoint(regions.get(i));
			int x = point.x;
			int y = point.y;
			Map<JLabel, List<JLabel>> permitTilesLabels = new HashMap<>();
			drawPermitTiles(mapPanel, permitTilesLabels, allPermitTilesCities.get(i), allPermitTilesBonusesName.get(i), allPermitTilesBonusesValue.get(i), x - 120, y - 12);
			permitTiles.put(regions.get(i), permitTilesLabels);
		}
	}
	
	private void drawBonusTile(String groupName, String bonusName, String bonusValue) {
		Point regionPoint = getCouncilPoint(groupName);
		int x = regionPoint.x;
		int y = regionPoint.y;
		if (KINGDOM.equals(groupName)) {
			x -= 63;
			y -= 40;
		}
		else {
			x += 7;
			y -= 8;
		}
		BufferedImage tileImage = guiLoad.readImage(IMAGES_PATH + groupName + BONUS_TILE_PATH);
		Image resizedTileImage = tileImage.getScaledInstance(50, 35, Image.SCALE_SMOOTH);
		JLabel tileLabel = new JLabel(new ImageIcon(resizedTileImage));
		tileLabel.setBounds(0, 0, 50, 35);
		tileLabel.setLocation(x, y);
		mapPanel.add(tileLabel, 0);
		drawBonus(mapPanel, bonusName, bonusValue, x + 25, y + 10, 23, 25, -5);
	}

	protected void refreshBonusTiles(List<String> groupsName, List<String> groupsBonusName, List<String> groupsBonusValue, String kingBonusName, String kingBonusValue) {
		for(int i = 0; i < groupsBonusName.size(); i++) {
			String regionBonusName = groupsBonusName.get(i);
			if (!ALREADY_ACQUIRED_BONUS_TILE.equals(regionBonusName)) {
				drawBonusTile(groupsName.get(i), regionBonusName, groupsBonusValue.get(i));
			}
		}
		if (!NO_KING_TILE.equals(kingBonusName)) {
			drawBonusTile(KINGDOM, kingBonusName, kingBonusValue);
		}
	}

	protected void refreshPlayersTable(List<String> playersName, List<String> playersCoins, 
			List<String> playersAssistants, List<String> playersNobilityTrackPoints, List<String> playersVictoryPoints) {
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		for (int i = 0; i < playersName.size(); i++) {
			Vector<Object> vector = new Vector<>();
			vector.add(0, playersName.get(i));
			vector.add(1, playersCoins.get(i));
			vector.add(2, playersAssistants.get(i));
			vector.add(3, playersNobilityTrackPoints.get(i));
			vector.add(4, playersVictoryPoints.get(i));
			tableModel.addRow(vector);
		}
	}
	
	protected void refreshPoliticCards(Map<String, List<String>> playersPoliticCards) {
		int x = 0;
		int y = 535;
		for(JLabel card : cardsList) {
			mapPanel.remove(card);
		}
		
		if(finished != null){
			mapPanel.remove(finished);
		}
		for (String card : playersPoliticCards.get(playerName)) {
			BufferedImage cardImage = readImage(IMAGES_PATH + card + POLITIC_CARD_PATH);
			Image resizedCardImage = cardImage.getScaledInstance(42, 66, Image.SCALE_SMOOTH);
			JLabel cardLabel = new JLabel(new ImageIcon(resizedCardImage));
			cardLabel.setDisabledIcon(new ImageIcon(resizedCardImage));
			cardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			cardsList.add(cardLabel);
			cardLabel.addMouseListener(new MouseAdapter() {
					@Override
	                public void mouseClicked(MouseEvent e) {
						chosenCard = card;
						cardLabel.setEnabled(false);
						guiView.resume();
	                }
	        });      
			cardLabel.setBounds(0, 0, 42, 66);
			cardLabel.setLocation(x, y);
			x += 44;
			mapPanel.add(cardLabel, 0);
			cardLabel.setEnabled(false);
		}
		finished = new JButton("Finished");
		finished.addActionListener(new ActionListener() {
			@Override 
            public void actionPerformed(ActionEvent e)
            {
				finish = true;
				guiView.resume();
            }
        });
		finished.setBounds(x, y, 80, 40);
		finished.setEnabled(false);
		mapPanel.add(finished, 0);
	}

	protected void loadMainActionPanel() {
		mainActionPanel = new JPanel();
		mainActionPanel.setBounds(925, 181, 215, 272);
		mapPanel.add(mainActionPanel,0);
		mainActionPanel.setVisible(false);
		GridBagLayout gblMainActionPanel = new GridBagLayout();
		gblMainActionPanel.columnWidths = new int[]{0, 0};
		gblMainActionPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gblMainActionPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gblMainActionPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		mainActionPanel.setLayout(gblMainActionPanel);
		JButton btnAcquireBusinessPermitTile = new JButton();
		btnAcquireBusinessPermitTile.addActionListener(new ActionListener() {
			@Override  
            public void actionPerformed(ActionEvent e)
            {
            	chosenAction = ACQUIRE_BUSINESS_PERMIT_TILE;
            	guiView.resume();
            }
        });      
		BufferedImage acquireBusinessPermitTileImage = guiLoad.readImage(IMAGES_PATH + "acquireBusinessPermitTile.png");
		btnAcquireBusinessPermitTile.setIcon(new ImageIcon(acquireBusinessPermitTileImage));
		GridBagConstraints gbcbtnAcquireBusinessPermitTile = new GridBagConstraints();
		gbcbtnAcquireBusinessPermitTile.insets = new Insets(0, 0, 5, 0);
		gbcbtnAcquireBusinessPermitTile.gridx = 0;
		gbcbtnAcquireBusinessPermitTile.gridy = 0;
		mainActionPanel.add(btnAcquireBusinessPermitTile, gbcbtnAcquireBusinessPermitTile);
		
		JButton btnBuildEmporiumKing = new JButton();
		btnBuildEmporiumKing.addActionListener(new ActionListener() {
			@Override 
            public void actionPerformed(ActionEvent e)
            {
            	chosenAction = BUILD_EMPORIUM_KING;
            	guiView.resume();
            }
        });
		BufferedImage buildEmporiumKingImage = guiLoad.readImage(IMAGES_PATH + "buildEmporiumKing.png");
		btnBuildEmporiumKing.setIcon(new ImageIcon(buildEmporiumKingImage));
		btnBuildEmporiumKing.setBounds(0, 0, 182, 54);
		GridBagConstraints gbcbtnBuildEmporiumKing = new GridBagConstraints();
		gbcbtnBuildEmporiumKing.insets = new Insets(0, 0, 5, 0);
		gbcbtnBuildEmporiumKing.gridx = 0;
		gbcbtnBuildEmporiumKing.gridy = 1;
		mainActionPanel.add(btnBuildEmporiumKing, gbcbtnBuildEmporiumKing);
		
		JButton btnElectCouncillor = new JButton();
		btnElectCouncillor.addActionListener(new ActionListener() {
			@Override  
            public void actionPerformed(ActionEvent e)
            {
            	chosenAction = ELECT_COUNCILLOR;
            	guiView.resume();
            }
        });
		BufferedImage electCouncillorImage = guiLoad.readImage(IMAGES_PATH + "electCouncillor.png");
		btnElectCouncillor.setIcon(new ImageIcon(electCouncillorImage));
		GridBagConstraints gbcbtnElectCouncillor = new GridBagConstraints();
		gbcbtnElectCouncillor.insets = new Insets(0, 0, 5, 0);
		gbcbtnElectCouncillor.gridx = 0;
		gbcbtnElectCouncillor.gridy = 2;
		mainActionPanel.add(btnElectCouncillor, gbcbtnElectCouncillor);
		
		JButton btnBuildEmporiumPermitTile = new JButton();
		btnBuildEmporiumPermitTile.addActionListener(new ActionListener() {
			@Override  
            public void actionPerformed(ActionEvent e)
            {
            	chosenAction = BUILD_EMPORIUM_TILE;
            	guiView.resume();
            }
        });
		BufferedImage builEmporiumPermitTileImage = guiLoad.readImage(IMAGES_PATH + "buildEmporiumPermitTile.png");
		btnBuildEmporiumPermitTile.setIcon(new ImageIcon(builEmporiumPermitTileImage));
		GridBagConstraints gbcbtnBuildEmporiumPermitTile = new GridBagConstraints();
		gbcbtnBuildEmporiumPermitTile.gridx = 0;
		gbcbtnBuildEmporiumPermitTile.gridy = 3;
		mainActionPanel.add(btnBuildEmporiumPermitTile, gbcbtnBuildEmporiumPermitTile);
	}
	
	protected void loadQuickActionPanel() {
		quickActionPanel = new JPanel();
		quickActionPanel.setBounds(1150, 181, 199, 272);
		mapPanel.add(quickActionPanel,0);
		quickActionPanel.setVisible(false);
		GridBagLayout gblQuickActionPanel = new GridBagLayout();
		gblQuickActionPanel.columnWidths = new int[]{0, 0};
		gblQuickActionPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gblQuickActionPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gblQuickActionPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		quickActionPanel.setLayout(gblQuickActionPanel);
		
		JButton btnEngageAssistant = new JButton();
		btnEngageAssistant.addActionListener(new ActionListener() {
			@Override  
            public void actionPerformed(ActionEvent e)
            {
            	chosenAction = ENGAGE_ASSITANT;
            	guiView.resume();
            }
        });
		BufferedImage engageAssistantImage = guiLoad.readImage(IMAGES_PATH + "engageAssistant.png");
		btnEngageAssistant.setIcon(new ImageIcon(engageAssistantImage));
		GridBagConstraints gbcbtnEngageAssistant = new GridBagConstraints();
		gbcbtnEngageAssistant.insets = new Insets(0, 0, 5, 0);
		gbcbtnEngageAssistant.gridx = 0;
		gbcbtnEngageAssistant.gridy = 0;
		quickActionPanel.add(btnEngageAssistant, gbcbtnEngageAssistant);
		
		JButton btnChangePermitsTile= new JButton();
		btnChangePermitsTile.addActionListener(new ActionListener() {
			@Override  
            public void actionPerformed(ActionEvent e)
            {
            	chosenAction = CHANGE_PERMIT_TILE;
            	guiView.resume();
            }
        });
		BufferedImage changePermitsTileImage = guiLoad.readImage(IMAGES_PATH + "changePermitsTile.png");
		btnChangePermitsTile.setIcon(new ImageIcon(changePermitsTileImage));
		GridBagConstraints gbcbtnChangePermitsTile = new GridBagConstraints();
		gbcbtnChangePermitsTile.insets = new Insets(0, 0, 5, 0);
		gbcbtnChangePermitsTile.gridx = 0;
		gbcbtnChangePermitsTile.gridy = 1;
		quickActionPanel.add(btnChangePermitsTile, gbcbtnChangePermitsTile);
		
		JButton btnAssistantToElectCouncillor = new JButton();
		btnAssistantToElectCouncillor.addActionListener(new ActionListener() {
			@Override  
            public void actionPerformed(ActionEvent e)
            {
            	chosenAction = ASSISTANT_TO_ELECT_COUNCILLOR;
            	guiView.resume();
            }
        });
		BufferedImage assistantToElectCouncillorImage = guiLoad.readImage(IMAGES_PATH + "assistantToElectCouncillor.png");
		btnAssistantToElectCouncillor.setIcon(new ImageIcon(assistantToElectCouncillorImage));
		GridBagConstraints gbcbtnAssistantToElectCouncillor = new GridBagConstraints();
		gbcbtnAssistantToElectCouncillor.insets = new Insets(0, 0, 5, 0);
		gbcbtnAssistantToElectCouncillor.gridx = 0;
		gbcbtnAssistantToElectCouncillor.gridy = 2;
		quickActionPanel.add(btnAssistantToElectCouncillor, gbcbtnAssistantToElectCouncillor);
		
		JButton btnAdditionalMainAction= new JButton();
		btnAdditionalMainAction.addActionListener(new ActionListener() {
			@Override  
            public void actionPerformed(ActionEvent e)
            {
            	chosenAction = ADDITIONAL_MAIN_ACTION; 
            	guiView.resume();
            }
        });
		BufferedImage additionalMainActionImage = guiLoad.readImage(IMAGES_PATH + "buyMainAction.png");
		btnAdditionalMainAction.setIcon(new ImageIcon(additionalMainActionImage));
		GridBagConstraints gbcbtnAdditionalMainAction = new GridBagConstraints();
		gbcbtnAdditionalMainAction.gridx = 0;
		gbcbtnAdditionalMainAction.gridy = 3;
		quickActionPanel.add(btnAdditionalMainAction, gbcbtnAdditionalMainAction);
		skipButton = new JButton("skip");
		skipButton.addActionListener(new ActionListener() {
			@Override  
			public void actionPerformed(ActionEvent e) {
				chosenAction = "skip"; 
				guiView.resume();
				}
			});
		skipButton.setEnabled(false);
		skipButton.setBounds(1300, 453, 66, 40);
		mapPanel.add(skipButton, 0);
		
	}

	private void loadRegionButtons() {
		BufferedImage kingImage = guiLoad.readImage(IMAGES_PATH + "kingIcon.png");
		btnKingdom = new JButton("");
		btnKingdom.addActionListener(new ActionListener() {
			@Override 
            public void actionPerformed(ActionEvent e)
            {
            	chosenRegion = "king";
            	guiView.resume();
            }
        });
		btnKingdom.setIcon(new ImageIcon(kingImage));
		btnKingdom.setBounds(865, 414, 50, 50);
		mapPanel.add(btnKingdom, 0);
		btnKingdom.setEnabled(false);
		BufferedImage seasideImage = guiLoad.readImage(IMAGES_PATH + "seasideRegion.png");
		JButton btnSeaside = new JButton("");
		btnSeaside.addActionListener(new ActionListener() {
			@Override 
            public void actionPerformed(ActionEvent e)
            {
            	chosenRegion = "seaside";
            	guiView.resume();
            }
        });
		btnSeaside.setIcon(new ImageIcon(seasideImage));
		btnSeaside.setBounds(120, 0, 50, 50);
		mapPanel.add(btnSeaside, 0);
		regionsButtons.add(btnSeaside);
		btnSeaside.setEnabled(false);
		BufferedImage hillImage = guiLoad.readImage(IMAGES_PATH + "hillRegion.png");
		JButton btnHill = new JButton("");
		btnHill.addActionListener(new ActionListener() {
			@Override 
            public void actionPerformed(ActionEvent e)
            {
            	chosenRegion = "hill";
            	guiView.resume();
            }
        });
		btnHill.setIcon(new ImageIcon(hillImage));
		btnHill.setBounds(370, 0, 50, 50);
		mapPanel.add(btnHill, 0);
		regionsButtons.add(btnHill);
		btnHill.setEnabled(false);
		BufferedImage mountainImage = guiLoad.readImage(IMAGES_PATH + "mountainRegion.png");
		JButton btnMountain = new JButton("");
		btnMountain.addActionListener(new ActionListener() {
			@Override 
            public void actionPerformed(ActionEvent e)
            {
            	chosenRegion = "mountain";
            	guiView.resume();
            }
        });
		btnMountain.setIcon(new ImageIcon(mountainImage));
		btnMountain.setBounds(670, 0, 50, 50);
		mapPanel.add(btnMountain, 0);
		regionsButtons.add(btnMountain);
		btnMountain.setEnabled(false);
	}
	
	private void loadCitiesButtons() {
		Set<Entry<String, JLabel>> cityLabelsSet = cityLabels.entrySet();
		for (Entry<String, JLabel> cityLabel : cityLabelsSet){
			cityLabel.getValue().setToolTipText("");
			cityLabel.getValue().addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
					chosenCity = cityLabel.getKey();
					cityLabel.getValue().setEnabled(false);
					guiView.resume();
                }
			});   
		}
	}

	private void enableCitiesButtons(boolean display) {
		Set<Entry<String, JLabel>> cityLabelsSet = cityLabels.entrySet();
		for (Entry<String, JLabel> cityLabel : cityLabelsSet){
			cityLabel.getValue().setEnabled(display);
		}
	}

	private void enableCards(boolean display) {
		for (JLabel jLabel : cardsList) {
			jLabel.setEnabled(display);
		}
	}

	public void showAvailableActions(boolean isAvailableMainAction, boolean isAvailableQuickAction) {
		mainActionPanel.setVisible(isAvailableMainAction);
		quickActionPanel.setVisible(isAvailableQuickAction);
		skipButton.setEnabled(false);
		if(!isAvailableMainAction && isAvailableQuickAction) {
			skipButton.setEnabled(true);
		}
		enableRegionButtons(false);
	}

	public void enableFreeCouncillorsButtons(boolean display) {
		for (Entry<String, Map<JLabel, JLabel>> entry : freeCouncillorsLabels.entrySet()) {
			Map<JLabel, JLabel> freeCouncillor = entry.getValue();
			for(Entry<JLabel, JLabel> jlabel : freeCouncillor.entrySet()) {
				jlabel.getKey().setEnabled(true);
			}
		}
	
	}

	public void enablePoliticCards(boolean display) {
		enableCards(display);
	}

	public void enablePermitTilesPanel(String chosenCouncil) {
		Map<JLabel, List<JLabel>> permitTilesLabels = permitTiles.get(chosenCouncil);
		for (JLabel jLabel : permitTilesLabels.keySet()) {
			jLabel.setEnabled(true);
		}
	}

	public void enableCities(boolean display) {
		enableCitiesButtons(display);
	}

	public void enableRegionButtons(boolean display) {
		for (JButton regionButton : regionsButtons) {
			regionButton.setEnabled(display);
		}
	}

	public void enablePermitTileDeck(boolean display) {
		for (JLabel jLabel : playerPermitTiles.keySet()) {
			jLabel.setEnabled(display);
		}
	}

	public void enableButtons(boolean display) {
		enableRegionButtons(display);
	}

	protected void clearChosenRegion() {
		chosenRegion = null;
	}

	public void clearSwingUI() {
		chosenCard = null;
		finish = false;
		clearChosenRegion();
		Set<Entry<String, Map<JLabel, List<JLabel>>>> allPermitTilesEntries = permitTiles.entrySet();
		for(Entry<String, Map<JLabel, List<JLabel>>> permitTilesEntry : allPermitTilesEntries) {
			Map<JLabel, List<JLabel>> permitTiles = permitTilesEntry.getValue();
			for(JLabel label : permitTiles.keySet()) {
				label.setEnabled(false);
			}
		}
	}
	
	public boolean hasFinished() {
		return finish;
	}

	public void loadMarketInputArea() {
		marketSendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
		         spinnerValue = (int)marketSpinner.getValue();
		         guiView.resume();
		     }
		});
	}
		     
	public void setConsoleText(String string) {
		guiLoad.setText(string);
	}
	
	public void appendConsoleText(String string) {
		guiLoad.appendText(string);
	}
	
	public void enableMarketInputArea(boolean display) {
		marketSpinner.setVisible(display);
		marketSpinner.setEnabled(display);
		marketSendButton.setVisible(display);
		marketSendButton.setEnabled(display);
	}

	public void enableFinish(boolean display) {
		finished.setEnabled(display);
	}
	
	protected void refreshAcquiredPermitTiles(List<String> playersName, List<List<List<String>>> permitTilesCities, List<List<List<String>>> permitTilesBonusesName, List<List<List<String>>> permitTilesBonusesValue) {
		Set<Entry<JLabel, List<JLabel>>> playerPermitTilesSet = playerPermitTiles.entrySet();
		for(Entry<JLabel, List<JLabel>> playerPermitTile : playerPermitTilesSet) {
			mapPanel.remove(playerPermitTile.getKey());
			for(JLabel jLabel : playerPermitTile.getValue()){
				mapPanel.remove(jLabel);
			}
		}
		playerPermitTiles.clear();
		
		int x = 0;
		int y = 611;
		int playerIndex = playersName.indexOf(playerName);
		for(int i = 0; i < permitTilesCities.get(playerIndex).size(); i++) {
			drawPermitTiles(mapPanel, playerPermitTiles, permitTilesCities.get(i), permitTilesBonusesName.get(i), permitTilesBonusesValue.get(i), x, y);
			x += 52;
		}
	}
	
	protected void refreshAllPermitTiles(List<String> playersName, List<List<List<String>>> permitTilesCities, List<List<List<String>>> permitTilesBonusesName, List<List<List<String>>> permitTilesBonusesValue) {
		Set<Entry<JLabel, List<JLabel>>> playerAllPermitTilesSet = playerAllPermitTiles.entrySet();
		for(Entry<JLabel, List<JLabel>> playerPermitTile : playerAllPermitTilesSet) {
			totalPermitsCardDialog.remove(playerPermitTile.getKey());
			for(JLabel jLabel : playerPermitTile.getValue()){
				totalPermitsCardDialog.remove(jLabel);
			}
		}
		
		int x = 0;
		int y = 0;
		int playerIndex = playersName.indexOf(playerName);
		for(int i = 0; i < permitTilesCities.get(playerIndex).size(); i++) {
			drawPermitTiles(totalPermitsCardDialog, playerAllPermitTiles, permitTilesCities.get(i), permitTilesBonusesName.get(i), permitTilesBonusesValue.get(i), x, y);
			x += 52;
		}
	}
	

	public void enableTotalHandDeck(boolean display) {
		for (JLabel jLabel : playerAllPermitTiles.keySet()) {
			jLabel.setEnabled(display);
		}
		totalPermitsCardDialog.setVisible(display);
		
	}	
	

}