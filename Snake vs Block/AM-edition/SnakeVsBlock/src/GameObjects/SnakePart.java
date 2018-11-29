package GameObjects;
//import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class SnakePart extends Circle{
	private final Paint COLOUR = Color.valueOf("#00EEFF");
	private final int pid;
	private static int ctr;
	//private static double displacement = .75;
	
	public SnakePart(double center_x, double center_y, double radius)
	{
		this.setCenterX(center_x);
		this.setCenterY(center_y);
		this.setRadius(radius);
		this.setFill(COLOUR);
		this.pid=ctr++;
	}

	public void setX(double x) {
		this.setCenterX(x);
	}
	
	public double getX() {
		return this.getCenterX();
	}
	
	public int getPId() {
		return this.pid;
	}
	
}
