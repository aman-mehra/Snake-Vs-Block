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
	
	private static final int radius = 11;
	private static final int part_radius = 5;
	private static final int ball_radius = 15;
	private static final double displace = 6.1;
	private static final double acc = 0.15;
	private static double vel;
	
	private static boolean leftMoveBlock=false;
	private static boolean rightMoveBlock=false;
 
	public Snake()
	{
		this.length = 5; 
		this.head_x = 250;
		this.head = new StackPane();
		
		this.body = new ArrayList<SnakePart>();
		initialize_snake();
	}
	
	public void initialize_snake() {
		SnakePart part = new SnakePart(head_x,head_y,radius);
		vel=displace;
		Text text = new Text("5");
		text.setFont(Font.font(16));
		head.getChildren().addAll(part, text);
		head.setTranslateX(part.getCenterX() - radius);
		head.setTranslateY(part.getCenterY() - radius);
		for(int i=1; i<this.length; i++)
			this.body.add(new SnakePart(head_x,head_y +(radius-part_radius)+i*(part_radius*2),part_radius));
	}
	
	public void moveAhead() {
		if(head.getTranslateY()!=head_y) {
			head.setTranslateY(head_y-radius);
		}
	}
	
	public void moveBack() {
		if(head.getTranslateY()==head_y) {
			head.setTranslateY(head_y-20);
		}
	}
	
	public void moveSnake(int dir) {
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
		head.setTranslateX((prev_x+dir*vel) );
		prev_x+=radius;
		for (int i = 0 ; i<this.body.size();i++) {
			temp_x=this.body.get(i).getCenterX();
			this.body.get(i).setCenterX(prev_x);
			prev_x=temp_x;
		}		
		
	}
	
	public boolean collision(StackPane ball) {
		double dist = Math.pow((ball.getTranslateX()-head.getTranslateX()),2)+Math.pow((ball.getTranslateY()-head_y),2);
		double limit = ((ball_radius+radius)*(ball_radius+radius));
		//System.out.println(head.getTranslateX()+":"+head.getTranslateY()+":::"+ball.getTranslateX()+":"+ball.getTranslateY());
		if (dist<limit) {
			return true;
		}
		return false;
	}
	
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
	
	public int decreaseLength() {
		int p_id=-1;
		if(this.body.size()>0) {
			p_id=this.body.get(this.body.size()-1).getPId();
			this.body.remove(this.body.size()-1);
		}
		changeLengthNumber(-1);
		return p_id;
	}
	
	private void changeLengthNumber(int change) {
		Text temp=((Text)this.head.getChildren().get(1));
		temp.setText(Integer.toString(Integer.parseInt(temp.getText())+change));
		this.length+=change;
	}
	
	public void setLeftBlock(boolean flag) {
		leftMoveBlock=flag;
	}
	
	public void setRightBlock(boolean flag) {
		rightMoveBlock=flag;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public ArrayList<SnakePart> getBody()
	{
		return body;
	}

	public StackPane getHead()
	{
		return head;
	}

	public void setHead(StackPane head)
	{
		this.head = head;
	}

	public void setBody(ArrayList<SnakePart> body)
	{
		this.body = body;
	}

	public double getHead_x()
	{
		return head_x;
	}

	public void setHead_x(double head_x)
	{
		this.head_x = head_x;
	}

	public double getHead_y()
	{
		return head_y;
	}

}
