package GamePage;

import GameApplication.Main;
import GameObjects.Block;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class GamePageController
{
/*
	public Label pos;
	public Pane backPane;


	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

		*/
/*Line pathline = new Line();
		pathline.setStartX(150); pathline.setStartY(-50);
		pathline.setEndX(150); pathline.setEndY(850);

		Line pathline2 = new Line();
		pathline2.setStartX(50); pathline2.setStartY(-50);
		pathline2.setEndX(50); pathline2.setEndY(850);


		Block obj1 = new Block(100,0,Color.valueOf("#00FF00"));

		Rectangle block2 = new Rectangle();
		block2.setHeight(100); block2.setWidth(100);
		block2.setX(0); block2.setY(0);
		block2.setFill(Color.valueOf("#FF0000"));

		backPane.getChildren().addAll(block2, obj1);


		PathTransition transition1 = new PathTransition();
		transition1.setNode(block2);
		transition1.setDuration(Duration.seconds(5));
		transition1.setPath(pathline);
		transition1.play();

		PathTransition transition2 = new PathTransition();
		transition2.setNode(obj1);
		transition2.setDuration(Duration.seconds(5));
		transition2.setPath(pathline2);
		transition2.play();*//*


		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.25), ae -> generateObjects()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		// later
		//timeline.stop();



	}

	public void generateObjects()
	{
		Block block = new Block(0,0,Color.valueOf("FF0000"));

		Line path = new Line();
		path.setStartX(150); path.setStartY(-50);
		path.setEndX(150); path.setEndY(850);

		PathTransition transition1 = new PathTransition();
		transition1.setNode(block);
		transition1.setDuration(Duration.seconds(5));
		transition1.setPath(path);
		transition1.play();

		backPane.getChildren().add(block);

	}

	public void displayPosition(MouseEvent event)
	{
		pos.setText("X = " + event.getX() + "		Y = " + event.getY());
	}
*/

}
