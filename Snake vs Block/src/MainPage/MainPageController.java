package MainPage;

import GameApplication.Main;


public class MainPageController
{
	public void startGame()
	{
		Main.mainStage.setScene(Main.gamePageScene);
	}

	public void resumeGame()
	{

	}

	public void showLeaderboard()
	{
		Main.mainStage.setScene(Main.leaderboardPageScene);
	}

	public void exit()
	{
		Main.closeProgram();
	}
}
