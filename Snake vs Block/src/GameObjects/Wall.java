package GameObjects;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Wall extends Rectangle
{
	private static final int breadth = 4;
	private int length;
	
	public Wall(double topleft_x, double topleft_y,int length) {
		
		this.length = length;
		this.setHeight(length); this.setWidth(breadth);
		this.setX(topleft_x); this.setY(topleft_y);
		this.setArcHeight(2); this.setArcWidth(2);
		this.setFill(Color.valueOf("#FFFFFF"));
		
	}

	public int getLength() {
		return length;
	}

	public static int getBreadth() {
		return breadth;
	}
	
	
}
