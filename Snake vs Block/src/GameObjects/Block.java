package GameObjects;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Block extends Rectangle
{
	private static final int SIDE = 100;
	private static int idGenerator;
	private final String ID;

	public Block(double topleft_x, double topleft_y, Paint colour)
	{
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
}
