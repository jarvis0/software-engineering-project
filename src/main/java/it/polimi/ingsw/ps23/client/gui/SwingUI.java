package it.polimi.ingsw.ps23.client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import javax.swing.JDialog;
/**
 * Manages Swing GUI refresh and draw methods.
 */
public abstract class SwingUI {

	private static final String CONFIGURATION_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/";
	private static final String IMAGES_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/images/";
	private static final String PERMIT_TILE_PATH = "permitTile.png";
	private static final String POLITIC_CARD_PATH = "Card.png";
	private static final String COUNCILLOR_PATH = "Councillor.png";

	private static final String KINGDOM = "kingdom";
	private static final String NO_KING_TILE = "noKingTile";

	private String mapPath;
	private String playerName;
	private GUIView guiView;
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
	private Map<String, Map<JLabel, JLabel>> freeCouncillorsLabels;
	private JFrame frame;
	private JPanel mapPanel;
	private Map<String, Map<JLabel, List<JLabel>>> permitTiles;
	private int chosenTile;
	private DefaultTableModel tableModel;
	private JButton finished;
	private List<JLabel> cardsList;
	private Map<JLabel, List<JLabel>> playerPermitTiles;
	private Map<JLabel, Map <JLabel, List<JLabel>>> otherPlayersPermitTiles;
	private Map<JLabel, List<JLabel>> playerAllPermitTiles;
	boolean finish;
	private String chosenCard;
	private JSpinner marketSpinner;
	private JButton marketSendButton;
	private JDialog totalPermitsCardDialog;
	private int spinnerValue;
	private boolean cityListener;
	private boolean freeCuncillorListener;
	private boolean permitTileListener;
	private boolean politicCardListener;
	private JDialog otherPlayersDialog;
	private JButton otherPlayersStatusButton;
	private Map<String, Map<JLabel,List<JLabel>>> bonusTilePanels;
	private SwingButtonsSet buttons;
	private SwingDraw swingDraw;

	protected SwingUI(GUIView guiView, String mapType, String playerName) {
		this.guiView = guiView;
		this.playerName = playerName;
		regionsButtons = new ArrayList<>();
		playerPermitTiles = new HashMap<>();
		otherPlayersPermitTiles = new HashMap<>();
		bonusTilePanels = new HashMap<>();
		mapPath = CONFIGURATION_PATH + mapType + "/";
		guiLoad = new GUILoad(mapPath);
		cityLabels = guiLoad.getCityLabels();
		marketSpinner = guiLoad.getMarketSpinner();
		marketSendButton = guiLoad.getMarketSendButton();
		freeCouncillorsLabels = new HashMap<>();
		cityListener = false;
		freeCuncillorListener = false;
		permitTileListener = false;
		politicCardListener = false;
		loadCitiesButtons();
		councilPoints = guiLoad.getCouncilPoints();
		cardsList = new ArrayList<>();
		permitTiles = new HashMap<>();
		finish = false;
		frame = guiLoad.getFrame();
		mapPanel = guiLoad.getMapPanel();
		swingDraw = new SwingDraw(mapPanel, guiLoad);
		tableModel = guiLoad.getTableModel();
		playerAllPermitTiles = new HashMap<>();
		totalPermitsCardDialog = new JDialog(frame, "Your Permission Total HandDeck");
		totalPermitsCardDialog.setBounds(300, 200, 600, 90);
		otherPlayersDialog = guiLoad.getOthersPlayersDialog();
		loadMarketInputArea();
		buttons = new SwingButtonsSet(guiLoad, guiView, mapPanel);
		loadRegionButtons();
		loadMainActionPanel();
		loadQuickActionPanel();
		loadOthersPlayersStatusButton();
	}

	private void loadOthersPlayersStatusButton() {
		otherPlayersStatusButton = new JButton("Players Status");
		otherPlayersStatusButton.addActionListener(e -> 
			otherPlayersDialog.setVisible(true)
		);
		otherPlayersStatusButton.setBounds(1150, 110, 200, 30);
		mapPanel.add(otherPlayersStatusButton, 0);
	}

	protected JFrame getFrame() {
		return frame;
	}

	public int getChosenValue() {
		return spinnerValue;
	}

