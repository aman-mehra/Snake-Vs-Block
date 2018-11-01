package GameObjects;

import javafx.scene.shape.Circle;

public class Shield extends Circle
{
	private static final double RADIUS = 3;
	private static final int TIME_TO_LIVE = 5;
	private static int idGenerator;
	private final String ID;
	private long startTime;

	public Shield(long startTime, double center_x, double center_y)
	{
		this.startTime = startTime;
		this.ID = "S" + idGenerator++;
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(RADIUS);
	}

	public long getStartTime()
	{
		return startTime;
	}

	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}

	public String getID()
	{
		return ID;
	}
}
