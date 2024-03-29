package GameObjects;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Snake
{
	private int length;
	private StackPane head;
	private ArrayList<SnakePart> body;
	private double head_x;
	private final double head_y=400;
	
	private boolean activeShield = false;
	private boolean activeMagnet = false;
	
	private long shieldStartTime;
	private long magnetStartTime;

	private static final int radius = 11;
	private static final int part_radius = 7;
	private static final int ball_radius = 15;
	private static final int magnet_radius = 150;
	private static final double displace = 7.1;
	private static final double acc = 0.32;
	private static final int TIME_TO_LIVE = 4000;
	private static double vel;
	
	private static boolean leftMoveBlock=false;
	private static boolean rightMoveBlock=false;

	/**
	 * Creates instance of Snake with default values
	 *
	 * @author Aman
	 */
	public Snake()
	{
		this.length = 5; 
		this.head_x = 250;
		this.head = new StackPane();
		this.body = new ArrayList<SnakePart>();
		initialize_snake();
	}

	/**
	 * Creates instance of Snake with parametrised values
	 *
	 * @param length length of snake
	 * @param head_x x coordinate of head
	 * @author Bhavye
	 */
	public Snake(int length, double head_x)
	{
		this.length = length;
		this.head_x = head_x;
		this.head = new StackPane();
		this.body = new ArrayList<SnakePart>();
		initialize_snake();
	}
	
	/**
	 * Initializes snake to default values
	 * @author Bhavye, Aman M
	 */
	public void initialize_snake() {
		SnakePart part = new SnakePart(head_x,head_y,radius);
		vel=displace;
		Text text = new Text(length + "");
		text.setFont(Font.font(16));
		head.getChildren().addAll(part, text);
		head.setTranslateX(part.getCenterX() - radius);
		head.setTranslateY(part.getCenterY() - radius);
		for(int i=1; i<this.length; i++)
			this.body.add(new SnakePart(head_x,head_y +(radius-part_radius)+i*(part_radius*2),part_radius));
	}
	
	/**
	 * Starts shield timer
	 * @author Aman M
	 */
	public void shieldStart()
	{
		shieldStartTime=System.currentTimeMillis();
	}

	/**
	 * Returns end of timer
	 * @return
	 * @author Aman M
	 */
	public boolean shieldHasExpired()
	{
		if (System.currentTimeMillis()-shieldStartTime>TIME_TO_LIVE) {
			return true;
		}
		return false;
	}
	
	/**
	 * Starts magnet timer
	 * @author Aman M
	 */
	public void magnetStart()
	{
		shieldStartTime=System.currentTimeMillis();
	}
	
	/**
	 * Returns end of timer
	 * @return
	 * @author Aman M
	 */
	public boolean magnetHasExpired()
	{
		if (System.currentTimeMillis()-shieldStartTime>TIME_TO_LIVE) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns shield activity
	 * @return
	 * @author Aman M
	 */
	public boolean getActiveShield() {
		return activeShield;
	}

	/**
	 * Alters shield activity
	 * @param activeShield
	 * @author Aman M
	 */
	public void setActiveShield(boolean activeShield) {
		this.activeShield = activeShield;
		if(this.activeShield) {
			for(SnakePart part : body) {
				part.setShieldColor();
			}
		}
		else {
			for(SnakePart part : body) {
				part.unsetTokenColor();
			}
		}
	}

	/**
	 * Returns magnet activity
	 * @return
	 * @author Aman M
	 */
	public boolean getActiveMagnet() {
		return activeMagnet;
	}

	/**
	 * Sets Magnet Activity
	 * @param activeMagnet
	 * @author Aman M
	 */
	public void setActiveMagnet(boolean activeMagnet) {
		this.activeMagnet = activeMagnet;
		SnakePart h = (SnakePart)head.getChildren().get(0);
		if(this.activeMagnet) {
			h.setMagnetColor();
		}
		else {
			h.unsetTokenColor();
		}
	}
	
	/**
	 * Moves snake forward
	 * @author Aman M
	 */
	public void moveAhead() {
		if(head.getTranslateY()!=head_y) {
			head.setTranslateY(head_y-radius);
		}
	}
	
	/**
	 * Moves snake backwards
	 * @author Aman M
	 */
	public void moveBack() {
		if(head.getTranslateY()==head_y) {
			head.setTranslateY(head_y-20);
		}
	}
	
	/**
	 * Moves snake horizontally
	 * @param dir
	 * @author Aman M
	 */
	public void moveSnake(int dir) {
		if(dir==1 && this.rightMoveBlock) {
			vel=displace;
			dir=-1;
		}
		else if(dir==-1 && this.leftMoveBlock) {
			vel=displace;
			dir=1;
		}
		double prev_x = head.getTranslateX();
		if (dir==0) {
			vel=displace;
		}
		else {
			vel+=acc;
		}
		
		if(prev_x<5 && dir==-1 ) {
			vel=displace;
			dir=1;
		}
		else if(prev_x>485 && dir==1) {
			vel=displace;
			dir=-1;
		}
		
		double temp_x;

		head.setTranslateX((prev_x + (dir*vel)));
		//head.setTranslateX((prev_x + (dir*vel)/10));

		prev_x+=radius;
		for (int i = 0 ; i<this.body.size();i++) {
			temp_x=this.body.get(i).getCenterX();
			this.body.get(i).setCenterX(prev_x);
			prev_x=temp_x;
		}		
		
	}
	
	/**
	 * Checks for collision with a ball
	 * @param ball
	 * @return
	 * @author Aman M
	 */
	public boolean collision(StackPane ball) {
		double dist = Math.pow((ball.getTranslateX()-head.getTranslateX()),2)+Math.pow((ball.getTranslateY()-head_y),2);
		double limit = ((ball_radius+radius)*(ball_radius+radius));
		if(this.getActiveMagnet()) {
			limit = magnet_radius*magnet_radius;
		}
		if (dist<limit) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks for collision with a token
	 * @param token
	 * @return
	 * @author Aman M
	 */
	public boolean collision(Token token) {
		
		double dist = Math.pow((token.getX()-head.getTranslateX()),2)+Math.pow((token.getY()-head_y),2);
		double limit = ((ball_radius+radius)*(ball_radius+radius));
		if (dist<limit) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks for collision with a block
	 * @param pane
	 * @param block
	 * @return
	 * @author Aman M
	 */
	public boolean blockCollision(StackPane pane,Block block) {
		double vert_dist = pane.getTranslateY()+block.getSIDE()-head_y;
		double limit = radius+2;
		
		if (Math.abs(vert_dist)<limit) {
			double hor_dist = head.getTranslateX()-pane.getTranslateX();
			if(hor_dist<block.getSIDE()+radius/2 && hor_dist>-radius) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks for sideways collision with block
	 * @param pane
	 * @param block
	 * @author Aman M
	 */
	public void sideWaysBlock(StackPane pane,Block block) {
		double right_hor_dist = head.getTranslateX() - (pane.getTranslateX()+block.getSIDE());//right with respect to top_left of block
		double left_hor_dist = pane.getTranslateX() - head.getTranslateX();//left with respect to top_left of block
		double limit = radius+2;
		
		if (Math.abs(right_hor_dist)<limit-radius/2) {
			double vert_dist = head_y-pane.getTranslateY();
			if(vert_dist<block.getSIDE()+10 && vert_dist>-radius) {
				this.leftMoveBlock = true;
			}
		}
		if(Math.abs(left_hor_dist)<limit+radius/2) {
			double vert_dist = head_y-pane.getTranslateY();
			if(vert_dist<block.getSIDE()+10 && vert_dist>-radius) {
				this.rightMoveBlock = true;
			}
		}	
		
	}
	
	/**
	 * Checks for sideways collision with wall
	 * @param pane
	 * @param wall
	 * @author Aman M
	 */
	public void sideWaysWall(StackPane pane,Wall wall) {
		double right_hor_dist = head.getTranslateX() - (pane.getTranslateX() + wall.getBreadth());//right with respect to wall
		double left_hor_dist = pane.getTranslateX() - head.getTranslateX();//left with respect to wall
		double limit = radius+2;
		
		if (right_hor_dist<limit-radius/2+5 && right_hor_dist>-5) {
			double vert_dist = head_y-pane.getTranslateY();
			if(vert_dist<wall.getLength()+10 && vert_dist>-radius) {
				this.leftMoveBlock = true;
			}
		}
		if(left_hor_dist<limit+radius+5 && left_hor_dist>-5) {
			double vert_dist = head_y-pane.getTranslateY();
			if(vert_dist<wall.getLength()+10 && vert_dist>-radius) {
				this.rightMoveBlock = true;
			}
		}	
		
	}
	
	/**
	 * Increases snake length
	 * @param num
	 * @author Aman M
	 */
	public void increaseLength(int num) {
		double last_y;
		if(this.body.size()>0) {
			last_y = this.body.get(this.body.size()-1).getCenterY();
		}
		else {
			last_y = head.getTranslateY()+(radius+part_radius);
		}
		for(int i=1; i<=num; i++) {
			this.body.add(new SnakePart(head.getTranslateX()+radius,last_y + i*(part_radius*2),part_radius));
		}
		changeLengthNumber(num);
	}
	
	/**
	 * Decreases snake length
	 * @return
	 * @author Aman M
	 */
	public int decreaseLength() {
		int p_id=-1;
		if(this.body.size()>0) {
			p_id=this.body.get(this.body.size()-1).getPId();
			this.body.remove(this.body.size()-1);
		}
		changeLengthNumber(-1);
		return p_id;
	}
	
	/**
	 * Alter length internally
	 * @param change
	 * @author Aman M
	 */
	private void changeLengthNumber(int change) {
		Text temp=((Text)this.head.getChildren().get(1));
		temp.setText(Integer.toString(Integer.parseInt(temp.getText())+change));
		this.length+=change;
	}
	
	/**
	 * Block leftward motion
	 * @param flag
	 * @author Aman M
	 */
	public void setLeftBlock(boolean flag) {
		leftMoveBlock=flag;
	}
	
	/**
	 * Blocks rightward motion
	 * @param flag
	 * @author Aman M
	 */
	public void setRightBlock(boolean flag) {
		rightMoveBlock=flag;
	}
	
	/**
	 * Length Getter
	 * @return
	 * @author Bhavye
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * Length setter
	 * @param length
	 * @author Bhavye
	 */
	public void setLength(int length)
	{
		this.length = length;
	}

	/**
	 * Body Getter
	 * @return
	 * @author Bhavye
	 */
	public ArrayList<SnakePart> getBody()
	{
		return body;
	}

	/**
	 * Head Getter
	 * @return
	 * @author Bhavye
	 */
	public StackPane getHead()
	{
		return head;
	}
	
	/**
	 * Head setter
	 * @param head
	 * @author Bhavye
	 */
	public void setHead(StackPane head)
	{
		this.head = head;
	}

	/**
	 * Body setter
	 * @param body
	 * @author Bhavye
	 */
	public void setBody(ArrayList<SnakePart> body)
	{
		this.body = body;
	}

	/**
	 * Head x-coordinate Getter
	 * @return
	 * @author Bhavye
	 */
	public double getHead_x()
	{
		return head_x;
	}

	/**
	 * Head x setter
	 * @param head_x
	 * @author Bhavye
	 */
	public void setHead_x(double head_x)
	{
		this.head_x = head_x;
	}

	/**
	 * Head y-coordinate Getter
	 * @return
	 * @author Bhavye
	 */
	public double getHead_y()
	{
		return head_y;
	}

}
