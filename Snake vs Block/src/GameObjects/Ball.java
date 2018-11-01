package GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle
{
	private static int idGenerator = 0;
	private final Paint COLOUR = Color.valueOf("00eeff");
	private final double RADIUS = 3;
	private final String ID;
	private int value;

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

}
