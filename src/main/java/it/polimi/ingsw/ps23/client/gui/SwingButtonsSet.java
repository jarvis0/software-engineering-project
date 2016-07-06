package it.polimi.ingsw.ps23.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

class SwingButtonsSet {
	
	private static final String IMAGES_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/images/";
	
	private static final String ELECT_COUNCILLOR = "elect councillor";
	private static final String ACQUIRE_BUSINESS_PERMIT_TILE = "acquire business permit tile";
	private static final String BUILD_EMPORIUM_KING = "build emporium king";
	private static final String BUILD_EMPORIUM_TILE = "build emporium permit tile";
	
	private static final String ASSISTANT_TO_ELECT_COUNCILLOR = "assistant to elect councillor";
	private static final String ADDITIONAL_MAIN_ACTION = "additional main action";
	private static final String ENGAGE_ASSITANT = "engage assistant";
	private static final String CHANGE_PERMIT_TILE = "change permit tile";
	private static final String SKIP = "Skip";
	
	private JPanel mapPanel;
	private GUILoad guiLoad;
	private GUIView guiView;
	
	private String chosenAction;
	private String chosenRegion;
	
	SwingButtonsSet(GUILoad guiLoad, GUIView guiView, JPanel mapPanel) {
		this.guiLoad = guiLoad;
		this.guiView = guiView;
		this.mapPanel = mapPanel;
	}
	
	String getChosenAction() {
		return chosenAction;
	}

	String getChosenRegion() {
		return chosenRegion;
	}
	
	public void setChosenRegion(String emptyChosenRegion) {
		this.chosenRegion = emptyChosenRegion;
	}


