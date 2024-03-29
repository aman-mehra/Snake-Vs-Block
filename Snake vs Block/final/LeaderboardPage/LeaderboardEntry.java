package LeaderboardPage;

import java.io.Serializable;


public class LeaderboardEntry implements Serializable, Comparable<LeaderboardEntry>
{
	private long score;
	private String date;

	public LeaderboardEntry(long score, String date)
	{
		this.score = score;
		this.date = date;
	}

	@Override
	public int compareTo(LeaderboardEntry o)
	{
		if(score < o.score)
			return 1;
		else if(score > o.score)
			return -1;
		return date.compareTo(o.date);
	}

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

	public long getScore()
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
