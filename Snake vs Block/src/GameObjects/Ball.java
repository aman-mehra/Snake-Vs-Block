package GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class Ball extends Circle implements Token
{
	private static int idGenerator = 0;
	private Paint COLOUR = Color.valueOf("#FF69B4");
	private final String ID;
	private String value;
	private boolean active;

	public Ball(String value, double center_x, double center_y, double radius)
	{
		this.active=true;
		this.value = value;
		this.ID = "B" + idGenerator++;
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(radius);
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
