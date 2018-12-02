package GameObjects;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class ExplosionPart extends Circle{
	private final String hex = "0123456789ABCDEF"; 
	private Paint COLOUR; 
	private static Random rand = new Random();
	
	public ExplosionPart(double center_x, double center_y)
	{
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(7);
		generateColor();
		this.setFill(COLOUR);
	}
	
	/**
	 * Generates random color
	 * @author Aman M
	 */
	private void generateColor() {
		String color = "#";
		for(int i=0; i < 6;i++) {
			color+=hex.charAt(rand.nextInt(16));
		}
		COLOUR = Color.valueOf(color);
	}
	
	/**
	 * Makes object invisible
	 * @author Aman M
	 */
	public void setCollectionColor() {
		COLOUR = Color.valueOf("#FFFFFF");
		this.setFill(COLOUR);
	}
	
	/**
	 * Sets radius
	 * @author Aman M
	 */
	public void setCollectionRadius() {
		this.setRadius(3);
	}

	/**
	 * sets x coord
	 * @param x
	 * @author Aman M
	 */
	public void setX(double x) {
		this.setCenterX(x);
	}
	
	/**
	 * gets x coord
	 * @return
	 * @author Aman M
	 */
	public double getX() {
		return this.getCenterX();
	}
	
}
