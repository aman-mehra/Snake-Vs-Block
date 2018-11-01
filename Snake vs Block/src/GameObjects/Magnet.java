package GameObjects;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Magnet extends Circle implements Token
{
	private static final Image img = new Image("file:///home/bhavye/Desktop/AP/Project/Snake%20vs%20Block/magnet.png");
	private static final double RADIUS = 15;
	private static final int TIME_TO_LIVE = 5;
	private static int idGenerator;
	private final String ID;
	private long startTime;
	private PathTransition transition;

	public Magnet(long startTime, double center_x, double center_y)
	{
		this.startTime = startTime;
		this.ID = "S" + idGenerator++;
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(RADIUS);
		this.setFill(new ImagePattern(img));
	}

	public long getStartTime()
	{
		return startTime;
	}

	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
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
