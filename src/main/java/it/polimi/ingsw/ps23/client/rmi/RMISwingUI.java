package it.polimi.ingsw.ps23.client.rmi;

import java.awt.Color;
import java.awt.Cursor;
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
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.BusinessPermitTile;
import it.polimi.ingsw.ps23.server.model.player.HandDeck;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;

class RMISwingUI extends SwingUI {

	private String chosenCard;
	private int chosenTile;
	private Map<String, List<JLabel>> permitTiles;
	private List<JLabel> playerPermitTiles;
	private List<JLabel> cardsList;
	private boolean finish;
	JButton finished;
	
	RMISwingUI(String mapType, String playerName) {
		super(mapType, playerName);
		cardsList = new ArrayList<>();
		playerPermitTiles = new ArrayList<>();
		permitTiles = new HashMap<>();
		finish = false;
	}

	public boolean hasFinished() {
		return finish;
	}

	String getChosenCard() {
		return chosenCard;
	}
	
	int getChosenTile() {
		return chosenTile;
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

	private void refreshRegionalPermitTiles(List<Region> regions) {
		for (Region region : regions) {
			Point point = getCouncilPoint(region.getName());
			int x = point.x - 120;
			int y = point.y - 12;
			drawPermitTiles(((GroupRegionalCity) region).getPermitTilesUp(), x, y, region.getName());
		}
	}
	
	private void drawPermitTiles(Deck permitTilesUp, int xCoord, int yCoord, String region) {
		List<Card> permitTiles = permitTilesUp.getCards();
		int x = xCoord;
		int y = yCoord;
		int indexOfTile = 0;
		List<JLabel> permitLabels = new ArrayList<>();
		for (Card permissionCard : permitTiles) {
			drawPermitCard(permissionCard, indexOfTile, permitLabels, x, y );
			this.permitTiles.put(region, permitLabels);
			x -= 52;
			indexOfTile++;
		}
	}
	
	private void drawPermitCard(Card permitTile, int indexOfTile, List<JLabel> permitLabels, int x, int y) {
		BufferedImage permissionTileImage = readImage(getImagesPath() + getPermitTilePath());
		Image resizedPermissionTile = permissionTileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		JLabel permissionTileLabel = new JLabel(new ImageIcon(resizedPermissionTile));
		permissionTileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		permitLabels.add(permissionTileLabel);
		permissionTileLabel.setBounds(0, 0, 50, 50);
		permissionTileLabel.setLocation(x, y);
		permissionTileLabel.addMouseListener(new MouseAdapter() {
				@Override
	            public void mouseClicked(MouseEvent e) {
					chosenTile = indexOfTile;
					permissionTileLabel.setEnabled(false);
	            	getRMIGUIView().resume();
	            }
			});
		
		getMapPanel().add(permissionTileLabel, 0);
		permissionTileLabel.setEnabled(false);
		List<Bonus> bonuses = ((BusinessPermitTile) permitTile).getBonuses();
		int bonusCoordX = x - 47;
		int bonusCoordY = y + 40;
		for (Bonus bonus : bonuses) {
			drawBonus(bonus.getName(), String.valueOf(bonus.getValue()), bonusCoordX + 50, bonusCoordY - 20, 23, 25, 0);
			bonusCoordX = bonusCoordX + 24;
		}
		List<City> cities = ((BusinessPermitTile) permitTile).getCities();
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
	}

	private void refreshPoliticCards(HandDeck politicHandDeck) {
		int x = 0;
		int y = 535;
		for(JLabel card : cardsList) {
			getMapPanel().remove(card);
		}
		
		if(finished != null){
			getMapPanel().remove(finished);
		}
		
		for (Card card : ((PoliticHandDeck) politicHandDeck).getCards()) {
			BufferedImage cardImage = readImage(SwingUI.getImagesPath() + card.toString() + SwingUI.getPoliticCardPath());
			Image resizedCardImage = cardImage.getScaledInstance(42, 66, Image.SCALE_SMOOTH);
			JLabel cardLabel = new JLabel(new ImageIcon(resizedCardImage));
			cardLabel.setDisabledIcon(new ImageIcon(resizedCardImage));
			cardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			cardsList.add(cardLabel);
			cardLabel.addMouseListener(new MouseAdapter() {
					@Override
	                public void mouseClicked(MouseEvent e) {
						chosenCard = card.toString();
						cardLabel.setEnabled(false);
		            	getRMIGUIView().resume();
	                }
	        });      
			cardLabel.setBounds(0, 0, 42, 66);
			cardLabel.setLocation(x, y);
			x += 44;
			getMapPanel().add(cardLabel, 0);
			cardLabel.setEnabled(false);
		}
		finished = new JButton("finished");
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

	private void freeCouncillorsToStrings(List<Councillor> freeCouncillors, List<String> freeCouncillorsColor) {
		for(int i = 0; i < freeCouncillors.size(); i++) {
			freeCouncillorsColor.add(freeCouncillors.get(i).getColor().toString());
		}
	}

	private void councilsToStrings(List<Region> regions, Queue<Councillor> kingCouncillors, List<String> councilsName, List<List<String>> councilsColor) {
		for(int i = 0; i < regions.size(); i++) {
			GroupRegionalCity region = (GroupRegionalCity) regions.get(i);
			councilsName.add(region.getName());
			Queue<Councillor> councillors = region.getCouncil().getCouncillors();
			List<String> council = new ArrayList<>();
			for(Councillor councillor : councillors) {
				council.add(councillor.getColor().toString());
			}
			councilsColor.add(council);
		}
		List<String> council = new ArrayList<>();
		councilsName.add(getKingdom());
		for(Councillor councillor : kingCouncillors) {
			council.add(councillor.getColor().toString());
		}
		councilsColor.add(council);
	}

	private void bonusTilesToStrings(List<Region> regions, List<String> groupsName, List<String> bonusesName, List<String> bonusesValue) {
		for(Region region : regions) {
			if(!region.alreadyUsedBonusTile()) {
				groupsName.add(region.getName());
			}
			else {
				groupsName.add(getAlreadyAcquiredBonusTile());
			}
			Bonus bonus = region.getBonusTile();
			bonusesName.add(bonus.getName());
			bonusesValue.add(String.valueOf(bonus.getValue()));
		}
	}
	
	void refreshDynamicContents(StartTurnState currentState) {
		int playerIndex = searchPlayer(currentState.getPlayersList());
		
		refreshKingPosition(currentState.getKingPosition());
		
		List<String> freeCouncillorsColor = new ArrayList<>();
		freeCouncillorsToStrings(currentState.getFreeCouncillors(), freeCouncillorsColor);
		refreshFreeCouncillors(freeCouncillorsColor);

		List<String> councilsName = new ArrayList<>();
		List<List<String>> councilsColor = new ArrayList<>();
		councilsToStrings(currentState.getGroupRegionalCity(), currentState.getKingCouncil().getCouncillors(), councilsName, councilsColor);
		refreshCouncils(councilsName, councilsColor);
		refreshRegionalPermitTiles(currentState.getGroupRegionalCity());//TODO
		List<String> groupsName = new ArrayList<>();
		List<String> bonusesName = new ArrayList<>();
		List<String> bonusesValue = new ArrayList<>();
		bonusTilesToStrings(currentState.getGroupRegionalCity(), groupsName, bonusesName, bonusesValue);
		List<String> coloredGroupsName = new ArrayList<>();
		List<String> coloredBonusesName = new ArrayList<>();
		List<String> coloredBonusesValue = new ArrayList<>();
		bonusTilesToStrings(currentState.getGroupColoredCity(), coloredGroupsName, coloredBonusesName, coloredBonusesValue);
		Bonus kingTile = currentState.getCurrentKingTile();
		String kingBonusName;
		String kingBonusValue;
		if(kingTile != null) {
			kingBonusName = kingTile.getName();
			kingBonusValue = String.valueOf(kingTile.getValue());
		}
		else {
			kingBonusName = getNoKingTile();
			kingBonusValue = String.valueOf(0);
		}
		groupsName.addAll(coloredGroupsName);
		bonusesName.addAll(coloredBonusesName);
		bonusesValue.addAll(coloredBonusesValue);
		refreshBonusTiles(groupsName, bonusesName, bonusesValue, kingBonusName, kingBonusValue);
		
		//TODO
		refreshPlayersTable(currentState.getPlayersList());
		refreshAcquiredPermitTiles((currentState.getPlayersList().get(playerIndex)).getPermitHandDeck(), (currentState.getPlayersList().get(playerIndex)).getPermitUsedHandDeck());
		refreshPoliticCards((currentState.getPlayersList().get(playerIndex)).getPoliticHandDeck());
		getFrame().repaint();
		getFrame().revalidate();
	}

	private void bonusesToStrings(List<Bonus> bonuses, List<String> bonusesName, List<String> bonusesValue) {
		for(int i = 0; i < bonuses.size(); i++)  {
			bonusesName.add(bonuses.get(i).getName());
			bonusesValue.add(String.valueOf(bonuses.get(i).getValue()));
		}
	}
	
	private void rewardTokensToStrings(Map<String, City> cityMap, List<String> citiesName, List<List<String>> citiesBonusesName, List<List<String>> citiesBonusesValue) {
		Set<Entry<String, City>> citiesEntries = cityMap.entrySet();
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
	}

	private void nobilityTrackToStrings(List<NobilityTrackStep> steps, List<List<String>> stepsBonusesName, List<List<String>> stepsBonusesValue) {
		for(NobilityTrackStep step : steps) {
			List<Bonus> bonuses = step.getBonuses();		
			List<String> bonusesName = new ArrayList<>();
			List<String> bonusesValue = new ArrayList<>();
			bonusesToStrings(bonuses, bonusesName, bonusesValue);
			stepsBonusesName.add(bonusesName);
			stepsBonusesValue.add(bonusesValue);
		}
	}

	void loadStaticContents(StartTurnState currentState) {
		List<String> citiesName = new ArrayList<>();
		List<List<String>> citiesBonusesName = new ArrayList<>();
		List<List<String>> citiesBonusesValue = new ArrayList<>();
		rewardTokensToStrings(currentState.getGameMap().getCities(), citiesName, citiesBonusesName, citiesBonusesValue);
		addRewardTokens(citiesName, citiesBonusesName, citiesBonusesValue);
		
		List<List<String>> stepsBonusesName = new ArrayList<>();
		List<List<String>> stepsBonusesValue = new ArrayList<>();
		nobilityTrackToStrings(currentState.getNobilityTrack().getSteps(), stepsBonusesName, stepsBonusesValue);
		addNobilityTrackBonuses(stepsBonusesName, stepsBonusesValue);
	}
	
	private void refreshAcquiredPermitTiles(HandDeck permissionHandDeck, HandDeck permissionUsedHandDeck) {
		/*for(JLabel permitTile : playerPermitTiles) {
			getMapPanel().remove(permitTile);
		}*/
		List<Card> permitHandDeckList = permissionHandDeck.getCards();
		int indexOfTile = 0; 
		int x = 0;
		int y = 611;
		for(Card permitTile : permitHandDeckList) {
			drawPermitCard(permitTile, indexOfTile, playerPermitTiles, x, y);
			x += 52;
		}
		
	}

	public void showAvailableActions(boolean isAvailableMainAction, boolean isAvailableQuickAction, RMIGUIView rmiguiView) {
		setRMIGUIView(rmiguiView);
		getMainActionPanel().setVisible(isAvailableMainAction);
		getQuickActionPanel().setVisible(isAvailableQuickAction);
		enableRegionButtons(false);
	}

	private void enableCards(boolean display) {
		for (JLabel jLabel : cardsList) {
			jLabel.setEnabled(display);
		}
	}
	
	public void enableButtons(boolean display) {
		enableRegionButtons(display);
	}

	public void enablePermissonTilePanel(String chosenCouncil) {
		List<JLabel> permitsLabel = permitTiles.get(chosenCouncil);	
		for (JLabel jLabel : permitsLabel) {
			jLabel.setEnabled(true);
		}
	}
	
	public void enablePermitTileDeck(boolean display) {
		for (JLabel jLabel : playerPermitTiles) {
			jLabel.setEnabled(display);
		}
	}

	public void enablePoliticCards(boolean display) {
		enableCards(display);
	}
	

	public void enableCities(boolean display) {
		enableCitiesButtons(display);
	}
	

	public void clearRMISwingUI() {
		chosenCard = null;
		finish = false;
		clearChosenRegion();
		Set<Entry<String, List<JLabel>>> allPermitTiles = permitTiles.entrySet();
		for (Entry<String, List<JLabel>> entry : allPermitTiles){
			for(JLabel permitTile : entry.getValue()) {
				permitTile.setEnabled(false);
			}
		}
				
	}

	public static void main(String[] args) {
		new RMISwingUI("hard", "ale");//TODO remove this method
	}


}
