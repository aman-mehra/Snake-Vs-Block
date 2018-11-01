package GameObjects;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Ball extends Circle implements Token
{
	private static int idGenerator = 0;
	private final Paint COLOUR = Color.valueOf("#00EEFF");
	private final double RADIUS = 15;
	private final String ID;
	private int value;
	private PathTransition transition;

	public Ball(int value, double center_x, double center_y)
	{
		this.value = value;
		this.ID = "B" + idGenerator++;
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(RADIUS);
		this.setFill(COLOUR);
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public String getID()
	{
		return ID;
	}

	public PathTransition getTransition()
	{
		return transition;
	}

	public void setTransition(PathTransition transition, Line path, Duration duration)
	{
		this.transition = transition;
		this.transition.setNode(this);
		this.transition.setPath(path);
		this.transition.setDuration(duration);
	}
}
