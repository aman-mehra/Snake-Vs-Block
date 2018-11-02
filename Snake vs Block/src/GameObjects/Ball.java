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
	private final String ID;
	private String value;

	public Ball(String value, double center_x, double center_y, double radius)
	{
		this.value = value;
		this.ID = "B" + idGenerator++;
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(radius);
		this.setFill(COLOUR);
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getID()
	{
		return ID;
	}

}
