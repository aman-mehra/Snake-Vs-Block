package GamePage;

import GameObjects.*;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.net.URL;
import java.util.Observable;
import java.util.Random;
import java.util.ResourceBundle;

public class GamePageController implements Initializable
{
	public static final String[] COLOUR = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00"};
	public static final long offset = 2000;
	public Label pos;
	public Pane window;
	public Random rand;
	public long blockPrevTime;
	public long tokenPrevTime;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		rand = new Random();
		blockPrevTime = System.currentTimeMillis();
		tokenPrevTime = System.currentTimeMillis() + offset/2;
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
				Block block = new Block(20, path.getStartX(),path.getStartY(),getRandomColour());
				window.getChildren().add(block);

				block.setTransition(new PathTransition(), path, Duration.seconds(8));
				block.getTransition().play();
			}
		}
	}

	public void generateToken()
	{
		Line path = getTokenPath();
		int idx = rand.nextInt(5);

		if(idx == 0 || idx == 1)
		{
			Ball ball = new Ball(rand.nextInt(10), path.getStartX(), path.getStartY());
			ball.setTransition(new PathTransition(), path, Duration.seconds(8));
			ball.getTransition().play();
			window.getChildren().add(ball);
		}

		else if(idx == 2)
		{
			Magnet magnet = new Magnet(System.currentTimeMillis(), path.getStartX(), path.getStartY());
			magnet.setTransition(new PathTransition(), path, Duration.seconds(8));
			magnet.getTransition().play();
			window.getChildren().add(magnet);
		}

		else if(idx == 3)
		{
			Shield shield = new Shield(System.currentTimeMillis(), path.getStartX(), path.getStartY());
			shield.setTransition(new PathTransition(), path, Duration.seconds(8));
			shield.getTransition().play();
			window.getChildren().add(shield);
		}

		else if(idx == 4)
		{
			DestroyBlocks destroyBlocks = new DestroyBlocks(path.getStartX(), path.getStartY());
			destroyBlocks.setTransition(new PathTransition(), path, Duration.seconds(8));
			destroyBlocks.getTransition().play();
			window.getChildren().add(destroyBlocks);
		}

	}

	public void deleteGarbage()
	{
		int i=0;
		while(i < window.getChildren().size())
		{
			if(window.getChildren().get(i).getClass() == Block.class)
			{
				Block tempBlock = (Block) window.getChildren().get(i);
				if(tempBlock.getTranslateY() > 800)
				{
					tempBlock.getTransition().stop();
					window.getChildren().remove(i);
				}
			}
			i++;
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
		int position_x = rand.nextInt(470) + 30;
		path.setStartX(position_x); path.setStartY(-100);
		path.setEndX(position_x); path.setEndY(1000);
		return path;
	}

	public Paint getRandomColour()
	{
		int i = rand.nextInt(4);
		return Color.valueOf(COLOUR[i]);
	}

	public void displayPosition(MouseEvent event)
	{
		pos.setText("X = " + event.getX() + "		Y = " + event.getY());
	}

}
