package LeaderboardPage;

import GameApplication.Main;
import HomePage.HomePageController;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class LeaderboardPageController
{

	public TableView<LeaderboardEntry> leaderboard;

	public void setUpLeaderboardPage()
	{
		System.out.println("Leaderboard coming up !");

		try
		{
			System.out.println("In LP Last Game Saved = " + Main.isLastGameSaved());
			if(Main.isLastGameSaved())
			{
				ObservableList<LeaderboardEntry> scores = Main.deserializeLeaderboard();
				Collections.sort(scores);
				leaderboard.setItems(scores);
			}
		}
		catch (IOException e){}
		catch (ClassNotFoundException e){}
	}

	public void homeButtonPressed()
	{
		Main.homePageController.setUpHomePage();
		Main.mainStage.setScene(Main.homePageScene);
	}
}
