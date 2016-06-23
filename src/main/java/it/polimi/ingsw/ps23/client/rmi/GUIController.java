package it.polimi.ingsw.ps23.client.rmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardToken;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.state.StartTurnState;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIController {

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

	private static StartTurnState currentState;
	private static GUIController self = null;
	private static Stage stage;

	public GUIController() {
		self = this;
	}

	private void placeRewardToken(Group group, List<ImageView> citiesImagesList) {
		Map<String, City> citiesMap = GUIController.currentState.getGameMap().getCitiesMap();
		for (ImageView imageView : citiesImagesList) {
			City city = citiesMap.get(imageView.getId().toString());
			if (city instanceof NormalCity) {
				RewardToken rewardToken = ((NormalCity) city).getRewardToken();
				Text text = new Text();
				text.setText(rewardToken.toString());
				text.setX(imageView.getLayoutX() + 5.00);
				text.setY(imageView.getLayoutY());
				text.toFront();
				text.setFont(Font.font("Verdana", 12));
				text.setFill(Color.MAGENTA);
				group.getChildren().add(text);
			}

		}

	}

	private void setPlayerSet() {
		Platform.setImplicitExit(false);
		Scene scene = stage.getScene();
		ObservableList<Player> playersList = FXCollections.observableArrayList();
		playersList.addAll(GUIController.currentState.getPlayerSet().getPlayers());
		TableView<Player> playerTable = (TableView<Player>) scene.lookup("#players");
		TableColumn<Player, String> playersName = ((TableColumn<Player, String>) playerTable.getColumns().get(0));
		playersName.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
		TableColumn<Player, String> victoryPoints = ((TableColumn<Player, String>) playerTable.getColumns().get(1));
		victoryPoints.setCellValueFactory(new PropertyValueFactory<Player, String>("victoryPoints"));
		TableColumn<Player, String> coins = ((TableColumn<Player, String>) playerTable.getColumns().get(2));
		coins.setCellValueFactory(new PropertyValueFactory<Player, String>("coins"));
		TableColumn<Player, String> assistants = ((TableColumn<Player, String>) playerTable.getColumns().get(3));
		assistants.setCellValueFactory(new PropertyValueFactory<Player, String>("assistants"));
		TableColumn<Player, String> nobilityPoints = ((TableColumn<Player, String>) playerTable.getColumns().get(4));
		nobilityPoints.setCellValueFactory(new PropertyValueFactory<Player, String>("nobilityTrackPoints"));
		playerTable.setItems(playersList);
	}

	private void placeKing(ImageView king, List<ImageView> citiesImagesList) {
		for (ImageView imageView : citiesImagesList) {
			if (imageView.getId().toString().equals(GUIController.currentState.getKing().getPosition().getName())) {
				king.setX(imageView.getLayoutX());
				king.setY(imageView.getLayoutY());
			}
		}
	}

	@FXML
	public void initialize() {
		self = this;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void updateGUI(StartTurnState state) {
		GUIController.currentState = state;
		//Platform.setImplicitExit(false);
		javafx.application.Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (self != null) {
					self.updateMap();
				}
			}
		});

	}

	private void updateMap() {
		Scene scene = stage.getScene();
		List<ImageView> citiesImagesList = new ArrayList<>();
		citiesImagesList.add((ImageView) scene.lookup("#A"));
		citiesImagesList.add((ImageView) scene.lookup("#B"));
		citiesImagesList.add((ImageView) scene.lookup("#C"));
		citiesImagesList.add((ImageView) scene.lookup("#D"));
		citiesImagesList.add((ImageView) scene.lookup("#E"));
		citiesImagesList.add((ImageView) scene.lookup("#F"));
		citiesImagesList.add((ImageView) scene.lookup("#G"));
		citiesImagesList.add((ImageView) scene.lookup("#H"));
		citiesImagesList.add((ImageView) scene.lookup("#I"));
		citiesImagesList.add((ImageView) scene.lookup("#J"));
		citiesImagesList.add((ImageView) scene.lookup("#K"));
		citiesImagesList.add((ImageView) scene.lookup("#L"));
		citiesImagesList.add((ImageView) scene.lookup("#M"));
		citiesImagesList.add((ImageView) scene.lookup("#N"));
		citiesImagesList.add((ImageView) scene.lookup("#O"));
		ImageView king = (ImageView) scene.lookup("#king");
		Group group = (Group) scene.lookup("#group");
		setPlayerSet();
		placeKing(king, citiesImagesList);
		// placeRewardToken(group, citiesImagesList);
	}

	public void doUpdateGUI(StartTurnState currentState) {
		updateGUI(currentState);
		;
	}
}
