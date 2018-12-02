package HomePage;

import GameApplication.Main;
import GamePage.GameState;
import LeaderboardPage.LeaderboardEntry;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


/**
 * Controller class for HomePageScene
 *
 * @author Bhavye
 */

public class HomePageController
{
	@FXML public Button resumegame;
	@FXML public Label lastGameScore;

	/**
	 * Sets up the HomePage before HomePageScene is setted up.
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author Bhavye
	 */
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

	/**
	 * Updates Score Label with the score provided.
	 *
	 * @param score score of last game.
	 * @author Bhavye
	 */
	public void updateScoreLabel(String score)
	{
		lastGameScore.setText("Last Game : " + score);
	}

	/**
	 * EventHandler when Start Game button is pressed.
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author Bhavye
	 */
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

	/**
	 * EventHandler when Resume Game button is pressed.
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author Bhavye
	 */
	public void resumeGameButtonPressed() throws IOException, ClassNotFoundException
	{
		Main.gamePageController.setUpGamePage();
		Main.mainStage.setScene(Main.gamePageScene);
	}

	/**
	 * EventHandler when Show Leaderboard button is pressed.
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author Bhavye
	 */
	public void showLeaderboardButtonPressed() throws IOException, ClassNotFoundException
	{
		Main.leaderboardPageController.setUpLeaderboardPage();
		Main.mainStage.setScene(Main.leaderboardPageScene);
	}

	/**
	 * EventHandler when Exit button is pressed.
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author Bhavye
	 */
	public void exitButtonPressed()
	{
		Main.closeApplication();
	}

}
