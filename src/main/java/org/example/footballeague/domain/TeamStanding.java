package org.example.footballeague.domain;

public class TeamStanding {

    private final String teamName;

    private int played;
    private int won;
    private int draw;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    public TeamStanding(String teamName) {

        if (teamName == null || teamName.isBlank()) {
            throw new IllegalArgumentException(
                    "Team name cannot be empty"
            );
        }

        this.teamName = teamName.trim();
    }

    public void recordMatch(
            int goalsFor,
            int goalsAgainst) {

        if (goalsFor < 0 || goalsAgainst < 0) {
            throw new IllegalArgumentException(
                    "Goals cannot be negative"
            );
        }

        played++;

        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;

        if (goalsFor > goalsAgainst) {
            won++;
            points += 2;
        } else if (goalsFor < goalsAgainst) {
            lost++;
        } else {
            draw++;
            points++;
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public int getPlayed() {
        return played;
    }

    public int getWon() {
        return won;
    }

    public int getDraw() {
        return draw;
    }

    public int getLost() {
        return lost;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

    public double getGoalAverage() {

        if (goalsAgainst == 0) {
            return goalsFor;
        }

        return (double) goalsFor / goalsAgainst;
    }
}