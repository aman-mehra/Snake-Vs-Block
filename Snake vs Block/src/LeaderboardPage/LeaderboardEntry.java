package LeaderboardPage;

import java.io.Serializable;


public class LeaderboardEntry implements Serializable, Comparable<LeaderboardEntry>
{
	private int score;
	private String date;

	public LeaderboardEntry(int score, String date)
	{
		this.score = score;
		this.date = date;
	}

	@Override
	public int compareTo(LeaderboardEntry o)
	{
		if(score == o.score) return 0;
		else if(score < o.score) return -1;
		return 1;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
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
}
