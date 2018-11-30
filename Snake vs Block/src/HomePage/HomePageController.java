package HomePage;

import GameApplication.Main;
import GamePage.GamePageController;
import GamePage.GameState;
import LeaderboardPage.LeaderboardEntry;
import LeaderboardPage.LeaderboardPageController;
import PopupBoxes.ConfirmBox;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomePageController
{
	public Button resumegame;

	public void setUpHomePage()
	{
		System.out.println("HomePage coming up !");
		System.out.println("In HP Last Game Saved = " + Main.isLastGameSaved());
		if(Main.isLastGameSaved())
			resumegame.setDisable(true);
	}

	public void startGameButtonPressed() throws IOException, ClassNotFoundException
	{
		if(Main.isLastGameSaved())
		{
			GamePageController lastSavedGame = Main.deserializeLastGame();
			LeaderboardEntry entry = new LeaderboardEntry(lastSavedGame.getScore(), lastSavedGame.getDate());
			Main.updateLeaderBoard(entry);
		}
		Main.gamePageController.setUpGamePage(null);
		Main.mainStage.setScene(Main.gamePageScene);
	}

	public void resumeGameButtonPressed() throws IOException, ClassNotFoundException
	{
		Main.gamePageController.setUpGamePage(Main.deserializeLastGame());
		Main.mainStage.setScene(Main.gamePageScene);
	}

	public void showLeaderboardButtonPressed() throws IOException
	{
		Main.leaderboardPageController.setUpLeaderboardPage();
		Main.mainStage.setScene(Main.leaderboardPageScene);
	}

	public void exitButtonPressed()
	{
		Main.closeApplication();
	}

}
