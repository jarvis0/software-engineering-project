package it.polimi.ingsw.ps23.client.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class SwingDraw {

	private static final String IMAGES_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/images/";
	private static final String PNG_EXTENSION = ".png";
	
	private static final String COUNCILLOR_PATH = "Councillor.png";
	private static final String BONUS_TILE_PATH = "BonusTile.png";

	private static final String KINGDOM = "kingdom";
	
	private JPanel mapPanel;
	private GUILoad guiLoad;
	
	SwingDraw(JPanel mapPanel, GUILoad guiLoad) {
		this.mapPanel = mapPanel;
		this.guiLoad = guiLoad;
	}
	
	List<JLabel> drawBonus(Container container, String bonusName, String bonusValue, Point point, int width, int height, int yOffset) {
		int x = (int) point.getX();
		int y = (int) point.getY();
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

	private void drawNobilityTrackBonus(List<List<String>> stepsBonusesName, List<List<String>> stepsBonusesValue, int xParam, int yParam, int yOffsetParam, int i, int j) {
		int x = xParam;
		int y = yParam;
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
			drawBonus(mapPanel, stepsBonusesName.get(i).get(j), stepsBonusesValue.get(i).get(j), new Point(x, y), width, height, yOffsetParam);
		}
	}

	void addNobilityTrackBonuses(List<List<String>> stepsBonusesName, List<List<String>> stepsBonusesValue) {
		int stepNumber = 0;
		for (int i = 0; i < stepsBonusesName.size(); i++) {
			int yOffset = 0;
			int x = (int) 38.1 * stepNumber + 8;
			int y = 495;
			for (int j = 0; j < stepsBonusesName.get(i).size(); j++) {
				drawNobilityTrackBonus(stepsBonusesName, stepsBonusesValue, x, y, yOffset, i, j);
				yOffset -= 25;
			}
			stepNumber++;
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

	void drawCouncil(List<String> council, int xCoord, int yCoord) {
		int x = xCoord;
		int y = yCoord;
		for (String councillor : council) {
			x -= 16;
			drawCouncillor(councillor, x, y);
		}
	}
	
	void drawBonusTile(Map<String, Map<JLabel, List<JLabel>>> bonusTilePanels, String groupName, String bonusName, String bonusValue, Point regionCouncilPoint) {
		int x = regionCouncilPoint.x;
		int y = regionCouncilPoint.y;
		if (KINGDOM.equals(groupName)) {
			x -= 63;
			y -= 40;
		} else {
			x += 7;
			y -= 8;
		}
		BufferedImage tileImage = guiLoad.readImage(IMAGES_PATH + groupName + BONUS_TILE_PATH);
		Image resizedTileImage = tileImage.getScaledInstance(50, 35, Image.SCALE_SMOOTH);
		JLabel tileLabel = new JLabel(new ImageIcon(resizedTileImage));
		tileLabel.setBounds(0, 0, 50, 35);
		tileLabel.setLocation(x, y);
		mapPanel.add(tileLabel, 0);
		Map<JLabel, List<JLabel>> permiTilesMap = new HashMap<>();
		permiTilesMap.put(tileLabel, drawBonus(mapPanel, bonusName, bonusValue, new Point(x + 25, y + 10), 23, 25, -5));
		bonusTilePanels.put(groupName, permiTilesMap);
	}

}