	void loadMainActionPanel(JPanel mainActionPanel) {
		mainActionPanel.setBounds(925, 145, 215, 272);
		mapPanel.add(mainActionPanel, 0);
		mainActionPanel.setVisible(false);
		GridBagLayout gblMainActionPanel = new GridBagLayout();
		gblMainActionPanel.columnWidths = new int[] { 0, 0 };
		gblMainActionPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gblMainActionPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gblMainActionPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainActionPanel.setLayout(gblMainActionPanel);
		JButton btnAcquireBusinessPermitTile = new JButton();
		btnAcquireBusinessPermitTile.addActionListener(e -> {
			chosenAction = ACQUIRE_BUSINESS_PERMIT_TILE;
			guiView.resume();
		});
		BufferedImage acquireBusinessPermitTileImage = guiLoad.readImage(IMAGES_PATH + "acquireBusinessPermitTile.png");
		btnAcquireBusinessPermitTile.setIcon(new ImageIcon(acquireBusinessPermitTileImage));
		GridBagConstraints gbcbtnAcquireBusinessPermitTile = new GridBagConstraints();
		gbcbtnAcquireBusinessPermitTile.insets = new Insets(0, 0, 5, 0);
		gbcbtnAcquireBusinessPermitTile.gridx = 0;
		gbcbtnAcquireBusinessPermitTile.gridy = 0;
		mainActionPanel.add(btnAcquireBusinessPermitTile, gbcbtnAcquireBusinessPermitTile);

		JButton btnBuildEmporiumKing = new JButton();
		btnBuildEmporiumKing.addActionListener(e -> {
			chosenAction = BUILD_EMPORIUM_KING;
			guiView.resume();
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
		btnElectCouncillor.addActionListener(e -> {
			chosenAction = ELECT_COUNCILLOR;
			guiView.resume();
		});
		BufferedImage electCouncillorImage = guiLoad.readImage(IMAGES_PATH + "electCouncillor.png");
		btnElectCouncillor.setIcon(new ImageIcon(electCouncillorImage));
		GridBagConstraints gbcbtnElectCouncillor = new GridBagConstraints();
		gbcbtnElectCouncillor.insets = new Insets(0, 0, 5, 0);
		gbcbtnElectCouncillor.gridx = 0;
		gbcbtnElectCouncillor.gridy = 2;
		mainActionPanel.add(btnElectCouncillor, gbcbtnElectCouncillor);

		JButton btnBuildEmporiumPermitTile = new JButton();
		btnBuildEmporiumPermitTile.addActionListener(e -> {
			chosenAction = BUILD_EMPORIUM_TILE;
			guiView.resume();
		});
		BufferedImage builEmporiumPermitTileImage = guiLoad.readImage(IMAGES_PATH + "buildEmporiumPermitTile.png");
		btnBuildEmporiumPermitTile.setIcon(new ImageIcon(builEmporiumPermitTileImage));
		GridBagConstraints gbcbtnBuildEmporiumPermitTile = new GridBagConstraints();
		gbcbtnBuildEmporiumPermitTile.gridx = 0;
		gbcbtnBuildEmporiumPermitTile.gridy = 3;
		mainActionPanel.add(btnBuildEmporiumPermitTile, gbcbtnBuildEmporiumPermitTile);
	}

	JButton loadQuickActionPanel(JPanel quickActionPanel) {

		quickActionPanel.setBounds(1150, 145, 199, 272);
		mapPanel.add(quickActionPanel, 0);
		quickActionPanel.setVisible(false);
		GridBagLayout gblQuickActionPanel = new GridBagLayout();
		gblQuickActionPanel.columnWidths = new int[] { 0, 0 };
		gblQuickActionPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gblQuickActionPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gblQuickActionPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		quickActionPanel.setLayout(gblQuickActionPanel);

		JButton btnEngageAssistant = new JButton();
		btnEngageAssistant.addActionListener(e -> {
			chosenAction = ENGAGE_ASSITANT;
			guiView.resume();
		});
		BufferedImage engageAssistantImage = guiLoad.readImage(IMAGES_PATH + "engageAssistant.png");
		btnEngageAssistant.setIcon(new ImageIcon(engageAssistantImage));
		GridBagConstraints gbcbtnEngageAssistant = new GridBagConstraints();
		gbcbtnEngageAssistant.insets = new Insets(0, 0, 5, 0);
		gbcbtnEngageAssistant.gridx = 0;
		gbcbtnEngageAssistant.gridy = 0;
		quickActionPanel.add(btnEngageAssistant, gbcbtnEngageAssistant);

		JButton btnChangePermitsTile = new JButton();
		btnChangePermitsTile.addActionListener(e -> {
			chosenAction = CHANGE_PERMIT_TILE;
			guiView.resume();
		});
		BufferedImage changePermitsTileImage = guiLoad.readImage(IMAGES_PATH + "changePermitsTile.png");
		btnChangePermitsTile.setIcon(new ImageIcon(changePermitsTileImage));
		GridBagConstraints gbcbtnChangePermitsTile = new GridBagConstraints();
		gbcbtnChangePermitsTile.insets = new Insets(0, 0, 5, 0);
		gbcbtnChangePermitsTile.gridx = 0;
		gbcbtnChangePermitsTile.gridy = 1;
		quickActionPanel.add(btnChangePermitsTile, gbcbtnChangePermitsTile);

		JButton btnAssistantToElectCouncillor = new JButton();
		btnAssistantToElectCouncillor.addActionListener(e -> {
			chosenAction = ASSISTANT_TO_ELECT_COUNCILLOR;
			guiView.resume();
		});
		BufferedImage assistantToElectCouncillorImage = guiLoad
				.readImage(IMAGES_PATH + "assistantToElectCouncillor.png");
		btnAssistantToElectCouncillor.setIcon(new ImageIcon(assistantToElectCouncillorImage));
		GridBagConstraints gbcbtnAssistantToElectCouncillor = new GridBagConstraints();
		gbcbtnAssistantToElectCouncillor.insets = new Insets(0, 0, 5, 0);
		gbcbtnAssistantToElectCouncillor.gridx = 0;
		gbcbtnAssistantToElectCouncillor.gridy = 2;
		quickActionPanel.add(btnAssistantToElectCouncillor, gbcbtnAssistantToElectCouncillor);

		JButton btnAdditionalMainAction = new JButton();
		btnAdditionalMainAction.addActionListener(e -> {
			chosenAction = ADDITIONAL_MAIN_ACTION;
			guiView.resume();
		});
		BufferedImage additionalMainActionImage = guiLoad.readImage(IMAGES_PATH + "buyMainAction.png");
		btnAdditionalMainAction.setIcon(new ImageIcon(additionalMainActionImage));
		GridBagConstraints gbcbtnAdditionalMainAction = new GridBagConstraints();
		gbcbtnAdditionalMainAction.gridx = 0;
		gbcbtnAdditionalMainAction.gridy = 3;
		quickActionPanel.add(btnAdditionalMainAction, gbcbtnAdditionalMainAction);
		JButton skipButton = new JButton(SKIP);
		skipButton.addActionListener(e -> {
			chosenAction = SKIP;
			guiView.resume();
		});
		skipButton.setEnabled(false);
		skipButton.setBounds(1283, 417, 66, 30);
		mapPanel.add(skipButton, 0);
		return skipButton;
	}

	void loadRegionButtons(List<JButton> regionsButtons, JButton btnKingdom) {
		BufferedImage kingImage = guiLoad.readImage(IMAGES_PATH + "kingIcon.png");
		btnKingdom.addActionListener(e -> {
			chosenRegion = "king";
			guiView.resume();
		});
		btnKingdom.setIcon(new ImageIcon(kingImage));
		btnKingdom.setBounds(865, 414, 50, 50);
		mapPanel.add(btnKingdom, 0);
		btnKingdom.setEnabled(false);
		BufferedImage seasideImage = guiLoad.readImage(IMAGES_PATH + "seasideRegion.png");
		JButton btnSeaside = new JButton();
		btnSeaside.addActionListener(e -> {
			chosenRegion = "seaside";
			guiView.resume();
		});
		btnSeaside.setIcon(new ImageIcon(seasideImage));
		btnSeaside.setBounds(120, 0, 50, 50);
		mapPanel.add(btnSeaside, 0);
		regionsButtons.add(btnSeaside);
		btnSeaside.setEnabled(false);
		BufferedImage hillImage = guiLoad.readImage(IMAGES_PATH + "hillRegion.png");
		JButton btnHill = new JButton();
		btnHill.addActionListener(e -> {
			chosenRegion = "hill";
			guiView.resume();
		});
		btnHill.setIcon(new ImageIcon(hillImage));
		btnHill.setBounds(370, 0, 50, 50);
		mapPanel.add(btnHill, 0);
		regionsButtons.add(btnHill);
		btnHill.setEnabled(false);
		BufferedImage mountainImage = guiLoad.readImage(IMAGES_PATH + "mountainRegion.png");
		JButton btnMountain = new JButton();
		btnMountain.addActionListener(e -> {
			chosenRegion = "mountain";
			guiView.resume();
		});
		btnMountain.setIcon(new ImageIcon(mountainImage));
		btnMountain.setBounds(670, 0, 50, 50);
		mapPanel.add(btnMountain, 0);
		regionsButtons.add(btnMountain);
		btnMountain.setEnabled(false);
	}

}
