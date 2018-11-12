package LeaderboardPage;

import GameApplication.Main;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardPageController implements Initializable
{
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		//Deserialize the entires
	}

	public void home()
	{
		Main.mainStage.setScene(Main.mainPageScene);
	}
}
