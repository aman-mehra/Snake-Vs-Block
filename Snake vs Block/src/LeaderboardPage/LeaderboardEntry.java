package LeaderboardPage;

import java.util.Date;

public class LeaderboardEntry
{
	private int score;
	private String date;


	public LeaderboardEntry(int score, String date)
	{
		this.score = score;
		this.date = date;
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
