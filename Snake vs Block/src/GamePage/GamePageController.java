package GamePage;

import GameObjects.*;
import javafx.animation.*;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GamePageController implements Initializable
{
	public static final String[] COLOUR = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00"};
	public static final long offset = 2000;

	//public Label pos;
	public Pane gameArea;
	public ComboBox<String> options;

	public Random rand;
	public long blockPrevTime;
	public long tokenPrevTime;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		rand = new Random();
		blockPrevTime = System.currentTimeMillis();
		tokenPrevTime = (System.currentTimeMillis() + offset/2);

		options.getItems().addAll("Restart", "Exit");

		Snake snake = new Snake();
		gameArea.getChildren().add(snake.getHead());
		gameArea.getChildren().addAll(snake.getBody());

		startBlockGeneration();
		startTokenGeneration();
	}

	public void startBlockGeneration()
	{

		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				long curTime = System.currentTimeMillis();
				if(curTime > blockPrevTime + offset)
				{
					generateBlocks();
					blockPrevTime = System.currentTimeMillis();
				}
				deleteGarbage();
			}
		};
		timer.start();

	}

	public void startTokenGeneration()
	{

		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				long curTime = System.currentTimeMillis();
				if(curTime > tokenPrevTime + offset)
				{
					generateToken();
					tokenPrevTime = System.currentTimeMillis();
				}
				deleteGarbage();
			}
		};
		timer.start();

	}

	public void generateBlocks()
	{
		for(int i=0; i<5; i++)
		{
			if(rand.nextInt(2) == 1)
			{
				Line path = getBlockPath(i);
				Block block = new Block(rand.nextInt(20) + 1, path.getStartX(),path.getStartY(),getRandomColour());
				Text text = new Text(block.getValue() + "");
				text.setFont(Font.font(40));
				StackPane stackPane = new StackPane();
				stackPane.getChildren().addAll(block, text);
				gameArea.getChildren().add(stackPane);
				PathTransition transition = new PathTransition(Duration.seconds(8), path, stackPane);
				transition.play();
			}
		}
	}

	public void generateToken()
	{
		Line path = getTokenPath();
		int idx = rand.nextInt(5);

		if(idx == 0 || idx == 1)
		{
			Ball ball = new Ball(Integer.toString(rand.nextInt(10) + 1), path.getStartX(), path.getStartY(), 15);
			Text text = new Text(ball.getValue());
			text.setFont(Font.font(18));
			StackPane stackPane = new StackPane();
			stackPane.getChildren().addAll(ball, text);
			gameArea.getChildren().add(stackPane);
			PathTransition transition = new PathTransition(Duration.seconds(8), path, stackPane);
			transition.play();
		}

		else if(idx == 2)
		{
			Magnet magnet = new Magnet(System.currentTimeMillis(), path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(8), path, magnet);
			transition.play();
			gameArea.getChildren().add(magnet);
		}

		else if(idx == 3)
		{
			Shield shield = new Shield(System.currentTimeMillis(), path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(8), path, shield);
			transition.play();
			gameArea.getChildren().add(shield);
		}

		else if(idx == 4)
		{
			DestroyBlocks destroyBlocks = new DestroyBlocks(path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(8), path, destroyBlocks);
			transition.play();
			gameArea.getChildren().add(destroyBlocks);
		}

	}

	public Line getBlockPath(int index)
	{
		Line path = new Line();
		path.setStartX(50 + index * 100); path.setStartY(-100);
		path.setEndX(50 + index * 100); path.setEndY(1000);
		return path;
	}

	public Line getTokenPath()
	{
		Line path = new Line();
		int position_x = rand.nextInt(440) + 30;
		path.setStartX(position_x); path.setStartY(-100);
		path.setEndX(position_x); path.setEndY(1000);
		return path;
	}

	public Paint getRandomColour()
	{
		int i = rand.nextInt(4);
		return Color.valueOf(COLOUR[i]);
	}

	public void deleteGarbage()
	{
		int i=0;
		while(i < gameArea.getChildren().size())
		{
			if(gameArea.getChildren().get(i).getClass() == StackPane.class)
			{
				StackPane stackPane = (StackPane) gameArea.getChildren().get(i);
				if(stackPane.getTranslateY() > 700)
					gameArea.getChildren().remove(i);
			}
			else if(gameArea.getChildren().get(i).getClass() == Magnet.class)
			{
				Magnet token = (Magnet) gameArea.getChildren().get(i);
				if(token.getTranslateY() > 800)
					gameArea.getChildren().remove(i);
			}
			else if(gameArea.getChildren().get(i).getClass() == Shield.class)
			{
				Shield token = (Shield) gameArea.getChildren().get(i);
				if(token.getTranslateY() > 800)
					gameArea.getChildren().remove(i);
			}
			else if(gameArea.getChildren().get(i).getClass() == DestroyBlocks.class)
			{
				DestroyBlocks token = (DestroyBlocks) gameArea.getChildren().get(i);
				if(token.getTranslateY() > 800)
					gameArea.getChildren().remove(i);
			}
			i++;
		}
	}

//	public void displayPosition(MouseEvent event)
//	{
//		pos.setText("X = " + event.getX() + "		Y = " + event.getY());
//	}

}
