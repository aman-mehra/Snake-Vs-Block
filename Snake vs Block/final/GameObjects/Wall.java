package GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Wall extends Rectangle
{
	private static final int BREADTH = 4;
	private final String ID = "W";
	private int length;
	
	public Wall(double topleft_x, double topleft_y, int length)
	{
		this.length = length;
		this.setHeight(length); this.setWidth(BREADTH);
		this.setX(topleft_x); this.setY(topleft_y);
		this.setArcHeight(2); this.setArcWidth(2);
		this.setFill(Color.valueOf("#FFFFFF"));
	}

	/**
	 * Length getter
	 * @return
	 * @author Aman M
	 */
	public int getLength() {
		return length;
	}

	/**
	 * ID getter
	 * @return
	 * @author Aman M
	 */
	public String getID()
	{
		return ID;
	}

	/**
	 * Breadth getter
	 * @return
	 * @author Aman M
	 */
	public static int getBreadth()
	{
		return BREADTH;
	}

	/**
	 * Length getter
	 * @param length
	 * @author Aman M
	 */
	public void setLength(int length)
	{
		this.length = length;
	}
}
