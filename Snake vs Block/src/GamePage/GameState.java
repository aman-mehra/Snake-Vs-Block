package GamePage;

import java.io.Serializable;
import java.util.ArrayList;


public class GameState implements Serializable
{
	private long score, offset;
	private String date;
	private double animation_speed;
	private double snake_head_x;
	private int snake_length;
	private ArrayList<String> gameObjects_id;
	private ArrayList<double[]> gameObjects_vals;

	public GameState(long score, String date, long offset, double animation_speed, double snake_head_x, int snake_length)
	{
		this.score = score;
		this.date = date;
		this.offset = offset;
		this.animation_speed = animation_speed;
		this.snake_head_x = snake_head_x;
		this.snake_length = snake_length;
		this.gameObjects_id = new ArrayList<>();
		this.gameObjects_vals = new ArrayList<>();
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

	public ArrayList<String> getGameObjects_id()
	{
		return gameObjects_id;
	}

	public void setGameObjects_id(ArrayList<String> gameObjects_id)
	{
		this.gameObjects_id = gameObjects_id;
	}

	public ArrayList<double[]> getGameObjects_vals()
	{
		return gameObjects_vals;
	}

	public void setGameObjects_vals(ArrayList<double[]> gameObjects_vals)
	{
		this.gameObjects_vals = gameObjects_vals;
	}
}
