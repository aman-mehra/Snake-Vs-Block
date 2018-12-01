package GamePage;

import GameApplication.Main;
import GameObjects.*;
import HomePage.HomePageController;
import LeaderboardPage.LeaderboardEntry;
import PopupBoxes.ConfirmBox;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class GamePageController implements Serializable
{
	public Pane gameArea;
	public ComboBox<String> options;
	public Label score_box;

	private static final String[] COLOUR = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00"};
	private static final double lambda = 0.7;
	private static final long mov_offset = 2;
	private static final Random rand = new Random();

	private long score;
	private String date;
	private long offset;
	private double animation_speed;
	private Snake snake;
	private boolean turnLeft, turnRight, gamePaused;
	private ArrayList<PathTransition> transitions;
	private AnimationTimer blockTimer, tokenTimer, snakeMovementTimer, gameLoopTimer;
	private long blockPrevTime, tokenPrevTime;
	private long startTime, moveTime;
	//private static ArrayList<Integer> garbageObjects = new ArrayList<Integer>();


	public void setUpGamePage()
	{
		date = "";
		transitions = new ArrayList<PathTransition>();
		gameArea.getChildren().removeAll();
		turnLeft = false; turnRight = false;
		blockTimer = null; tokenTimer = null; snakeMovementTimer = null; gameLoopTimer = null;
		options.getItems().addAll("Restart", "Exit");
		score_box.setText(score + "");

		if(Main.gameState == null)
		{
			score = 0;
			offset = 1000;
			animation_speed = 3;
			snake = new Snake();
		}
		else
		{
			score = Main.gameState.getScore();
			offset = Main.gameState.getOffset();
			animation_speed = Main.gameState.getAnimation_speed();
			//reconstruct snake, gameObjects
		}

		gameArea.getChildren().addAll(snake.getBody());
		gameArea.getChildren().add(snake.getHead());

		blockPrevTime = System.currentTimeMillis();
		tokenPrevTime = (System.currentTimeMillis() + offset/2);
		startTime = System.currentTimeMillis();
		moveTime = 0;

		setGlobals();
		gameLoop();

		startBlockGeneration();
		startTokenGeneration();
	}

	private void setGlobals()
	{
		snakeMovementTimer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				long curTime = System.currentTimeMillis();
				if(curTime-startTime > 1000 )
				{
					setKeyPressEventHandlers();
					this.stop();
				}
			}
		};
		snakeMovementTimer.start();
	}

	private void setKeyPressEventHandlers()
	{
		Main.gamePageScene.setOnKeyPressed(e ->
		{
			switch (e.getCode())
			{
				case LEFT	:
					turnLeft = true;
					moveTime = System.currentTimeMillis();
					break;

				case RIGHT	:
					turnRight = true;
					moveTime = System.currentTimeMillis();
					break;
			}
		});

		Main.gamePageScene.setOnKeyReleased(e ->
		{
			switch (e.getCode())
			{
				case LEFT	:	turnLeft = false;
								break;
				case RIGHT	:	turnRight = false;
								break;
			}
		});
	}

	private void gameLoop()
	{
		gameLoopTimer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				if(turnLeft==true && now-moveTime>mov_offset) {
					snake.moveSnake(-1);
					moveTime=System.currentTimeMillis();
				}
				else if(turnRight==true && now-moveTime>mov_offset) {
					snake.moveSnake(1);
					moveTime=System.currentTimeMillis();
				}
				else if(now-moveTime>mov_offset) {
					snake.moveSnake(0);
					moveTime=System.currentTimeMillis();
				}


				for(int i=0;i < gameArea.getChildren().size();i++)
				{
					if(gameArea.getChildren().get(i).getClass() == StackPane.class)
					{
						StackPane stackPane = (StackPane) gameArea.getChildren().get(i);
						if(stackPane.getChildren().get(0).getClass() == Ball.class) {
							Ball ball=(Ball)stackPane.getChildren().get(0);
							int value = Integer.parseInt(ball.getValue());
							if(snake.collision(stackPane) && ball.isActive()) {
								snake.increaseLength(value);
								ball.setActive(false);
								gameArea.getChildren().get(i).setVisible(false);
								gameArea.getChildren().addAll(snake.getBody().subList(snake.getBody().size()-value, snake.getBody().size()));
								print(1);
							}
						}

						if(stackPane.getChildren().get(0).getClass() == Block.class) {
							Block block=(Block)stackPane.getChildren().get(0);
							int value = block.getValue();
							if(snake.blockCollision(stackPane,block) && block.isActive()) {
								if (value<snake.getLength()) {
									//print(2);
									if(value<=5) {
										block.setActive(false);
										gameArea.getChildren().get(i).setVisible(false);
										for(int j=0;j<value;j++) {
											int id=snake.decreaseLength();
											deleteSnakePart(id);
										}
										playTransitions();
									}
									else {
										print(value);
										pauseTransitions();
										//moveBlocksUp();
										snake.moveBack();
										block.setValue(value-1);
										int id=snake.decreaseLength();
										deleteSnakePart(id);

									}
								}
								else {

								}
							}
						}
					}

					if(gameArea.getChildren().get(i).getClass() == StackPane.class)
					{
						StackPane stackPane = (StackPane) gameArea.getChildren().get(i);
						if(stackPane.getChildren().get(0).getClass() == Ball.class) {
							Ball ball=(Ball)stackPane.getChildren().get(0);
							int value = Integer.parseInt(ball.getValue());
							if(snake.collision(stackPane) && ball.isActive()) {
								snake.increaseLength(value);
								ball.setActive(false);
								gameArea.getChildren().get(i).setVisible(false);
								gameArea.getChildren().addAll(snake.getBody().subList(snake.getBody().size()-value, snake.getBody().size()));
								print(1);
							}
						}
					}
				}
				//animation_speed=3;
				//offset=(long)(8-animation_speed)*300;//for speed = 4
				//offset=(long)(8-animation_speed)*200;//for speed = 3
				//offset=(long)(8-animation_speed)*150;for speed = 2
				snake.moveAhead();
			}
		};
		gameLoopTimer.start();
	}

	private void startBlockGeneration()
	{

		blockTimer = new AnimationTimer()
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
		blockTimer.start();

	}

	private void startTokenGeneration()
	{

		tokenTimer = new AnimationTimer()
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
		tokenTimer.start();

	}

	private void explode(Block block)
	{
		double start_position_x = block.getX()+(block.getSIDE()/2)+(rand.nextDouble()*(block.getSIDE()/4)-(block.getSIDE()/8));
		double start_position_y = block.getX()+(block.getSIDE()/2)+(rand.nextDouble()*(block.getSIDE()/4)-(block.getSIDE()/8));
		double path_time = 0.5;
		for(int shards = 0 ; shards<60; shards++) {
			Line path = new Line();
			path.setStartX(start_position_x); path.setStartY(start_position_x);
			path.setEndX(rand.nextInt(900)-300); path.setEndY(-100);
			ExplosionPart debris = new ExplosionPart( path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(path_time+rand.nextDouble()*(0.3)), path, debris);
			transition.play();
			gameArea.getChildren().add(debris);
		}

		for(int shards = 0 ; shards<60; shards++) {
			Line path = new Line();
			path.setStartX(start_position_x); path.setStartY(start_position_x);
			path.setEndX(rand.nextInt(900)-300); path.setEndY(1100);
			ExplosionPart debris = new ExplosionPart( path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(path_time+rand.nextDouble()*(0.3)), path, debris);
			transition.play();
			gameArea.getChildren().add(debris);
		}

		for(int shards = 0 ; shards<60; shards++) {
			Line path = new Line();
			path.setStartX(start_position_x); path.setStartY(start_position_x);
			path.setEndX(-600); path.setEndY(rand.nextInt(1500)-300);
			ExplosionPart debris = new ExplosionPart( path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(path_time+rand.nextDouble()*(0.3)), path, debris);
			transition.play();
			gameArea.getChildren().add(debris);
		}

		for(int shards = 0 ; shards<60; shards++) {
			Line path = new Line();
			path.setStartX(start_position_x); path.setStartY(start_position_x);
			path.setEndX(700); path.setEndY(rand.nextInt(1500)-300);
			ExplosionPart debris = new ExplosionPart( path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(path_time+rand.nextDouble()*(0.3)), path, debris);
			transition.play();
			gameArea.getChildren().add(debris);
		}
	}

	private void moveBlocksUp()
	{
		for(int i=0; i<gameArea.getChildren().size(); i++)
		{
			if(gameArea.getChildren().get(i).getClass() == StackPane.class)
			{
				StackPane stackPane = (StackPane) gameArea.getChildren().get(i);
				if(stackPane.getChildren().get(0).getClass() == Block.class || stackPane.getChildren().get(0).getClass() == Ball.class) {
					stackPane.setTranslateY(stackPane.getTranslateY());
				}
			}
			else if(gameArea.getChildren().get(i).getClass() == Magnet.class) {
				Magnet token = (Magnet) gameArea.getChildren().get(i);
				token.setTranslateY(token.getTranslateY());
			}
			else if(gameArea.getChildren().get(i).getClass() == Shield.class) {
				Shield token = (Shield) gameArea.getChildren().get(i);
				token.setTranslateY(token.getTranslateY());
			}
			else if(gameArea.getChildren().get(i).getClass() == DestroyBlocks.class) {
				DestroyBlocks token = (DestroyBlocks) gameArea.getChildren().get(i);
				token.setTranslateY(token.getTranslateY());
			}
		}


	}

	public void pauseTimers()
	{
		blockTimer.stop();
		tokenTimer.stop();
		snakeMovementTimer.stop();
		gameLoopTimer.stop();
	}

	public void playTimers()
	{
		blockTimer.start();
		tokenTimer.start();
		snakeMovementTimer.start();
		gameLoopTimer.start();
	}

	public void pauseTransitions()
	{
		int i = 0;
		while (i<transitions.size())
		{
			transitions.get(i).pause();
			i+=1;
		}
	}

	public void playTransitions()
	{
		int i = 0;
		while (i<transitions.size())
		{
			transitions.get(i).play();
			i+=1;
		}
	}

	////////////////////////////////////
	private void print(Object toPrint)
	{
		System.out.println(toPrint);
	}
	////////////////////////////////////

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
				PathTransition transition = new PathTransition(Duration.seconds(animation_speed), path, stackPane);
				transition.play();
				transitions.add(transition);
			}
		}
	}

	public void generateToken()
	{
		Line path = getTokenPath();
		int idx = rand.nextInt(5);

		if(idx == 0 || idx == 1)
		{
			Ball ball = new Ball(Integer.toString(getNextBallNumber()), path.getStartX(), path.getStartY(), 15);
			Text text = new Text(ball.getValue());
			text.setFont(Font.font(18));
			StackPane stackPane = new StackPane();
			stackPane.getChildren().addAll(ball, text);
			gameArea.getChildren().add(stackPane);
			PathTransition transition = new PathTransition(Duration.seconds(animation_speed), path, stackPane);
			transition.play();
			transitions.add(transition);
		}

		else if(idx == 2)
		{
			Magnet magnet = new Magnet(System.currentTimeMillis(), path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(animation_speed), path, magnet);
			transition.play();
			transitions.add(transition);
			gameArea.getChildren().add(magnet);
		}

		else if(idx == 3)
		{
			Shield shield = new Shield(System.currentTimeMillis(), path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(animation_speed), path, shield);
			transition.play();
			transitions.add(transition);
			gameArea.getChildren().add(shield);
		}

		else if(idx == 4)
		{
			DestroyBlocks destroyBlocks = new DestroyBlocks(path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(animation_speed), path, destroyBlocks);
			transition.play();
			transitions.add(transition);
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

	public int getNextBallNumber()
	{
		return  (int)(Math.ceil(Math.log(1-rand.nextDouble())/(-lambda)));
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

	public void deleteSnakePart(int id)
	{
		for (int i=0;i<gameArea.getChildren().size();i++)
		{
			if(gameArea.getChildren().get(i).getClass()==SnakePart.class)
			{
				SnakePart temp = (SnakePart)gameArea.getChildren().get(i);
				if(temp.getPId()==id)
				{
					gameArea.getChildren().get(i).setVisible(false);
					gameArea.getChildren().remove(i);
				}
			}
		}
	}

	public void optionSelected() throws IOException, ClassNotFoundException
	{
		pauseTimers();
		pauseTransitions();

		String choice = options.getValue();
		if(choice.equals("Restart"))
		{
			boolean ans = ConfirmBox.display("Confirm Restart", "Are you sure you want to restart?");
			if(ans)
			{
				date = getCurrentDate();
				LeaderboardEntry entry = new LeaderboardEntry(score, date);
				Main.updateLeaderBoard(entry);
				Main.gamePageController.setUpGamePage();
			}
			else
			{
				playTimers();
				playTransitions();
			}
		}
		else if(choice.equals("Home"))
		{
			boolean ans = ConfirmBox.display("Confirm Exit", "Are you sure you want to quit?");
			if(ans)
			{
				Main.gameState = getCurrentGameState();
				Main.serializeLastGame(Main.gameState);
				Main.homePageController.setUpHomePage();
				Main.mainStage.setScene(Main.homePageScene);
			}
			else
			{
				playTimers();
				playTransitions();
			}
		}
	}

	public GameState getCurrentGameState()
	{
		return (new GameState(score, date, offset, animation_speed, snake.getHead_x(), snake.getLength(), gameArea.getChildren().size() - snake.getLength() - 1));
	}

	public String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
