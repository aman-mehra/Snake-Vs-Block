package GameObjects;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Shield extends Circle implements Token
{
	//private static final Image img = new Image("file:///home/bhavye/Desktop/AP/Project/Snake%20vs%20Block/shield.png");
	private static final Image img = new Image("file://../resources/shield.png");
	private static final double RADIUS = 15;
	//private static final int TIME_TO_LIVE = 5000;
	private final String ID = "S";
	private long startTime;
	private boolean isActive = true;

	public Shield(double center_x, double center_y)
	{
		super(center_x, center_y, RADIUS);
		this.startTime = -1;
		this.setFill(new ImagePattern(img));

	}

	/**
	 * Returns id
	 * @return
	 * @author Bhavye
	 */
	public String getID()
	{
		return ID;
	}

	@Override
	public double getX() {
		return this.getCenterX();
	}
	
	@Override
	public double getY() {
		return this.getTranslateY()-100;
	}
	
	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
