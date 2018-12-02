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

	/**
	 * Checks Item Actv=ivity
	 * @return
	 * @author Aman M
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Alters Activity of Item
	 * @param active
	 * @author Aman M
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Makes ball invisible
	 * @author Aman M
	 */
	public void eraseBall() {
		this.COLOUR = Color.valueOf("#FFFFFF");
	}

	/**
	 * Returns ball value
	 * @return
	 * @author Bhavye
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets ball value
	 * @param value
	 * @author Bhavye
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * Returns object ID
	 * @return
	 * @author Bhavye
	 */
	public String getID()
	{
		return ID;
	}

}
