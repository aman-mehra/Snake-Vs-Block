package GameObjects;
//import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class SnakePart extends Circle{
	private Paint defaultCOLOUR = Color.valueOf("#00EEFF");
	private Paint shieldCOLOUR = Color.valueOf("#FFFFFF");
	private Paint magnetCOLOUR = Color.valueOf("#D4AF37");
	private final int pid;
	private static int ctr;
	//private static double displacement = .75;
	
	public SnakePart(double center_x, double center_y, double radius)
	{
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(radius);
		this.setFill(defaultCOLOUR);
		this.pid=ctr++;
	}

	/**
	 * x-coordinate setter
	 * @param x
	 * @author Aman M
	 */
	public void setX(double x) {
		this.setCenterX(x);
	}

	/**
	 * x-coordinate getter
	 * @return
	 * @author Aman M
	 */
	public double getX() {
		return this.getCenterX();
	}

	/**
	 * id getter
	 * @return
	 * @author Aman M
	 */
	public int getPId() {
		return this.pid;
	}

	/**
	 * Custom shield color setter
	 * @author Aman M
	 */
	public void setShieldColor() {
		this.setFill(shieldCOLOUR);
	}

	/**
	 * Custom magnet color setter
	 * @author Aman M
	 */
	public void setMagnetColor() {
		this.setFill(magnetCOLOUR);
	}

	/**
	 * default color setter
	 * @author Aman M
	 */
	public void unsetTokenColor() {
		this.setFill(defaultCOLOUR);
	}
	
}
