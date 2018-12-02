package LeaderboardPage;

import GameApplication.Main;
import HomePage.HomePageController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class LeaderboardPageController implements Initializable
{

	@FXML public TableView<LeaderboardEntry> leaderboard;
	@FXML public TableColumn<LeaderboardEntry, Integer> score;
	@FXML public TableColumn<LeaderboardEntry, String> date;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		score.setCellValueFactory(new PropertyValueFactory<>("score"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
	}

	public void setUpLeaderboardPage() throws IOException, ClassNotFoundException
	{
		//System.out.println("Leaderboard coming up !");
		//System.out.println("In LP entries present = " + Main.areEntriesPresent());

		if(Main.areEntriesPresent())
		{
			ObservableList<LeaderboardEntry> scores = Main.deserializeLeaderboard();
			leaderboard.setItems(scores);
		}
		//System.out.println("LB setted up");
	}

	public void homeButtonPressed() throws IOException, ClassNotFoundException
	{
		Main.homePageController.setUpHomePage();
		Main.mainStage.setScene(Main.homePageScene);
	}
}
