package it.polimi.ingsw.ps23.client;

import java.awt.Color;
import java.awt.Component;
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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import it.polimi.ingsw.ps23.client.rmi.RMIGUIView;

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
	private static final String SANS_SERIF_FONT = "Sans serif";
	
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
	private RMIGUIView rmiGUIView;
	private String chosenAction;
	private String chosenRegion;
	private String chosenCity;
	private List<JButton> regionsButtons;
	private GUILoad guiLoad;
	private JPanel mainActionPanel;
	private JPanel quickActionPanel;
	private Map<String, JLabel> cityLabels;
	private Map<String, Point> councilPoints;
	private JFrame frame;
	private JPanel mapPanel;
	private DefaultTableModel tableModel;

	protected SwingUI(String mapType, String playerName) {
		this.playerName = playerName;
		regionsButtons = new ArrayList<>();
		mapPath = CONFIGURATION_PATH + mapType + "/";
		guiLoad = new GUILoad(mapPath);
		cityLabels = guiLoad.getCityLabels();
		loadCitiesButtons();
		councilPoints = guiLoad.getCouncilPoints();
		frame = guiLoad.getFrame();
		mapPanel = guiLoad.getMapPanel();
		tableModel = guiLoad.getTableModel();
		loadRegionButtons();
		loadMainActionPanel();
		loadQuickActionPanel();
	}
	
	protected void setRMIGUIView(RMIGUIView rmiGUIView) {
		this.rmiGUIView = rmiGUIView;
	}
	
	protected RMIGUIView getRMIGUIView() {
		return rmiGUIView;
	}

	protected Map<String, Point> getCouncilPoints() {
		return councilPoints;
	}

	protected JFrame getFrame() {
		return frame;
	}

	protected JPanel getMapPanel() {
		return mapPanel;
	}

	protected DefaultTableModel getTableModel() {
		return tableModel;
	}

	protected String getPlayerName() {
		return playerName;
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
	
	protected JPanel getMainActionPanel() {
		return mainActionPanel;
	}
	
	protected JPanel getQuickActionPanel() {
		return quickActionPanel;
	}

	protected static String getPermitTilePath() {
		return PERMIT_TILE_PATH;
	}

	protected static String getSansSerifFont() {
		return SANS_SERIF_FONT;
	}

	protected static String getPoliticCardPath() {
		return POLITIC_CARD_PATH;
	}

	protected static String getImagesPath() {
		return IMAGES_PATH;
	}
	
	protected static String getPngExtension() {
		return PNG_EXTENSION;
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

	protected Point getCouncilPoint(String region) {
		return councilPoints.get(region);
	}

	protected void enableRegionButtons(boolean display) {
		for (JButton regionButton : regionsButtons) {
			regionButton.setEnabled(display);
		}
	}
	
	protected void clearChosenRegion() {
		chosenRegion = null;
	}

	protected void drawBonus(String bonusName, String bonusValue, int x, int y, int width, int height, int yOffset) {
		BufferedImage bonusImage = guiLoad.readImage(IMAGES_PATH + bonusName + PNG_EXTENSION);
		Image resizedBonusImage = bonusImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		JLabel bonusLabel = new JLabel(new ImageIcon(resizedBonusImage));
		bonusLabel.setBounds(0, 0, width, height);
		bonusLabel.setLocation(x, y + yOffset);
		getMapPanel().add(bonusLabel, 0);
		int bonusNumber = Integer.parseInt(bonusValue);
		if (bonusNumber > 1 || "victoryPoint".equals(bonusName)) {
			JLabel bonusLabelValue = new JLabel();
			bonusLabelValue.setBounds(0, 0, width, height);
			bonusLabelValue.setLocation(x + 8, y + yOffset);
			bonusLabelValue.setFont(new Font(SwingUI.getSansSerifFont(), Font.BOLD, 9));
			if ("coin".equals(bonusName)) {
				bonusLabelValue.setForeground(Color.black);
			} else {
				bonusLabelValue.setForeground(Color.white);
			}
			bonusLabelValue.setText(String.valueOf(bonusNumber));
			getMapPanel().add(bonusLabelValue, 0);
		}
	}

	protected void addRewardTokens(List<String> citiesName, List<List<String>> citiesBonusesName, List<List<String>> citiesBonusesValue) {
		for (int i = 0; i < citiesName.size(); i++) {
			Component cityComponent = getCityLabel(citiesName.get(i));
			Point point = cityComponent.getLocationOnScreen();
			int x = point.x;
			int y = point.y;
			for (int j = 0; j < citiesBonusesName.get(i).size(); j++) {
				drawBonus(citiesBonusesName.get(i).get(j), citiesBonusesValue.get(i).get(j), x + 50, y - 20, 23, 25, 0);
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
					drawBonus(stepsBonusesName.get(i).get(j), stepsBonusesValue.get(i).get(j), x, y, width, height, yOffset);
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
		Point freeCouncillorsPoint = getCouncilPoints().get("free");
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

	private void drawCouncillor(String color, int x, int y) {
		BufferedImage councillorImage = guiLoad.readImage(IMAGES_PATH + color + COUNCILLOR_PATH);
		Image resizedCouncillorImage = councillorImage.getScaledInstance(14, 39, Image.SCALE_SMOOTH);
		JLabel councillorLabel = new JLabel(new ImageIcon(resizedCouncillorImage));
		councillorLabel.setBounds(0, 0, 15, 39);
		councillorLabel.setLocation(x, y);
		getMapPanel().add(councillorLabel, 0);
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

	protected void drawBonusTile(String groupName, String bonusName, String bonusValue) {
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
		getMapPanel().add(tileLabel, 0);
		drawBonus(bonusName, bonusValue, x + 25, y + 10, 23, 25, -5);
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
	
	protected void resumeRMIGUIView() {
		rmiGUIView.resume();
	}
	protected void loadMainActionPanel() {
		mainActionPanel = new JPanel();
		mainActionPanel.setBounds(895, 181, 215, 272);
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
            	rmiGUIView.resume();
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
            	rmiGUIView.resume();
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
            	rmiGUIView.resume();
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
            	rmiGUIView.resume();
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
		quickActionPanel.setBounds(1120, 181, 199, 272);
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
            	rmiGUIView.resume();
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
            	rmiGUIView.resume();
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
            	rmiGUIView.resume();
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
            	rmiGUIView.resume();
            }
        });
		BufferedImage additionalMainActionImage = guiLoad.readImage(IMAGES_PATH + "buyMainAction.png");
		btnAdditionalMainAction.setIcon(new ImageIcon(additionalMainActionImage));
		GridBagConstraints gbcbtnAdditionalMainAction = new GridBagConstraints();
		gbcbtnAdditionalMainAction.gridx = 0;
		gbcbtnAdditionalMainAction.gridy = 3;
		quickActionPanel.add(btnAdditionalMainAction, gbcbtnAdditionalMainAction);
	}
	
	private void loadRegionButtons() {
		BufferedImage seasideImage = guiLoad.readImage(IMAGES_PATH + "seasideRegion.png");
		JButton btnSeaside = new JButton("");
		btnSeaside.addActionListener(new ActionListener() {
			@Override 
            public void actionPerformed(ActionEvent e)
            {
            	chosenRegion = "seaside";
            	rmiGUIView.resume();
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
            	rmiGUIView.resume();
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
            	rmiGUIView.resume();
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
			cityLabel.getValue().addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
					chosenCity = cityLabel.getKey();
					cityLabel.getValue().setEnabled(false);
	            	getRMIGUIView().resume();
                }
        });   
		}
		
	}
	
	protected void enableCitiesButtons(boolean display) {
		Set<Entry<String, JLabel>> cityLabelsSet = cityLabels.entrySet();
		for (Entry<String, JLabel> cityLabel : cityLabelsSet){
			cityLabel.getValue().setEnabled(display);
		}
	}

	
}