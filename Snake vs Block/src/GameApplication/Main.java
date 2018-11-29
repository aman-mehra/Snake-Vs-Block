package GameApplication;

import LeaderboardPage.LeaderboardEntry;
import MainPage.MainPageController;
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

    public static Parent mainPageParent, leaderboardPageParent, gamePageParent;
    public static Scene mainPageScene, leaderboardPageScene, gamePageScene;
    public static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
	{

    	mainStage = primaryStage;

		mainStage.setTitle("Snake vs Block");

        mainPageParent = FXMLLoader.load(getClass().getClassLoader().getResource("MainPage/MainScene.fxml"));
        leaderboardPageParent = FXMLLoader.load(getClass().getClassLoader().getResource("LeaderboardPage/LeaderboardScene.fxml"));
		gamePageParent = FXMLLoader.load(getClass().getClassLoader().getResource("GamePage/GameScene.fxml"));

		mainPageScene = new Scene(mainPageParent);
		leaderboardPageScene = new Scene(leaderboardPageParent);
		gamePageScene = new Scene(gamePageParent);

		mainStage.setScene(gamePageScene);
		mainStage.show();

		mainStage.setOnCloseRequest(e ->
		{
			e.consume();
			MainPageController.closeProgram();
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
			in.close();
		}
		return scores;
	}

	public static void serializeLastGame()
	{

	}

	public static void deserializeLastGame()
	{

	}

}
