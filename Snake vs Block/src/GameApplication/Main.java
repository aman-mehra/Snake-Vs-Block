package GameApplication;

import GamePage.GamePageController;
import GamePage.GameState;
import HomePage.HomePageController;
import LeaderboardPage.LeaderboardEntry;
import LeaderboardPage.LeaderboardPageController;
import PopupBoxes.ConfirmBox;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

	public static GameState gameState;
	public static FXMLLoader homePageLoader, leaderboardPageLoader, gamePageLoader;
	public static HomePageController homePageController;
	public static LeaderboardPageController leaderboardPageController;
	public static GamePageController gamePageController;
	public static Parent homePageParent, leaderboardPageParent, gamePageParent;
    public static Scene homePageScene, leaderboardPageScene, gamePageScene;
    public static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
	{

    	mainStage = primaryStage;
		mainStage.setTitle("Snake vs Block");

		homePageLoader = new FXMLLoader(getClass().getClassLoader().getResource("HomePage/HomeScene.fxml"));
		homePageController = new HomePageController();
		homePageLoader.setController(homePageController);
		homePageParent = homePageLoader.load();
		homePageScene = new Scene(homePageParent);

		leaderboardPageLoader = new FXMLLoader(getClass().getClassLoader().getResource("LeaderboardPage/LeaderboardScene.fxml"));
		leaderboardPageController = new LeaderboardPageController();
		leaderboardPageLoader.setController(leaderboardPageController);
		leaderboardPageParent = leaderboardPageLoader.load();
		leaderboardPageScene = new Scene(leaderboardPageParent);

		gamePageLoader = new FXMLLoader(getClass().getClassLoader().getResource("GamePage/GameScene.fxml"));
		gamePageController = new GamePageController();
		gamePageLoader.setController(gamePageController);
		gamePageParent = gamePageLoader.load();
		gamePageScene = new Scene(gamePageParent);

		homePageController.setUpHomePage();
		mainStage.setScene(homePageScene);
		mainStage.show();

		mainStage.setOnCloseRequest(e ->
		{
			e.consume();
			if(mainStage.getScene() == gamePageScene)
			{
				gamePageController.pauseTimers();
				gamePageController.pauseTransitions();
				Main.gameState = gamePageController.getCurrentGameState();
			}
			closeApplication();
		});
    }

    public static void serializeLeaderboard(ArrayList<LeaderboardEntry> scores) throws IOException
	{
		ObjectOutputStream out = null;
		try
		{
			out = new ObjectOutputStream(new FileOutputStream("leaderboard.txt"));
			int sz = scores.size();
			out.write(sz);
			for(int i=0; i<sz; i++)
				out.writeObject(scores.get(i));
		}
		finally
		{
			if(out != null)
				out.close();
		}

	}

    public static ObservableList<LeaderboardEntry> deserializeLeaderboard() throws IOException, ClassNotFoundException
	{
		ObjectInputStream in = null;
		ObservableList<LeaderboardEntry> scores = null;
		try
		{
			in = new ObjectInputStream(new FileInputStream("leaderboard.txt"));
			File file = new File("leaderboard.txt");
			if(file.length() != 0)
			{
				int sz = in.read();
				scores = FXCollections.observableArrayList();
				for(int i=0; i<sz; i++)
					scores.add((LeaderboardEntry) in.readObject());
			}
		}
		finally
		{
			if(in != null)
				in.close();
		}
		return scores;
	}

	public static void updateLeaderBoard(LeaderboardEntry entry) throws IOException, ClassNotFoundException
	{
		ObservableList<LeaderboardEntry> entries = FXCollections.observableArrayList();
		if(areEntriesPresent())
			entries = Main.deserializeLeaderboard();
		entries.add(entry);
		FXCollections.sort(entries);
		ArrayList<LeaderboardEntry> updatedEntries = new ArrayList<>();

		int sz = Math.min(entries.size(), 10);
		for(int i=0; i<sz; i++)
			updatedEntries.add(entries.get(i));

		Main.serializeLeaderboard(updatedEntries);
	}

	public static boolean areEntriesPresent()
	{
		File file = new File("leaderboard.txt");
		if(file.length() == 0)
			return false;
		return true;
	}

	public static void serializeLastGame(GameState gameState) throws IOException
	{
		ObjectOutputStream out = null;
		try
		{
			out = new ObjectOutputStream(new FileOutputStream("lastgame.txt"));
			out.writeObject(gameState);
		}
		finally
		{
			if(out != null)
				out.close();
		}
	}

	public static GameState deserializeLastGame() throws IOException, ClassNotFoundException
	{
		ObjectInputStream in = null;
		GameState gameState = null;
		try
		{
			in = new ObjectInputStream(new FileInputStream("lastgame.txt"));
			gameState = (GameState) in.readObject();
		}
		finally
		{
			if(in != null)
				in.close();
		}
		return gameState;
	}

	public static boolean isLastGameSaved()
	{
		File file = new File("lastgame.txt");
		if(file.length() == 0)
			return false;
		return true;
	}

	public static void closeApplication()
	{
		boolean ans = ConfirmBox.display("Confirm Exit", "Are you sure you want to exit?");
		if(ans)
		{
			try	{serializeLastGame(gameState);}
			catch (IOException e) {}
			Main.mainStage.close();
		}
		else if(mainStage.getScene() == gamePageScene)
		{
			gamePageController.playTimers();
			gamePageController.playTransitions();
		}
	}
}
