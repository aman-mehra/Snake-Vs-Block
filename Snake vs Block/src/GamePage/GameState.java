package GamePage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Blueprint for the state of a game.
 *
 * @author Bhavye
 */
public class GameState implements Serializable
{
	private long score, offset;
	private String date;
	private double animation_speed;
	private double snake_head_x;
	private int snake_length;
	private ArrayList<String> gameObjects_id;
	private ArrayList<double[]> gameObjects_vals;

	/**
	 * Creates instance of GameState with parametrised valuues.
	 *
	 * @param score
	 * @param date
	 * @param offset
	 * @param animation_speed
	 * @param snake_head_x
	 * @param snake_length
	 * @author Bhavye
	 */
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

	/**
	 * Getter method for score
	 * @return score
	 */
	public long getScore()
	{
		return score;
	}

	/**
	 * Setter method for score
	 * @param score
	 */
	public void setScore(long score)
	{
		this.score = score;
	}

	/**
	 * Getter method for date
	 * @return score
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * Setter method for date
	 * @return score
	 */
	public void setDate(String date)
	{
		this.date = date;
	}

	/**
	 * Getter method for offset
	 * @return score
	 */
	public long getOffset()
	{
		return offset;
	}

	/**
	 * Setter method for offset
	 * @return score
	 */
	public void setOffset(long offset)
	{
		this.offset = offset;
	}

	/**
	 * Getter method for animation_speed
	 * @return score
	 */
	public double getAnimation_speed()
	{
		return animation_speed;
	}

	/**
	 * Setter method for animation_speed
	 * @return score
	 */
	public void setAnimation_speed(double animation_speed)
	{
		this.animation_speed = animation_speed;
	}

	/**
	 * Getter method for snake_head_x
	 * @return score
	 */
	public double getSnake_head_x()
	{
		return snake_head_x;
	}

	/**
	 * Setter method for snake_head_x
	 * @return score
	 */
	public void setSnake_head_x(double snake_head_x)
	{
		this.snake_head_x = snake_head_x;
	}

	/**
	 * Getter method for snake_length
	 * @return score
	 */
	public int getSnake_length()
	{
		return snake_length;
	}

	/**
	 * Setter method for snake_length
	 * @return score
	 */
	public void setSnake_length(int snake_length)
	{
		this.snake_length = snake_length;
	}

	/**
	 * Getter method for gameObjects_ids
	 * @return score
	 */
	public ArrayList<String> getGameObjects_id()
	{
		return gameObjects_id;
	}

	/**
	 * Setter method for gameObjects_ids
	 * @return score
	 */
	public void setGameObjects_id(ArrayList<String> gameObjects_id)
	{
		this.gameObjects_id = gameObjects_id;
	}

	/**
	 * Getter method for gameObjects_vals
	 * @return score
	 */
	public ArrayList<double[]> getGameObjects_vals()
	{
		return gameObjects_vals;
	}

	/**
	 * Setter method for gameObjects_vals
	 * @return score
	 */
	public void setGameObjects_vals(ArrayList<double[]> gameObjects_vals)
	{
		this.gameObjects_vals = gameObjects_vals;
	}
}
