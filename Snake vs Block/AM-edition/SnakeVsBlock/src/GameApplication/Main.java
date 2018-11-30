package GameApplication;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    public static Parent mainPage, leaderboardPage, gamePage;
    public static Scene GamePageScene;
    public static boolean turnLeft,turnRight;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Snake vs Block");

        mainPage = FXMLLoader.load(getClass().getClassLoader().getResource("HomePage/HomeScene.fxml"));
        leaderboardPage = FXMLLoader.load(getClass().getClassLoader().getResource("LeaderboardPage/LeaderboardScene.fxml"));
		gamePage = FXMLLoader.load(getClass().getClassLoader().getResource("GamePage/GameScene.fxml"));

		GamePageScene = new Scene(gamePage);
				
		primaryStage.setScene(GamePageScene);
        primaryStage.show();
    }
    
}
