package LeaderboardPage;

import GameApplication.Main;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class LeaderboardPageController implements Initializable
{

	public TableView<LeaderboardEntry> leaderboard;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		try
		{
			ObservableList<LeaderboardEntry> scores = Main.deserializeLeaderboard();
			Collections.sort(scores);
			leaderboard.setItems(scores);
		}
		catch (IOException e)
		{

		}
		catch (ClassNotFoundException e)
		{

		}
		catch (NullPointerException e)
		{

		}

	}

	public void homeButtonPressed()
	{
		Main.mainStage.setScene(Main.mainPageScene);
	}
}
