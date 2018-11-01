package GameObjects;

import javafx.animation.PathTransition;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Block extends Rectangle
{
	private static final int SIDE = 100;
	private static int idGenerator;
	private final String ID;
	private int value;
	private PathTransition transition;

	public Block(int value, double topleft_x, double topleft_y, Paint colour)
	{
		this.value = value;
		this.ID = "BL" + idGenerator++;
		this.setHeight(SIDE); this.setWidth(SIDE);
		this.setX(topleft_x); this.setY(topleft_y);
		this.setArcHeight(30); this.setArcWidth(30);
		this.setFill(colour);
	}

	public static int getSIDE()
	{
		return SIDE;
	}

	public static int getIdGenerator()
	{
		return idGenerator;
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
