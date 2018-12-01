package GamePage;

import GameObjects.Snake;

import java.io.Serializable;

public class GameState implements Serializable
{
	private long score, offset;
	private String date;
	private double animation_speed;
	private double snake_head_x;
	private int snake_length;
	private int number_of_gameObjects;
	private String[] gameObjects_id;
	private double[][] gameObjects_pos;

	public GameState(long score, String date, long offset, double animation_speed, double snake_head_x, int snake_length, int number_of_gameObjects)
	{
		this.score = score;
		this.date = date;
		this.offset = offset;
		this.animation_speed = animation_speed;
		this.snake_head_x = snake_head_x;
		this.snake_length = snake_length;
		this.number_of_gameObjects = number_of_gameObjects;
		this.gameObjects_id = new String[number_of_gameObjects];
		this.gameObjects_pos = new double[number_of_gameObjects][2];
	}

	public long getScore()
	{
		return score;
	}

	public void setScore(long score)
	{
		this.score = score;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public long getOffset()
	{
		return offset;
	}

	public void setOffset(long offset)
	{
		this.offset = offset;
	}

	public double getAnimation_speed()
	{
		return animation_speed;
	}

	public void setAnimation_speed(double animation_speed)
	{
		this.animation_speed = animation_speed;
	}

	public double getSnake_head_x()
	{
		return snake_head_x;
	}

	public void setSnake_head_x(double snake_head_x)
	{
		this.snake_head_x = snake_head_x;
	}

	public int getSnake_length()
	{
		return snake_length;
	}

	public void setSnake_length(int snake_length)
	{
		this.snake_length = snake_length;
	}

	public int getNumber_of_gameObjects()
	{
		return number_of_gameObjects;
	}

	public void setNumber_of_gameObjects(int number_of_gameObjects)
	{
		this.number_of_gameObjects = number_of_gameObjects;
	}

	public String[] getGameObjects_id()
	{
		return gameObjects_id;
	}

	public void setGameObjects_id(String[] gameObjects_id)
	{
		this.gameObjects_id = gameObjects_id;
	}

	public double[][] getGameObjects_pos()
	{
		return gameObjects_pos;
	}

	public void setGameObjects_pos(double[][] gameObjects_pos)
	{
		this.gameObjects_pos = gameObjects_pos;
	}
}
