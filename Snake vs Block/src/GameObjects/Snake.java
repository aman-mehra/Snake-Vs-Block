package GameObjects;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Snake extends Line
{
	private int length;
	private StackPane head;
	private ArrayList<Ball> body;
	private double head_x, head_y;

	public Snake()
	{
		this.length = 4;
		head = new StackPane();
		Ball ball = new Ball("4", 250,400,15);
		Text text = new Text("4");
		text.setFont(Font.font(18));
		head.getChildren().addAll(ball, text);
		head.setTranslateX(ball.getCenterX() - 15);
		head.setTranslateY(ball.getCenterY() - 15);
		this.body = new ArrayList<Ball>();
		for(int i=1; i<4; i++)
			this.body.add(new Ball("", 250,400 + i*24,12));
		this.head_x = 250;
		this.head_y = 400;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public ArrayList<Ball> getBody()
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

	public void setBody(ArrayList<Ball> body)
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

	public void setHead_y(double head_y)
	{
		this.head_y = head_y;
	}
}
