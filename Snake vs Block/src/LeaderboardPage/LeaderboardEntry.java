package LeaderboardPage;

import java.io.Serializable;

/**
 *
 * Blueprint for leaderboard entries.
 *
 * @author Bhavye
 */


public class LeaderboardEntry implements Serializable, Comparable<LeaderboardEntry>
{
	private long score;
	private String date;

	/**
	 *
	 * Creates instance of LeaderboardEntry class with parametrised values
	 *
	 * @param score score of the game
	 * @param date date and time of score
	 * @author Bhavye
	 */
	public LeaderboardEntry(long score, String date)
	{
		this.score = score;
		this.date = date;
	}

	/**
	 *
	 * To compare two LeaderboardEntry objects while sorting the list
	 *
	 * @param o other object
	 * @return int value after comparison
	 * @author Bhavye
	 */
	@Override
	public int compareTo(LeaderboardEntry o)
	{
		if(score < o.score)
			return 1;
		else if(score > o.score)
			return -1;
		return date.compareTo(o.date);
	}

	/**
	 *
	 * Checks for equality between two LeaderboardEntry objects
	 *
	 * @param obj other object
	 * @return int value after comparison
	 * @author Bhavye
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && getClass() == obj.getClass())
		{
			LeaderboardEntry entry = (LeaderboardEntry) obj;
			return (score == entry.score && date.equals(entry.date));
		}
		return false;
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
	 * @param score score value
	 */
	public void setScore(int score)
	{
		this.score = score;
	}

	/**
	 * Getter method for Date
	 * @return date
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * Setter method for Date
	 * @param date date value
	 */
	public void setDate(String date)
	{
		this.date = date;
	}
}
