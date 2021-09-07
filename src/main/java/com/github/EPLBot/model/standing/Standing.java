package com.github.EPLBot.model.standing;

import com.github.EPLBot.model.competition.Competition;
import com.github.EPLBot.model.competition.CompetitionSeason;

import java.util.List;

/**
 * Class model for Standing list
 * @author remimarion
 */
public class Standing {

	//ATTRIBUT
	private Competition competition;
	private CompetitionSeason season;
	private List<StandingType> standings;
	
	//CONSTRUCTOR
	public Standing(Competition competition, CompetitionSeason season,
			List<StandingType> standings) {
		super();
		this.competition = competition;
		this.season = season;
		this.standings = standings;
	}

	//GET/SET
	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public CompetitionSeason getSeason() {
		return season;
	}

	public void setSeason(CompetitionSeason season) {
		this.season = season;
	}

	public List<StandingType> getStandings() {
		return standings;
	}

	public void setStandings(List<StandingType> standings) {
		this.standings = standings;
	}

	//METHOD
	@Override
	public String toString() {
		return "StandingListModel [competition=" + competition + ", season=" + season + ", standings=" + standings
				+ "]";
	}
}
