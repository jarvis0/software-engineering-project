package it.polimi.ingsw.ps23.client.rmi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.common.base.Service.State;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.map.GameMap;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardToken;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SimpleController implements Initializable {
	
	@FXML
	ArrayList<ImageView> citiesImagesList;
	@FXML
	Group group;
	@FXML
	TableView<Player> players;
	@FXML
	TableColumn<Player, String> playerName;
	@FXML
	TableColumn<Player, String> victoryPoints;
	@FXML
	TableColumn<Player, String> coins;
	@FXML
	TableColumn<Player, String> assistants;
	@FXML
	TableColumn<Player, String> nobilityPoints;
	@FXML
	ImageView A;
	@FXML
	ImageView B;
	@FXML
	ImageView C;
	@FXML
	ImageView D;
	@FXML
	ImageView E;
	@FXML
	ImageView F;
	@FXML
	ImageView G;
	@FXML
	ImageView H;
	@FXML
	ImageView I;
	@FXML
	ImageView J;
	@FXML
	ImageView K;
	@FXML
	ImageView L;
	@FXML
	ImageView M;
	@FXML
	ImageView N;
	@FXML
	ImageView O;
	@FXML
	ImageView king;
	
	public void changeValue(GameMap gameMap, PlayersSet playersSet) {
		placeRewardToken(gameMap.getCitiesMap());
		setPlayerSet(playersSet);
	}
	
	private void placeRewardToken(Map<String, City> map) {
		for (ImageView imageView : citiesImagesList) {
			City city = map.get(imageView.getId().toString());
			if(city instanceof NormalCity) {
				RewardToken rewardToken = ((NormalCity)city).getRewardToken();
				Text text = new Text();
				group.getChildren().add(text);
				text.setText(rewardToken.toString());
				text.setX(imageView.getLayoutX()+5.00);
				text.setY(imageView.getLayoutY());
				text.toFront();
				text.setFont(Font.font ("Verdana", 12));
				text.setFill(Color.MAGENTA);
			}
			
		}
		
	}
	
	private void setPlayerSet(PlayersSet playersSet) {
		ObservableList<Player> playersList = FXCollections.observableArrayList();
		playersList.addAll(playersSet.getPlayers());
		playerName.setCellValueFactory(new PropertyValueFactory<Player,String>("name"));  
		coins.setCellValueFactory(new PropertyValueFactory<Player,String>("coins"));  
		assistants.setCellValueFactory(new PropertyValueFactory<Player,String>("assistants"));  
		nobilityPoints.setCellValueFactory(new PropertyValueFactory<Player,String>("nobilityTrackPoints"));  
		victoryPoints.setCellValueFactory(new PropertyValueFactory<Player,String>("victoryPoints"));
		players.setItems(playersList);
	}
	
	private void placeKing(City city) {
		for (ImageView imageView : citiesImagesList) {
			if(imageView.getId().toString().equals(city.getName())) {
				king.setX(imageView.getLayoutX());
				king.setY(imageView.getLayoutY());
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}	

