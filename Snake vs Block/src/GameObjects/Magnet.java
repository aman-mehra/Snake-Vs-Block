package GameObjects;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Magnet extends Circle implements Token
{
	private static final Image img = new Image("file:///home/bhavye/Desktop/AP/Project/Snake%20vs%20Block/magnet.png");
	//private static final Image img = new Image("file://../resources/magnet.png");
	private static final double RADIUS = 15;
	//private static final int TIME_TO_LIVE = 5000;
	private final String ID = "M";
	private long startTime;
	private boolean isActive = true;

	public Magnet( double center_x, double center_y)
	{
		super(center_x, center_y, RADIUS);
		this.setFill(new ImagePattern(img));
	}
	
	@Override
	public double getX() {
		return this.getCenterX();
	}
	
	@Override
	public double getY() {
		return this.getTranslateY()-100;
	}

	/*public void start()
	{
		startTime=System.currentTimeMillis();
	}

	public boolean hasExpired()
	{
		if (System.currentTimeMillis()-startTime>TIME_TO_LIVE) {
			return true;
		}
		return false;
	}*/

	/**
	 * Id getter
	 * @return
	 * @author Aman M
	 */
	public String getID()
	{
		return ID;
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
