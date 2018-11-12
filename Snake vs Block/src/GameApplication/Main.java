package GameApplication;

import MainPage.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

		mainStage.setScene(mainPageScene);
		mainStage.show();

		mainStage.setOnCloseRequest(e ->
		{
			e.consume();
			MainPageController.closeProgram();
		});
    }
}
