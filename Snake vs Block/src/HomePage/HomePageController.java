package HomePage;

import GameApplication.Main;
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
		//System.out.println("In HP Last Game Saved = " + Main.isGameSaved());

		if(Main.isGameSaved())
		{
			resumegame.setDisable(false);
			GameState gameState = Main.deserializeLastGame();
			lastGameScore.setText("Last Game : " + gameState.getScore());
		}
		else
		{
			resumegame.setDisable(true);
		}
	}

	public void updateScoreLabel(String score)
	{
		lastGameScore.setText("Last Game : " + score);
	}

	public void startGameButtonPressed() throws IOException, ClassNotFoundException
	{
		if(Main.isGameSaved())
		{
			GameState gameState = Main.deserializeLastGame();
			Main.serializeLastGame(null);
			LeaderboardEntry entry = new LeaderboardEntry(gameState.getScore(), gameState.getDate());
			Main.updateLeaderBoard(entry);
		}
		Main.gamePageController.setUpGamePage();
		Main.mainStage.setScene(Main.gamePageScene);
	}

	public void resumeGameButtonPressed() throws IOException, ClassNotFoundException
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
