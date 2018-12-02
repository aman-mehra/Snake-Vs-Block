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
	private final String ID = "BL";
	private int value;
	private boolean active;

	public Block(int value, double topleft_x, double topleft_y, Paint colour)
	{
		super(topleft_x, topleft_y, SIDE, SIDE);
		this.active = true;
		this.value = value;
		this.setArcHeight(30); this.setArcWidth(30);
		this.setFill(colour);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public static int getSIDE()
	{
		return SIDE;
	}

	public String getID()
	{
		return ID;
	}

}
