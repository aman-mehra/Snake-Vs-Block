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
	private static int idGenerator;
	private final String ID;
	private long startTime;
	private boolean isActive = true;

	public Shield(double center_x, double center_y)
	{
		this.startTime = -1;
		this.ID = "S" + idGenerator++;
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(RADIUS);
		this.setFill(new ImagePattern(img));

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
