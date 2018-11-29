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
	
	private void generateColor() {
		String color = "#";
		for(int i=0; i < 6;i++) {
			color+=hex.charAt(rand.nextInt(16));
		}
		COLOUR = Color.valueOf(color);
	}

	public void setX(double x) {
		this.setCenterX(x);
	}
	
	public double getX() {
		return this.getCenterX();
	}
	
}
