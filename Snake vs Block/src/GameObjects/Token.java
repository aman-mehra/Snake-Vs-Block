package GameObjects;

import javafx.scene.layout.Pane;

public interface Token
{
	public double getX();
	public double getY();
	public boolean isActive();
	public void setActive(boolean isActive);
}
