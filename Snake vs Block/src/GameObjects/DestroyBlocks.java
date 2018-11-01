package GameObjects;

import javafx.scene.shape.Circle;

public class DestroyBlocks extends Circle
{
	private static final double RADIUS = 3;
	private static int idGenerator;
	private final String ID;
	private long scoreCollected;

	public DestroyBlocks(double center_x, double center_y)
	{
		this.ID = "DB" + idGenerator++;
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(RADIUS);
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
