package GamePage;

import GameObjects.*;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import GameApplication.Main;

public class GamePageController implements Initializable
{
	private static final String[] COLOUR = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00","#722F37"};
	private static double animation_speed = 3;
	private static long offset = 1000;
	private static final double lambda = 0.7;
	
	public static final long mov_offset = 2;
	
	@FXML
	public Pane gameArea;
	@FXML
	public ComboBox<String> options;
	@FXML
	public Label score_box;
	
	public static Scene mainPageScene, gamePageScene;
    public static boolean turnLeft,turnRight;
    
    private ArrayList<PathTransition> transitions;
    
    //private static ArrayList<Integer> garbageObjects = new ArrayList<Integer>();
    
    private static int score;
    
	public Random rand;
	private long blockPrevTime;
	private long tokenPrevTime;
	private long startTime;
	private static long moveTime;
	private static long animation_last_time;
	
	private Snake snake;
	

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		rand = new Random();
		blockPrevTime = System.currentTimeMillis();
		animation_last_time = System.currentTimeMillis();
		moveTime = 0;
		tokenPrevTime = (System.currentTimeMillis() + offset/2);
		startTime = System.currentTimeMillis();

		options.getItems().addAll("Restart", "Exit");

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
					gamePageScene = Main.GamePageScene;
					setMotionEventHandlers();
					this.stop();
				}
			}
		}.start();
	}
	
	private static void setMotionEventHandlers() {
		gamePageScene.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                     case LEFT:  turnLeft  = true;moveTime=System.currentTimeMillis(); break;
                     case RIGHT: turnRight  = true;moveTime=System.currentTimeMillis(); break;
                }
            }
        });
		
		gamePageScene.setOnKeyReleased((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT: turnLeft  = false; break;
                    case RIGHT: turnRight  = false; break;
                }
            }
        });
	}
	
	private void explode(Block block) {
		
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
	
    private void gameLoop() {
    	new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{	
				//print(now-moveTime);
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
						if(stackPane.getChildren().get(0).getClass() == Ball.class) {//checks collision with ball
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
						
						if(stackPane.getChildren().get(0).getClass() == Block.class) {//check collision with block
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
										explode(block);/////////////////////////////////EXPLOSION????????????????
										playTransitions();
									}
									else {
										//print(value);
										pauseTransitions();
										//moveBlocksUp();
										snake.moveBack();
										block.setValue(value-1);
										int id=snake.decreaseLength();
										deleteSnakePart(id);
										try {
											Thread.sleep(80);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
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
					stackPane.setTranslateY(stackPane.getTranslateY()-50);
	    		}
			}
    		else if(gameArea.getChildren().get(i).getClass() == Magnet.class) {
    			Magnet token = (Magnet) gameArea.getChildren().get(i);
    			token.setTranslateY(token.getTranslateY()-50);
    		}
    		else if(gameArea.getChildren().get(i).getClass() == Shield.class) {
    			Shield token = (Shield) gameArea.getChildren().get(i);
    			token.setTranslateY(token.getTranslateY()-50);
    		}
    		else if(gameArea.getChildren().get(i).getClass() == DestroyBlocks.class) {
    			DestroyBlocks token = (DestroyBlocks) gameArea.getChildren().get(i);
    			token.setTranslateY(token.getTranslateY()-50);
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
		int i = rand.nextInt(COLOUR.length);
		return Color.valueOf(COLOUR[i]);
	}
	
	public void deleteSnakePart(int id) {
		for (int i=0;i<gameArea.getChildren().size();i++) {
			if(gameArea.getChildren().get(i).getClass()==SnakePart.class) {
				SnakePart temp = (SnakePart)gameArea.getChildren().get(i);
				if(temp.getPId()==id) {
					gameArea.getChildren().get(i).setVisible(false);
					gameArea.getChildren().remove(i);
				}
			}		
		}
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
			
			else if(gameArea.getChildren().get(i).getClass() == ExplosionPart.class)
			{
				ExplosionPart token = (ExplosionPart) gameArea.getChildren().get(i);
				if(token.getTranslateY() > 800 || token.getTranslateY() < -50 || token.getTranslateX() > 500 || token.getTranslateX() < -50)
					gameArea.getChildren().remove(i);
			}
			i++;
		}
	}

	public int getNextBallNumber() {
	    return  (int)(Math.ceil(Math.log(1-rand.nextDouble())/(-lambda)));
	}

}
