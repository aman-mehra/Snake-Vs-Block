package GameObjects;

public interface Token
{
	/**
	 * Returns X coord
	 * @return
	 * @author Aman M
	 */
	public double getX();

	/**
	 * Returns Y coord
	 * @return
	 * @author Aman M
	 */
	public double getY();

	/**
	 * Returns activity
	 * @return
	 * @author Aman M
	 */
	public boolean isActive();

	/**
	 * Sets/Unsets activity
	 * @param isActive
	 * @author Aman M
	 */
	public void setActive(boolean isActive);
}
