package MainPage;

import GameApplication.Main;
import PopupBoxes.ConfirmBox;


public class MainPageController
{
	public void startGameButtonPressed()
	{
//		startGame();
		Main.mainStage.setScene(Main.gamePageScene);
	}

	public void resumeGameButtonPressed()
	{

	}

	public void showLeaderboardButtonPressed()
	{
		Main.mainStage.setScene(Main.leaderboardPageScene);
	}

	public void exitButtonPressed()
	{
		closeProgram();
	}

	public static void startGame()
	{

	}

	public static void closeProgram()
	{
		boolean ans = ConfirmBox.display("Confirm Exit", "Are you sure you want to exit?");
		if(ans)
			Main.mainStage.close();
	}
}
