package GamePage;

import GameObjects.Snake;
import javafx.animation.PathTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import java.util.ArrayList;


public class GameState
{
	private long score;
	private String date;
	private double animation_speed;
	private long offset;
	private ObservableList<Node> nodes;
	private ArrayList<PathTransition> transitions;
	private Snake snake;

	public GameState(long score, String date, double animation_speed, long offset, ObservableList<Node> nodes, ArrayList<PathTransition> transitions, Snake snake)
	{
		this.score = score;
		this.date = date;
		this.animation_speed = animation_speed;
		this.offset = offset;
		this.nodes = nodes;
		this.transitions = transitions;
		this.snake = snake;
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

	public double getAnimation_speed()
	{
		return animation_speed;
	}

	public void setAnimation_speed(double animation_speed)
	{
		this.animation_speed = animation_speed;
	}

	public long getOffset()
	{
		return offset;
	}

	public void setOffset(long offset)
	{
		this.offset = offset;
	}

	public ObservableList<Node> getNodes()
	{
		return nodes;
	}

	public void setNodes(ObservableList<Node> nodes)
	{
		this.nodes = nodes;
	}

	public ArrayList<PathTransition> getTransitions()
	{
		return transitions;
	}

	public void setTransitions(ArrayList<PathTransition> transitions)
	{
		this.transitions = transitions;
	}

	public Snake getSnake()
	{
		return snake;
	}

	public void setSnake(Snake snake)
	{
		this.snake = snake;
	}
}
