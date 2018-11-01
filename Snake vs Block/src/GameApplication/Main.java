package GameApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Parent mainPageScene, leaderboardPageScene, gamePageScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Snake vs Block");

        mainPageScene = FXMLLoader.load(getClass().getClassLoader().getResource("MainPage/MainScene.fxml"));
        leaderboardPageScene = FXMLLoader.load(getClass().getClassLoader().getResource("LeaderboardPage/LeaderboardScene.fxml"));
		gamePageScene = FXMLLoader.load(getClass().getClassLoader().getResource("GamePage/GameScene.fxml"));

		primaryStage.setScene(new Scene(gamePageScene));
        primaryStage.show();
    }

}
