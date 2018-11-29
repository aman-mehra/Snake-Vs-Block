package GamePage;

import GameApplication.Main;
import GameObjects.*;
import MainPage.MainPageController;
import PopupBoxes.ConfirmBox;
import javafx.animation.*;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class GamePageController implements Initializable
{
	//public Label pos;
	public Pane gameArea;
	public ComboBox<String> options;
	public Label score_box;

	private static final String[] COLOUR = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00"};
	private static double animation_speed = 3;
	private static long offset = 1000;
	private static final double lambda = 0.7;
	private static final long mov_offset = 2;
	private static boolean turnLeft,turnRight;
	private static ArrayList<PathTransition> transitions;
	private static int score;

	private static long blockPrevTime;
	private static long tokenPrevTime;
	private static long startTime;
	private static long moveTime;
	private static AnimationTimer blockTimer;
	private static AnimationTimer tokenTimer;
	private static Snake snake;
	private static Random rand;
	//private static ArrayList<Integer> garbageObjects = new ArrayList<Integer>();


	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		rand = new Random();
		blockPrevTime = System.currentTimeMillis();
		tokenPrevTime = (System.currentTimeMillis() + offset/2);
		startTime = System.currentTimeMillis();

		options.getItems().addAll("Restart", "Exit");
//		options.toFront();
//		score.toFront();

		snake = new Snake();
		gameArea.getChildren().add(snake.getHead());
		gameArea.getChildren().addAll(snake.getBody());

		transitions = new ArrayList<PathTransition>();

		startBlockGeneration();
		startTokenGeneration();

		setGlobals();
		gameLoop();
	}

	public void setGlobals() {
		new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				long curTime = System.currentTimeMillis();
				if(curTime-startTime > 1000 )
				{
					setMotionEventHandlers();
					this.stop();
				}
			}
		}.start();
	}

	private static void setMotionEventHandlers() {
		Main.gamePageScene.setOnKeyPressed(e ->
		{
			switch (e.getCode()) {
				case LEFT:  turnLeft  = true;moveTime=System.currentTimeMillis(); break;
				case RIGHT: turnRight  = true;moveTime=System.currentTimeMillis(); break;
			}
		});

		Main.gamePageScene.setOnKeyReleased(e ->
		{
			switch (e.getCode()) {
				case LEFT: turnLeft  = false; break;
				case RIGHT: turnRight  = false; break;
			}
		});
	}

	private void explode(long last_time) {
		new AnimationTimer()
		{
			private final int delay = 10;

			//private void

			@Override
			public void handle(long now)
			{
				if(last_time-now>delay) {

				}
			}
		}.start();
	}

	private void gameLoop() {
		new AnimationTimer()
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
		}.start();
	}

	private void pauseTransitions() {
		int i = 0;
		while (i<transitions.size()) {
			transitions.get(i).pause();
			i+=1;
		}
	}

	private void playTransitions() {
		int i = 0;
		while (i<transitions.size()) {
			transitions.get(i).play();
			i+=1;
		}
	}

	private void moveBlocksUp(){
		for(int i=0;i < gameArea.getChildren().size();i++)
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

	////////////////////////////////////
	private void print(Object toPrint) {
		System.out.println(toPrint);
	}
	////////////////////////////////////

	public void startBlockGeneration()
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

	public void startTokenGeneration()
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

	public void optionSelected()
	{
		String choice = options.getValue();
		if(choice.equals("Restart"))
		{
			boolean ans = ConfirmBox.display("Confirm Restart", "Are you sure you want to restart?");
			if(ans)
			{
				// save data
				MainPageController.startGame();
			}
		}
		else if(choice.equals("Home"))
		{
			boolean ans = ConfirmBox.display("Confirm Exit", "Are you sure you want to quit?");
			if(ans)
			{
				// save data
				Main.mainStage.setScene(Main.mainPageScene);
			}
		}
	}

	//	public void displayPosition(MouseEvent event)
//	{
//		pos.setText("X = " + event.getX() + "		Y = " + event.getY());
//	}

}
