package GameObjects;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;


public class DestroyBlocks extends Circle implements Token
{
	private static final Image img = new Image("file:///home/bhavye/Desktop/AP/Project/Snake%20vs%20Block/bomb.png");
	private static final double RADIUS = 15;
	private static int idGenerator;
	private final String ID;
	private long scoreCollected;

	public DestroyBlocks(double center_x, double center_y)
	{
		this.ID = "DB" + idGenerator++;
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(RADIUS);
		this.setFill(new ImagePattern(img));

	}

	public String getID()
	{
		return ID;
	}

	public long getScoreCollected()
	{
		return scoreCollected;
	}

	public void setScoreCollected(long scoreCollected)
	{
		this.scoreCollected = scoreCollected;
	}

}
