package net.bubbaland.trivia;

import java.io.Serializable;

/**
 * A data structure for the announced score and place of one team.
 * 
 * @author Walter Kolczynski
 *
 */

public class ScoreEntry implements Comparable<ScoreEntry>, Serializable {
	
	private static final long	serialVersionUID	= -6052352344375555126L;
	
	private final String teamName;
	private final int score;
	private final int place;
	
	public ScoreEntry(String teamName, int score, int place) {
		this.teamName = teamName;
		this.score = score;
		this.place = place;
	}

	public String getTeamName() {
		return this.teamName;
	}

	public int getScore() {
		return this.score;
	}

	public int getPlace() {
		return this.place;
	}

	public int compareTo(ScoreEntry o) {
		return teamName.compareTo(o.getTeamName());
	}
	

}
