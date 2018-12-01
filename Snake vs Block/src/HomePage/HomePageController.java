package HomePage;

import GameApplication.Main;
import GamePage.GamePageController;
import GamePage.GameState;
import LeaderboardPage.LeaderboardEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


public class HomePageController
{
	@FXML public Button resumegame;
	@FXML public Label lastGameScore;

	public void setUpHomePage() throws IOException, ClassNotFoundException
	{
		//System.out.println("HomePage coming up !");
		//System.out.println("In HP Last Game Saved = " + Main.isLastGameSaved());
		lastGameScore.setText("Last Game : 0");
		if(Main.isLastGameSaved())
		{
			resumegame.setDisable(false);
			Main.gameState = Main.deserializeLastGame();
			lastGameScore.setText("Last Game : " + Main.gameState.getScore());
		}
	}

	public void startGameButtonPressed() throws IOException, ClassNotFoundException
	{
		if(Main.isLastGameSaved())
		{
			LeaderboardEntry entry = new LeaderboardEntry(Main.gameState.getScore(), Main.gameState.getDate());
			Main.updateLeaderBoard(entry);
			Main.gameState = null;
		}
		Main.gamePageController.setUpGamePage();
		Main.mainStage.setScene(Main.gamePageScene);
	}

	public void resumeGameButtonPressed()
	{
		Main.gamePageController.setUpGamePage();
		Main.mainStage.setScene(Main.gamePageScene);
	}

	public void showLeaderboardButtonPressed() throws IOException, ClassNotFoundException
	{
		Main.leaderboardPageController.setUpLeaderboardPage();
		Main.mainStage.setScene(Main.leaderboardPageScene);
	}

	public void exitButtonPressed()
	{
		Main.closeApplication();
	}

}
