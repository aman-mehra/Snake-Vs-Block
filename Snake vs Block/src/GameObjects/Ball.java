package GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class Ball extends Circle
{
	private Paint COLOUR = Color.valueOf("#FF69B4");
	private final String ID = "B";
	private String value;
	private boolean active;

	public Ball(String value, double center_x, double center_y, double radius)
	{
		super(center_x, center_y, radius);
		this.active=true;
		this.value = value;
		this.setFill(COLOUR);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void eraseBall() {
		this.COLOUR = Color.valueOf("#FFFFFF");
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
