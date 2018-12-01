package GamePage;

import GameApplication.Main;
import GameObjects.*;
import HomePage.HomePageController;
import LeaderboardPage.LeaderboardEntry;
import PopupBoxes.ConfirmBox;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
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
	private static final String hex = "123456789ABCDE";
	private static final Random rand = new Random();

	private long score;
	private String date;
	private static double animation_speed = 3;// 1.8 - 3
	private static long offset = (long)(1000*(1+(animation_speed-3)/3));
	
	public static Scene mainPageScene, gamePageScene;
	public static boolean turnLeft,turnRight;
	
	private long blockPrevTime;
	private long tokenPrevTime;
	private long blockCurrentTime;
	private long tokenCurrentTime;
	private long startTime;
	private static boolean isPaused = false;
	private static long moveTime;
	
	private ArrayList<PathTransition> transitions;
	/*private AnimationTimer blockTimer, tokenTimer, snakeMovementTimer, gameLoopTimer;
	private long blockPrevTime, tokenPrevTime;
	private long startTime, moveTime;*/
	
	private Snake snake;
	
	private boolean holocaust = false;


	public void setUpGamePage()
	{
		date = "";
		transitions = new ArrayList<PathTransition>();
		gameArea.getChildren().removeAll();
		turnLeft = false; turnRight = false;
		//blockTimer = null; tokenTimer = null; snakeMovementTimer = null; gameLoopTimer = null;
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

	public void setGlobals() {
		new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				long curTime = System.currentTimeMillis();
				if(curTime-startTime > 2000 )
				{
					startBlockGeneration();
					startTokenGeneration();
					gamePageScene = Main.gamePageScene;
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
		updateScore(block.getValue());
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
	
	private void collectAnimation(double start_position_x,double start_position_y) {
		double path_time = 0.5;
		for(int shards = 0 ; shards<30; shards++) {
			Line path = new Line();
			path.setStartX(start_position_x); path.setStartY(start_position_x);
			path.setEndX(rand.nextInt(900)-300); path.setEndY(-100);
			ExplosionPart debris = new ExplosionPart( path.getStartX(), path.getStartY());
			debris.setCollectionColor();
			debris.setCollectionRadius();
			PathTransition transition = new PathTransition(Duration.seconds(path_time+rand.nextDouble()*(0.3)), path, debris);
			transition.play();
			gameArea.getChildren().add(debris);
		}
		
		for(int shards = 0 ; shards<30; shards++) {
			Line path = new Line();
			path.setStartX(start_position_x); path.setStartY(start_position_x);
			path.setEndX(rand.nextInt(900)-300); path.setEndY(1100);
			ExplosionPart debris = new ExplosionPart( path.getStartX(), path.getStartY());
			debris.setCollectionColor();
			debris.setCollectionRadius();
			PathTransition transition = new PathTransition(Duration.seconds(path_time+rand.nextDouble()*(0.3)), path, debris);
			transition.play();
			gameArea.getChildren().add(debris);
		}
		
		for(int shards = 0 ; shards<30; shards++) {
			Line path = new Line();
			path.setStartX(start_position_x); path.setStartY(start_position_x);
			path.setEndX(-600); path.setEndY(rand.nextInt(1500)-300);
			ExplosionPart debris = new ExplosionPart( path.getStartX(), path.getStartY());
			debris.setCollectionColor();
			debris.setCollectionRadius();
			PathTransition transition = new PathTransition(Duration.seconds(path_time+rand.nextDouble()*(0.3)), path, debris);
			transition.play();
			gameArea.getChildren().add(debris);
		}
		
		for(int shards = 0 ; shards<30; shards++) {
			Line path = new Line();
			path.setStartX(start_position_x); path.setStartY(start_position_x);
			path.setEndX(700); path.setEndY(rand.nextInt(1500)-300);
			ExplosionPart debris = new ExplosionPart( path.getStartX(), path.getStartY());
			debris.setCollectionColor();
			debris.setCollectionRadius();
			PathTransition transition = new PathTransition(Duration.seconds(path_time+rand.nextDouble()*(0.3)), path, debris);
			transition.play();
			gameArea.getChildren().add(debris);
		}
	}
	
	private void ballCollection(StackPane pane,Ball ball) {
		double start_position_x = pane.getTranslateX()+(rand.nextDouble()*(ball.getRadius()/4)-(ball.getRadius()/8));
		double start_position_y = pane.getTranslateY()+(rand.nextDouble()*(ball.getRadius()/4)-(ball.getRadius()/8));
		collectAnimation(start_position_x,start_position_y);	
	}
	
	private void tokenCollection(Token token) {
		double start_position_x = token.getX()+(rand.nextDouble()*(15.0/4)-(15.0/8));
		double start_position_y = token.getY()+(rand.nextDouble()*(15.0/4)-(15.0/8));
		collectAnimation(start_position_x,start_position_y);	
	}
	
	private void wallsHandler(StackPane stackPane,int i) {
		Wall wall=(Wall)stackPane.getChildren().get(0);
		snake.sideWaysWall(stackPane, wall);
	}
	
	private void ballHandler(StackPane stackPane,int i) {
		Ball ball=(Ball)stackPane.getChildren().get(0);
		int value = Integer.parseInt(ball.getValue());
		if(snake.collision(stackPane) && ball.isActive()) {
			updateScore(value);
			snake.increaseLength(value);
			ballCollection(stackPane,ball);
			ball.setActive(false);
			gameArea.getChildren().get(i).setVisible(false);
			gameArea.getChildren().addAll(snake.getBody().subList(snake.getBody().size()-value, snake.getBody().size()));
		}
		
	}
	
	private void blocksHandler(StackPane stackPane,int i) {
		Block block=(Block)stackPane.getChildren().get(0);
		snake.sideWaysBlock(stackPane, block);
		int value = block.getValue();
		if(snake.blockCollision(stackPane,block) && block.isActive()) {
			
			if(!snake.getActiveShield()) {
			
				if (value<snake.getLength()) {
					if(value<=5) {
						block.setActive(false);
						gameArea.getChildren().get(i).setVisible(false);
						for(int j=0;j<value;j++) {
							int id=snake.decreaseLength();
							deleteSnakePart(id);
						}
						explode(block);
						snake.setRightBlock(false);
						snake.setLeftBlock(false);
						playTransitions();
					}
					else {
						pauseTransitions();
						snake.setRightBlock(true);
						snake.setLeftBlock(true);
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
					///////TO DO
				}
				
			}
			
			else {
				
				block.setActive(false);
				gameArea.getChildren().get(i).setVisible(false);
				explode(block);
				playTransitions();
			}
		}
	}
	
	private void destroyBlocksHandler(DestroyBlocks token,int i) {
		holocaust=true;
		tokenCollection(token);
		token.setActive(false);
		gameArea.getChildren().get(i).setVisible(false);
		for(int j=0;j < gameArea.getChildren().size();j++) {
			if(gameArea.getChildren().get(i).getClass() == StackPane.class)
			{
				StackPane stackPane = (StackPane) gameArea.getChildren().get(j);
				if(stackPane.getChildren().get(0).getClass() == Block.class) {
					Block block=(Block)stackPane.getChildren().get(0);
					if(block.isActive()) {
						int value = block.getValue();
						token.updateScoreCollected(value);
					}
				}
			}
		}
	}
	
	private void shieldHandler() {
		
		snake.setActiveShield(!snake.shieldHasExpired());
	}
	
	private void magnetHandler() {
		
		snake.setActiveMagnet(!snake.magnetHasExpired());
	}

	private void gameLoop()
	{
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
				
				snake.setLeftBlock(false);
				snake.setRightBlock(false);
				for(int i=0;i < gameArea.getChildren().size();i++)
				{
					if(gameArea.getChildren().get(i).getClass() == StackPane.class)
					{
						StackPane stackPane = (StackPane) gameArea.getChildren().get(i);
						if(stackPane.getChildren().get(0).getClass() == Ball.class) {//checks collision with ball
							ballHandler(stackPane,i);
						}
						
						else if(stackPane.getChildren().get(0).getClass() == Block.class) {//check collision with block
							blocksHandler(stackPane,i);
						}
						
						else if(stackPane.getChildren().get(0).getClass() == Wall.class) {//check collision with wall
							wallsHandler(stackPane,i);
						}
					}
					
					else if(gameArea.getChildren().get(i).getClass() == Magnet.class)
					{
						Magnet token = (Magnet) gameArea.getChildren().get(i);
						if(snake.collision(token) && token.isActive()) {
							token.setActive(false);
							snake.magnetStart();
							//activeMagnets.add(token);
							tokenCollection(token);
							gameArea.getChildren().get(i).setVisible(false);
							snake.setActiveMagnet(true);
						}
						
					}
					else if(gameArea.getChildren().get(i).getClass() == Shield.class)
					{
						Shield token = (Shield) gameArea.getChildren().get(i);
						if(snake.collision(token) && token.isActive()) {
							token.setActive(false);
							snake.shieldStart();
							//activeShields.add(token);
							tokenCollection(token);
							gameArea.getChildren().get(i).setVisible(false);
							snake.setActiveShield(true);
						}
						
					}
					else if(gameArea.getChildren().get(i).getClass() == DestroyBlocks.class)
					{
						DestroyBlocks token = (DestroyBlocks) gameArea.getChildren().get(i);
						if(snake.collision(token) && token.isActive()) {
							destroyBlocksHandler(token,i);
						}
						
					}
					 
				}
				
				if (holocaust==true) {
					holocaust=false;
					for(int i=0;i < gameArea.getChildren().size();i++) {
						if(gameArea.getChildren().get(i).getClass() == StackPane.class)
						{
							StackPane stackPane = (StackPane) gameArea.getChildren().get(i);
							if(stackPane.getChildren().get(0).getClass() == Block.class) {
								Block block=(Block)stackPane.getChildren().get(0);
								if(block.isActive()) {
									block.setActive(false);
									gameArea.getChildren().get(i).setVisible(false);
									explode(block);
								}
							}
						}
					}
				}
				
				if(snake.getActiveMagnet()) {
					magnetHandler();
				}
				
				if(snake.getActiveShield()) {
					shieldHandler();
				}
								
				snake.moveAhead();
				
				speedModeration();
				
				//animation_speed=3;
				//offset=(long)(8-animation_speed)*300;//for speed = 4
				//offset=(long)(8-animation_speed)*200;//for speed = 3
				//offset=(long)(8-animation_speed)*150;for speed = 2
			}
		}.start();
	}
	
	private void pauseTransitions() {
    	int i = 0;
    	while (i<transitions.size()) {
    		transitions.get(i).pause();
    		i+=1;
    	}
    	this.isPaused = true;
    }
    
    private void playTransitions() {
    	int i = 0;
    	while (i<transitions.size()) {
    		transitions.get(i).play();
    		i+=1;
    	}
    	this.isPaused = false;
    }
    
   private void speedModeration() {
	   animation_speed = Math.max(3-(this.score/250.0),1.8);
	   offset = (long)(1000*(1+(animation_speed-3)/6));
   }

	private void startBlockGeneration()
	{

		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				if(isPaused) {
					blockPrevTime = System.currentTimeMillis() - (blockCurrentTime-blockPrevTime);
				}
				blockCurrentTime = System.currentTimeMillis();
				if(blockCurrentTime > blockPrevTime + offset)
				{
					if(!isPaused) {
						generateBlocks();
						blockPrevTime = System.currentTimeMillis();
					}
				}
				deleteGarbage();
			}
		};
		timer.start();

	}

	private void startTokenGeneration()
	{

		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{	
				if(isPaused) {
					tokenPrevTime = System.currentTimeMillis() - (tokenCurrentTime-tokenPrevTime );
				}
				tokenCurrentTime = System.currentTimeMillis();
				
				if((tokenCurrentTime > tokenPrevTime + offset) && (tokenCurrentTime-blockPrevTime>offset*0.4*(1+(3-animation_speed)/2)) )
				{
					generateToken();
					tokenPrevTime = System.currentTimeMillis();
						
				}
				deleteGarbage();
			}
		};
		timer.start();

	}

	////////////////////////////////////
	private void print(Object toPrint)
	{
		System.out.println(toPrint);
	}
	////////////////////////////////////

	public void generateBlocks()//generates blocks and walls
	{
		int[] indices = new int[5];
		int wall_flip = rand.nextInt(7)+1;
		int wall_flip_2 = rand.nextInt(16)+1;
		while(wall_flip==wall_flip_2) {
			wall_flip_2 = rand.nextInt(16)+1;
		}
		for(int i = 0;i<5;i++) {
			int flip = rand.nextInt(2);
			if(indices[i]==0) {
				indices[i]=1;
			}
			if(flip==1) {
				indices[i]=2;
			}
			if((wall_flip==i || wall_flip_2==i) && i!=4 ) {
				indices[i]=indices[i]*3;
			}
		}
		int num_blocks = indices.length;
		int passable = -1;
		if(num_blocks==5) {
			passable = rand.nextInt(num_blocks);
		}
		for(int i=0; i<5; i++)
		{
			if(indices[i]%2==0) {
				Line path = getBlockPath(i);
				int block_value = rand.nextInt(snake.getLength())+(snake.getLength()/4)+3;
				if(passable>=0 && i==indices[passable]) {
					block_value = rand.nextInt(Math.max(snake.getLength()-2,1))+1;
				}
				Block block = new Block(block_value, path.getStartX(),path.getStartY(),getRandomColour());
				Text text = new Text(block.getValue() + "");
				text.setFont(Font.font(40));
				StackPane stackPane = new StackPane();
				stackPane.getChildren().addAll(block, text);
				gameArea.getChildren().add(stackPane);
				PathTransition transition = new PathTransition(Duration.seconds(animation_speed), path, stackPane);
				transition.play();
				transitions.add(transition);		
			}
			if(indices[i]%3==0) {
				Line path = getWallPath(i);
				int wall_length = rand.nextInt(150)+100;
				Wall wall = new Wall(path.getStartX(),path.getStartY(),wall_length);
				StackPane stackPane = new StackPane();
				stackPane.getChildren().addAll(wall);
				gameArea.getChildren().add(stackPane);
				PathTransition transition = new PathTransition(Duration.seconds(animation_speed*(17.0/11)), path, stackPane);
				transition.play();
				transitions.add(transition);
			}
		}
	}

	public void generateToken()
	{
		Line path = getTokenPath();
		int idx = rand.nextInt(100);

		if(idx < 15 || idx > 80)
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

		else if(idx < 25)
		{
			Magnet magnet = new Magnet(path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(animation_speed), path, magnet);
			transition.play();
			transitions.add(transition);
			gameArea.getChildren().add(magnet);
		}

		else if(idx < 35)
		{
			Shield shield = new Shield( path.getStartX(), path.getStartY());
			PathTransition transition = new PathTransition(Duration.seconds(animation_speed), path, shield);
			transition.play();
			transitions.add(transition);
			gameArea.getChildren().add(shield);
		}

		else if(idx < 42)
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
	
	public Line getWallPath(int index)
	{
		Line path = new Line();
		path.setStartX(100 + index * 100); path.setStartY(-700-(3-animation_speed)*120);
		path.setEndX(100 + index * 100); path.setEndY(1000-(3-animation_speed)*120);
		return path;
	}

	public Line getTokenPath()
	{
		Line path = new Line();
		int position_x = rand.nextInt(440) + 30;
		boolean condition1 = (position_x<120 && position_x >84) || (position_x<220 && position_x >184);
		boolean condition2 = (position_x<320 && position_x >284) || (position_x<420 && position_x >384);
		boolean condition = condition1 || condition2;
		while(condition)   {
			position_x = rand.nextInt(440) + 30;
			condition1 = (position_x<120 && position_x >84) || (position_x<220 && position_x >184);
			condition2 = (position_x<320 && position_x >284) || (position_x<420 && position_x >384);
			condition = condition1 || condition2;
		}
		path.setStartX(position_x); path.setStartY(-100);
		path.setEndX(position_x); path.setEndY(1000);
		return path;
	}

	public Paint getRandomColour()
	{
		String color = "#";
		for(int i=0; i < 6;i++) {
			color+=hex.charAt(rand.nextInt(14));
		}
		return Color.valueOf(color);
	}

	public int getNextBallNumber() {
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
			
			else if(gameArea.getChildren().get(i).getClass() == ExplosionPart.class)
			{
				ExplosionPart token = (ExplosionPart) gameArea.getChildren().get(i);
				if(token.getTranslateY() > 800 || token.getTranslateY() < -50 || token.getTranslateX() > 500 || token.getTranslateX() < -50)
					gameArea.getChildren().remove(i);
			}
			i++;
		}
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

	private void updateScore(int upd) {
		this.score+=upd;
		this.score_box.setText("Score : "+score+" ");
	}

	public void optionSelected() throws IOException, ClassNotFoundException
	{
		//pauseTimers();
		isPaused=true;
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
				//playTimers();
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
				//playTimers();
				playTransitions();
			}
		}
	}

	public GameState getCurrentGameState()
	{
		GameState gameState = new GameState(score, date, offset, animation_speed, snake.getHead_x(), snake.getLength(), gameArea.getChildren().size() - snake.getLength() - 1);
		//add data of gameObjects
		return gameState;
	}

	public String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