	/**
	 * @return the current player chosen action.
	 */
	public String getChosenAction() {
		return buttons.getChosenAction();
	}

	/**
	 * @return the current player chosen region.
	 */
	public String getChosenRegion() {
		return buttons.getChosenRegion();
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
	/**
	 * Sets the value of display of the king buttons in GUI. True will set visible, false not visible.
	 * @param display - value of display
	 */
	public void enableKingButton(boolean display) {
		btnKingdom.setEnabled(display);
	}

	protected void addRewardTokens(List<String> citiesName, List<List<String>> citiesBonusesName,
			List<List<String>> citiesBonusesValue) {
		for (int i = 0; i < citiesName.size(); i++) {
			Component cityComponent = getCityLabel(citiesName.get(i));
			Point point = cityComponent.getLocationOnScreen();
			int x = point.x;
			int y = point.y;
			for (int j = 0; j < citiesBonusesName.get(i).size(); j++) {
				swingDraw.drawBonus(mapPanel, citiesBonusesName.get(i).get(j), citiesBonusesValue.get(i).get(j), new Point(x + 50, y - 20), 23, 25, 0);
				x += 22;
			}
		}
	}

	protected void addNobilityTrackBonuses(List<List<String>> stepsBonusesName, List<List<String>> stepsBonusesValue) {
		swingDraw.addNobilityTrackBonuses(stepsBonusesName, stepsBonusesValue);
	}

	protected void refreshKingPosition(String city) {
		Point point = getCityLabel(city).getLocationOnScreen();
		guiLoad.getKingLabel().setLocation(point);
	}

	private void drawFreeCouncillor(Map<String, Integer> freeCouncillorsMap, Point freeCouncillorsPoint) {
		int x = freeCouncillorsPoint.x;
		int y = freeCouncillorsPoint.y;
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
					if(freeCuncillorListener) {
						chosenCouncillor = color;
						councillorLabel.setEnabled(true);
						guiView.resume();
					}
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

	protected void refreshFreeCouncillors(List<String> freeCouncillors) {
		for (Entry<String, Map<JLabel, JLabel>> entry : freeCouncillorsLabels.entrySet()) {
			Map<JLabel, JLabel> freeCouncillor = entry.getValue();
			for (Entry<JLabel, JLabel> jlabel : freeCouncillor.entrySet()) {
				mapPanel.remove(jlabel.getValue());
				mapPanel.remove(jlabel.getKey());
			}
		}
		Point freeCouncillorsPoint = councilPoints.get("free");
		Map<String, Integer> freeCouncillorsMap = new HashMap<>();
		for (String freeCouncillor : freeCouncillors) {
			if (freeCouncillorsMap.containsKey(freeCouncillor)) {
				freeCouncillorsMap.put(freeCouncillor, freeCouncillorsMap.get(freeCouncillor) + 1);
			} else {
				freeCouncillorsMap.put(freeCouncillor, 1);
			}
		}
		drawFreeCouncillor(freeCouncillorsMap, freeCouncillorsPoint);
	}
	
	protected void refreshCouncils(List<String> councilsName, List<List<String>> councilsColor) {
		for (int i = 0; i < councilsName.size(); i++) {
			Point point = getCouncilPoint(councilsName.get(i));
			swingDraw.drawCouncil(councilsColor.get(i), point.x, point.y);
		}
	}

	private void drawPermitTile(Container container, Map<JLabel, List<JLabel>> permitLabels,
			List<String> permitTileCities, List<String> permitTileBonusesName, List<String> permitTileBonusesValue,
			int indexOfTile, Point point) {
		int x = (int) point.getX();
		int y = (int) point.getY();
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
				if(permitTileListener) {
					chosenTile = indexOfTile;
					permitTileLabel.setEnabled(true);
					guiView.resume();
				}
			}
		});
		container.add(permitTileLabel, 0);
		permitTileLabel.setEnabled(false);
		int cityCoordX = x + 5;
		int cityCoordY = y;
		int citiesNumber = permitTileCities.size();
		for (int i = 0; i < citiesNumber; i++) {
			String cityName = permitTileCities.get(i);
			JLabel cityInitial = new JLabel();
			String slash = new String();
			if (i != citiesNumber - 1) {
				slash = " / ";
			}
			cityInitial.setText(cityName + slash);
			cityInitial.setBounds(0, 0, 23, 25);
			cityInitial.setLocation(cityCoordX, cityCoordY);
			cityInitial.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			cityInitial.setForeground(Color.black);
			container.add(cityInitial, 0);
			listJlabel.add(cityInitial);
			cityCoordX += 17;
		}
		int bonusCoordX = x - 47;
		int bonusCoordY = y + 40;
		for (int i = 0; i < permitTileBonusesName.size(); i++) {
			listJlabel.addAll(swingDraw.drawBonus(container, permitTileBonusesName.get(i), permitTileBonusesValue.get(i), new Point(bonusCoordX + 50, bonusCoordY - 20), 23, 25, 0));
			bonusCoordX = bonusCoordX + 24;
		}
		JLabel emptyLabel = new JLabel();
		emptyLabel.setText(" ");
		container.add(emptyLabel, 0);
		permitLabels.put(permitTileLabel, listJlabel);
	}

	private void drawPermitTiles(Container container, Map<JLabel, List<JLabel>> permitLabels,
			List<List<String>> permitTilesCities, List<List<String>> permitTilesBonusesName,
			List<List<String>> permitTilesBonusesValue, Point point, int increment) {
		int x = (int) point.getX();
		int y = (int) point.getY();
		int indexOfTile = 0;
		for (int i = 0; i < permitTilesCities.size(); i++) {
			drawPermitTile(container, permitLabels, permitTilesCities.get(i), permitTilesBonusesName.get(i),
					permitTilesBonusesValue.get(i), indexOfTile, new Point(x, y));
			x += increment;
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
			drawPermitTiles(mapPanel, permitTilesLabels, allPermitTilesCities.get(i), allPermitTilesBonusesName.get(i),
					allPermitTilesBonusesValue.get(i), new Point(x - 120, y - 12), -52);
			permitTiles.put(regions.get(i), permitTilesLabels);
		}
	}

	protected void refreshBonusTiles(List<String> groupsName, List<String> groupsBonusName,
			List<String> groupsBonusValue, String kingBonusName, String kingBonusValue) {
		for (Entry<String, Map<JLabel, List<JLabel>>> bonusTile : bonusTilePanels.entrySet()) {
			for(Entry<JLabel, List<JLabel>> bonusLabel  : bonusTile.getValue().entrySet()) {
				mapPanel.remove(bonusLabel.getKey());
				for(JLabel jLabel : bonusLabel.getValue()) {
					mapPanel.remove(jLabel);
				}
			}
		}
		bonusTilePanels.clear();
		for (int i = 0; i < groupsBonusName.size(); i++) {
			String regionBonusName = groupsBonusName.get(i);
			String groupName = groupsName.get(i);
			swingDraw.drawBonusTile(bonusTilePanels, groupName, regionBonusName, groupsBonusValue.get(i), getCouncilPoint(groupName));
		}
		if (!NO_KING_TILE.equals(kingBonusName)) {
			swingDraw.drawBonusTile(bonusTilePanels, KINGDOM, kingBonusName, kingBonusValue, getCouncilPoint(KINGDOM));
		}
	}

	protected void refreshPlayersTable(List<String> playersName, List<String> playersCoins,
			List<String> playersAssistants, List<String> playersNobilityTrackPoints,
			List<String> playersVictoryPoints, List<String> playersAreOnline) {
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		for (int i = 0; i < playersName.size(); i++) {
			String nameField =  playersName.get(i);
			String playerCoinsField = playersCoins.get(i);
			String playerAssistantsField = playersAssistants.get(i);
			String playersNobilityTrackPointsField = playersNobilityTrackPoints.get(i);
			String playerVictoryPointsField = playersVictoryPoints.get(i);
			String playerIsOnline = playersAreOnline.get(i); 
			Object[] vector = {nameField, playerCoinsField, playerAssistantsField, playersNobilityTrackPointsField, playerVictoryPointsField, playerIsOnline};
			tableModel.addRow(vector);
		}
	}

	protected void refreshPoliticCards(Map<String, List<String>> playersPoliticCards) {
		int x = 0;
		int y = 535;
		for (JLabel card : cardsList) {
			mapPanel.remove(card);
		}
		if (finished != null) {
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
					if(politicCardListener) {
						chosenCard = card;
						cardLabel.setEnabled(false);
						guiView.resume();
					}
				}
			});
			cardLabel.setBounds(0, 0, 42, 66);
			cardLabel.setLocation(x, y);
			x += 44;
			mapPanel.add(cardLabel, 0);
			cardLabel.setEnabled(false);
		}
		finished = new JButton("Finish");
		finished.addActionListener(e -> {
			finish = true;
			guiView.resume();
		});
		finished.setBounds(x, y, 80, 40);
		finished.setEnabled(false);
		mapPanel.add(finished, 0);
	}

	protected void loadMainActionPanel() {
		mainActionPanel = new JPanel();
		buttons.loadMainActionPanel(mainActionPanel);
		
	}

	protected void loadQuickActionPanel() {
		quickActionPanel = new JPanel();
		skipButton = buttons.loadQuickActionPanel(quickActionPanel);

	}

	private void loadRegionButtons() {
		btnKingdom = new JButton();
		buttons.loadRegionButtons(regionsButtons, btnKingdom);
		
	}

	private void loadCitiesButtons() {
		Set<Entry<String, JLabel>> cityLabelsSet = cityLabels.entrySet();
		for (Entry<String, JLabel> cityLabel : cityLabelsSet){
			cityLabel.getValue().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(cityListener) {
						chosenCity = cityLabel.getKey();
						cityLabel.getValue().setEnabled(true);
						guiView.resume();
					}
				}
			});
		}
	}
	
	protected void refreshCitiesToolTip(List<String> citiesName, List<List<String>> citiesBuiltEmporium) {
		int i = 0;
		for(String cityName : citiesName) {
			StringBuilder toolTipString = new StringBuilder();
			toolTipString.append("List of Player's Emporiums: \n");
			JLabel cityLabel = cityLabels.get(cityName);
			for(String playerNameString : citiesBuiltEmporium.get(i)) {
				toolTipString.append(playerNameString);
			}
			cityLabel.setToolTipText(new String(toolTipString) + "\n");
			i++;
		}
	}

	/**
	 * Sets the visibility of the panel of main action and quick action. If is it true it will display the 
	 * panel else the panel will be not displayed.
	 * @param isAvailableMainAction - the visibility of main action
	 * @param isAvailableQuickAction - the visibility of quick action
	 */
	public void showAvailableActions(boolean isAvailableMainAction, boolean isAvailableQuickAction) {
		mainActionPanel.setVisible(isAvailableMainAction);
		quickActionPanel.setVisible(isAvailableQuickAction);
		skipButton.setEnabled(false);
		if (!isAvailableMainAction && isAvailableQuickAction) {
			skipButton.setEnabled(true);
		}
		enableRegionButtons(false);
	}
	/**
	 * Sets the visibility of the free councillor buttons. If is it true it will display the button else
	 * the button will be not enabled.
	 * @param display - the visibility of the free councillors buttons
	 */
	public void enableFreeCouncillorsButtons(boolean display) {
		freeCuncillorListener = display;
		for (Entry<String, Map<JLabel, JLabel>> entry : freeCouncillorsLabels.entrySet()) {
			Map<JLabel, JLabel> freeCouncillor = entry.getValue();
			for (Entry<JLabel, JLabel> jlabel : freeCouncillor.entrySet()) {
				jlabel.getKey().setEnabled(display);
			}
		}

	}
	/**
	 * Sets the visibility of politic cards label. If is it true it will display the labels else
	 * the labels will be not enabled.
	 * @param display - the visibility of the politic card labels
	 */
	public void enablePoliticCards(boolean display) {
		politicCardListener = display;
		for (JLabel jLabel : cardsList) {
			jLabel.setEnabled(display);
		}
	}
	/**
	 * Sets the visibility of the specific permit tile panel. If is it true it will display the panel else
	 * the panel will be not enabled
	 * @param chosenCouncil - the selected council
	 * @param display - the visibility of the permit tile panel
	 */
	public void enablePermitTilesPanel(String chosenCouncil, boolean display) {
		permitTileListener = display;
		Map<JLabel, List<JLabel>> permitTilesLabels = permitTiles.get(chosenCouncil);
		for (JLabel jLabel : permitTilesLabels.keySet()) {
			jLabel.setEnabled(display);
		}
	}
	/**
	 * Sets the visibility of the cities. If is it true it will display the cities else the cities will be not
	 * enabled
	 * @param display - the visibility of cities
	 */
	public void enableCities(boolean display) {
		cityListener = display;
		Set<Entry<String, JLabel>> cityLabelsSet = cityLabels.entrySet();
		for (Entry<String, JLabel> cityLabel : cityLabelsSet) {
			cityLabel.getValue().setEnabled(display);
		}
	}
	/**
	 * Sets the visibility of the region buttons. If is it true it will display the region buttons else
	 * the region buttons will be not enabled
	 * @param display - the visibility of region buttons
	 */
	public void enableRegionButtons(boolean display) {
		for (JButton regionButton : regionsButtons) {
			regionButton.setEnabled(display);
		}
	}
	/**
	 * Sets the visibility of the permit tile deck. If is it true it will display the permit tile deck else
	 * the permit tile deck will be not displayed.
	 * @param display - the visibility of permit deck
	 */
	public void enablePermitTileDeck(boolean display) {
		permitTileListener = true;
		for (JLabel jLabel : playerPermitTiles.keySet()) {
			jLabel.setEnabled(display);
		}
	}

	protected void clearChosenRegion() {
		buttons.setChosenRegion(new String());
	}
	/**
	 * Refresh all parameters to the initial state during an action when an exception occurs. 
	 */
	public void clearSwingUI() {
		chosenCard = null;
		finish = false;
		clearChosenRegion();
		Set<Entry<String, Map<JLabel, List<JLabel>>>> allPermitTilesEntries = permitTiles.entrySet();
		for (Entry<String, Map<JLabel, List<JLabel>>> permitTilesEntry : allPermitTilesEntries) {
			Map<JLabel, List<JLabel>> permitTilesLabel = permitTilesEntry.getValue();
			for (JLabel label : permitTilesLabel.keySet()) {
				label.setEnabled(false);
			}
		}
	}
	/**
	 * Controls if "finish" button has been pushed.
	 * @return true if it has been pushed, false otherwise
	 */
	public boolean hasFinished() {
		return finish;
	}
	/**
	 * Loads the area used by the user when the game enter in market phase.
	 */
	public void loadMarketInputArea() {
		marketSendButton.addActionListener(e -> {
			spinnerValue = (int) marketSpinner.getValue();
			guiView.resume();
		});
	}
	/**
	 * Appends a string to the text displayed on the console.
	 * @param string - the string to append
	 */
	public void appendConsoleText(String string) {
		guiLoad.appendText(string);
	}
	/**
	 * Set the visibility of the market input area. If is it true it will display the input area else
	 * the market input area will be not displayed.
	 * @param display - the visibility of market input area
	 */
	public void enableMarketInputArea(boolean display) {
		marketSpinner.setVisible(display);
		marketSpinner.setEnabled(display);
		marketSendButton.setVisible(display);
		marketSendButton.setEnabled(display);
	}
	/**
	 * Sets the visibility of finish button. If is it true it will display the button else
	 * the finish button will be not enabled.
	 * @param display
	 */
	public void enableFinish(boolean display) {
		finished.setEnabled(display);
	}

	private void refreshAcquiredPermitTiles(List<String> playersName, List<List<List<String>>> permitTilesCities,
			List<List<List<String>>> permitTilesBonusesName, List<List<List<String>>> permitTilesBonusesValue) {
		Set<Entry<JLabel, List<JLabel>>> playerPermitTilesSet = playerPermitTiles.entrySet();
		for (Entry<JLabel, List<JLabel>> playerPermitTile : playerPermitTilesSet) {
			mapPanel.remove(playerPermitTile.getKey());
			for (JLabel jLabel : playerPermitTile.getValue()) {
				mapPanel.remove(jLabel);
			}
		}
		playerPermitTiles.clear();
		
		int x = 0;
		int y = 611;
		int playerIndex = playersName.indexOf(playerName);
		int increment = 52;
		drawPermitTiles(mapPanel, playerPermitTiles, permitTilesCities.get(playerIndex), permitTilesBonusesName.get(playerIndex),
					permitTilesBonusesValue.get(playerIndex), new Point(x, y), increment);
	}
	
	private void refreshOtherPlayersStatusDialog(List<String> playersName, List<List<List<String>>> permitTilesCities,
			List<List<List<String>>> permitTilesBonusesName, List<List<List<String>>> permitTilesBonusesValue) {
	
		for (Entry<JLabel, Map<JLabel, List<JLabel>>> playerPermitTile : otherPlayersPermitTiles.entrySet()) {
			otherPlayersDialog.remove(playerPermitTile.getKey());
			for(Entry<JLabel, List<JLabel>> permitLabel  : playerPermitTile.getValue().entrySet()) {
				otherPlayersDialog.remove(permitLabel.getKey());
				for(JLabel jLabel : permitLabel.getValue()) {
					otherPlayersDialog.remove(jLabel);
				}
			}
		}
		otherPlayersPermitTiles.clear();

		int x = 0;
		int y = 30;
		int currentPlayerIndex = playersName.indexOf(playerName);
		int increment = 52;
		int i = 0;
		for (String playerNameString : playersName) {
			if(i != currentPlayerIndex) {
				JLabel playerNameLabel = new JLabel();
				playerNameLabel.setText(playerNameString);
				playerNameLabel.setBounds(0, 0, 100, 25);
				playerNameLabel.setLocation(x, y - 20);
				playerNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
				playerNameLabel.setForeground(Color.black);
				otherPlayersDialog.add(playerNameLabel,0);
				Map<JLabel, List<JLabel>> otherPlayerPermitTilesMap = new HashMap<>();
				drawPermitTiles(otherPlayersDialog, otherPlayerPermitTilesMap, permitTilesCities.get(i), permitTilesBonusesName.get(i),
							permitTilesBonusesValue.get(i), new Point(x, y), increment);
				otherPlayersPermitTiles.put(playerNameLabel, otherPlayerPermitTilesMap);
				y += 110;
			}
			i++;
		}
		otherPlayersDialog.repaint();
		otherPlayersDialog.revalidate();
	}

	protected void refreshGamePlayersPermitTiles(List<String> playersName, List<List<List<String>>> permitTilesCities,
		List<List<List<String>>> permitTilesBonusesName, List<List<List<String>>> permitTilesBonusesValue) {
		refreshAcquiredPermitTiles(playersName, permitTilesCities, permitTilesBonusesName, permitTilesBonusesValue);
		refreshOtherPlayersStatusDialog(playersName, permitTilesCities, permitTilesBonusesName, permitTilesBonusesValue);
	}
	
	protected void refreshAllPermitTiles(List<String> playersName, List<List<List<String>>> permitTilesCities,
			List<List<List<String>>> permitTilesBonusesName, List<List<List<String>>> permitTilesBonusesValue) {
		Set<Entry<JLabel, List<JLabel>>> playerAllPermitTilesSet = playerAllPermitTiles.entrySet();
		for (Entry<JLabel, List<JLabel>> playerPermitTile : playerAllPermitTilesSet) {
			totalPermitsCardDialog.remove(playerPermitTile.getKey());
			for (JLabel jLabel : playerPermitTile.getValue()) {
				totalPermitsCardDialog.remove(jLabel);
			}
		}
		playerAllPermitTiles.clear();
		int x = 0;
		int y = 0;
		int playerIndex = playersName.indexOf(playerName);
		drawPermitTiles(totalPermitsCardDialog, playerAllPermitTiles, permitTilesCities.get(playerIndex), permitTilesBonusesName.get(playerIndex), permitTilesBonusesValue.get(playerIndex), new Point(x, y), 52);
	}
	/**
	 * Sets the visibility of the total permit tile deck. If is it true it will display the total 
	 * permit tile deck else the total permit tile deck will be not displayed.
	 * @param display - the visibility of total permit deck
	 */
	public void enableTotalHandDeck(boolean display) {
		permitTileListener = display;
		for (JLabel jLabel : playerAllPermitTiles.keySet()) {
			jLabel.setEnabled(display);
		}
		totalPermitsCardDialog.setVisible(display);
	}	
	
}