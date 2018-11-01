package GameObjects;

public class Token
{
	protected static int idGenerator;
	protected double position_x, positon_y;
	protected int ID;

	public static int getIdGenerator()
	{
		return idGenerator;
	}

	public static void setIdGenerator(int idGenerator)
	{
		Token.idGenerator = idGenerator;
	}

	public double getPosition_x()
	{
		return position_x;
	}

	public void setPosition_x(double position_x)
	{
		this.position_x = position_x;
	}

	public double getPositon_y()
	{
		return positon_y;
	}

	public void setPositon_y(double positon_y)
	{
		this.positon_y = positon_y;
	}

	public int getID()
	{
		return ID;
	}

	public void setID(int ID)
	{
		this.ID = ID;
	}
}
