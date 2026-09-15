package org.example.footballeague.service;

import org.example.footballeague.domain.MatchResult;
import org.example.footballeague.domain.TeamStanding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueTableCalculator {
    TeamStanding standing = new TeamStanding();
    // match result
    public List<TeamStanding> calculateLeagueTable(List<MatchResult> matches) {
        Map<String, TeamStanding> teamStandings = new HashMap<>();

        for (MatchResult match : matches) {
            TeamStanding homeTeam = getStanding(teamStandings, match.homeTeam);
            TeamStanding awayTeam = getStanding(teamStandings, match.awayTeam);

            updateGoals(homeTeam, match.homeGoals, match.awayGoals);
            updateGoals(awayTeam, match.awayGoals, match.homeGoals);

            updateResult(homeTeam, awayTeam, match.homeGoals, match.awayGoals);

        }

        return null;
    };

    private TeamStanding getStanding(Map<String, TeamStanding> standings,String teamName) {
        if (!standings.containsKey(teamName)) {
            standings.put(teamName, new TeamStanding());
        }
        return standings.get(teamName);

    }

    private void updateGoals(TeamStanding teamStanding, int goalsFor, int goalsAgainst){
        teamStanding.goalsFor += goalsFor;
        teamStanding.goalsAgainst += goalsAgainst;
        teamStanding.played++;
    }

    private void updateResult(
            TeamStanding homeTeam, TeamStanding awayTeam, int homeGoals, int awayGoals
    ){
        if (homeGoals > awayGoals){
            homeTeam.points += 2;
            homeTeam.won++;
            awayTeam.lost++;
        }

        else if (homeGoals < awayGoals){
            awayTeam.points += 2;
            awayTeam.won++;
            homeTeam.lost++;
        }

        else {
            homeTeam.draw++;
            awayTeam.draw++;

            homeTeam.points += 1;
            awayTeam.points += 1;
        }
    }
}
