package GameApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Parent mainPageScene, leaderboardPageScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Snake vs Block");

        mainPageScene = FXMLLoader.load(getClass().getClassLoader().getResource("MainPage/MainPageScene.fxml"));
        leaderboardPageScene = FXMLLoader.load(getClass().getClassLoader().getResource("LeaderboardPagePage/LeaderboardPageScene.fxml"));

        primaryStage.setScene(new Scene(mainPageScene, 500, 700));
        primaryStage.show();
    }

}
