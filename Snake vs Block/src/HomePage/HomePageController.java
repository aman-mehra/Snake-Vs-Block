package HomePage;

import GameApplication.Main;
import GamePage.GamePageController;
import GamePage.GameState;
import LeaderboardPage.LeaderboardEntry;
import javafx.scene.control.Button;

import java.io.IOException;


public class HomePageController
{
	public Button resumegame;

	public void setUpHomePage()
	{
		System.out.println("HomePage coming up !");
		System.out.println("In HP Last Game Saved = " + Main.isLastGameSaved());
		if(Main.isLastGameSaved())
			resumegame.setDisable(false);
	}

	public void startGameButtonPressed() throws IOException, ClassNotFoundException
	{
		if(Main.isLastGameSaved())
		{
			Main.gameState = Main.deserializeLastGame();
			LeaderboardEntry entry = new LeaderboardEntry(Main.gameState.getScore(), Main.gameState.getDate());
			Main.updateLeaderBoard(entry);
		}
		Main.gamePageController.setUpGamePage();
		Main.mainStage.setScene(Main.gamePageScene);
	}

	public void resumeGameButtonPressed() throws IOException, ClassNotFoundException
	{
		Main.gameState = Main.deserializeLastGame();
		Main.gamePageController.setUpGamePage();
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
