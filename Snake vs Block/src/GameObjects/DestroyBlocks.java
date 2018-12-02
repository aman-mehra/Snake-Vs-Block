package GameObjects;

import GamePage.GamePageController;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class DestroyBlocks extends Circle implements Token
{	
	private static final Image img = new Image("file:///home/bhavye/Desktop/AP/Project/Snake%20vs%20Block/bomb.png");
	//private static final Image img = new Image("file://../resources/bomb.png");
	private static final double RADIUS = 15;
	private final String ID = "DB";
	private long scoreCollected;
	private boolean isActive = true;
	
	public DestroyBlocks(double center_x, double center_y)
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

	/**
	 * Id getter
	 * @return
	 * @author Bhavye
	 */
	public String getID()
	{
		return ID;
	}

	/**
	 * Score getter
	 * @return
	 * @author Bhavye
	 */
	public long getScoreCollected()
	{
		return scoreCollected;
	}

	/**
	 * Updates score
	 * @param scoreCollected
	 * @author Aman M
	 */
	public void updateScoreCollected(long scoreCollected)
	{
		this.scoreCollected += scoreCollected;
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
