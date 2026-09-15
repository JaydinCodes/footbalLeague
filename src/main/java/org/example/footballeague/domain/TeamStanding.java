package org.example.footballeague.domain;

public class TeamStanding {
    public String teamName;
    public int played;
    public int won;
    public int draw;
    public int lost;
    public int goalsFor;
    public int goalsAgainst;
    public int points;

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

}